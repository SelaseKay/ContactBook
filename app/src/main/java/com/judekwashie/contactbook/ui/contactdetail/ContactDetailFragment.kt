package com.judekwashie.contactbook.ui.contactdetail

import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.judekwashie.contactbook.R
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.databinding.FragmentContactDetailBinding
import com.judekwashie.contactbook.ui.contactedit.ContactEditFragmentArgs
import kotlinx.android.synthetic.main.labeled_view.view.*
import kotlin.properties.Delegates

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ContactEditFragmentArgs by navArgs()
    private var _contactId by Delegates.notNull<Long>()

    private val contactDetailViewModel: ContactDetailViewModel by activityViewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    private lateinit var fullName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        _contactId = args.contactId
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_items, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.contact_detail_toolbar)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_layout)

        contactDetailViewModel.getContact(_contactId).observe(viewLifecycleOwner, Observer {
            it?.let {
                validateContactProperties(it)
                initializeViews(it)
                (activity as AppCompatActivity).setSupportActionBar(toolbar)
            }

        })


    }

    private fun validateContactProperties(contact: Contact) {

        if (contact.firstName.trim().isEmpty() && contact.lastName.trim().isEmpty()) {
            fullName = "(No Name)"
        }
        if (contact.firstName.trim().isNotEmpty() && contact.lastName.trim().isEmpty()) {
            fullName = contact.firstName.trim()
        }
        if (contact.firstName.trim().isEmpty() && contact.lastName.trim().isNotEmpty()) {
            fullName = contact.lastName.trim()
        }
        if (contact.firstName.trim().isNotEmpty() && contact.lastName.trim().isNotEmpty()) {
            fullName = contact.firstName.trim() + " " + contact.lastName.trim()
        }
    }

    private fun initializeViews(contact: Contact) {
        binding.fullNameLabelTextView.description.text =
            fullName
        binding.emailLabelTextView.description.text = contact.email.trim()
        binding.phoneNumber.apply {
            description.text = contact.phoneNumbers[0]
            if (this.visibility == View.VISIBLE) {
                binding.phoneImageView.visibility = View.VISIBLE
                binding.textSmsImageView.visibility = View.VISIBLE
            } else {
                binding.phoneImageView.visibility = View.INVISIBLE
                binding.textSmsImageView.visibility = View.INVISIBLE
            }
        }
        toolbar.title = fullName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_item -> {
                val action =
                    ContactDetailFragmentDirections.actionContactDetailFragmentToContactEditFragment(
                        _contactId
                    )
                findNavController().navigate(action)
                true
            }
            R.id.add_photo_item -> {
                Toast.makeText(this.requireContext(), "add photo clicked", Toast.LENGTH_LONG).show()
                true
            }
            R.id.delete_item -> {
                val contact = Contact().apply { contactId = _contactId }
                contactDetailViewModel.deleteContact(contact)
                findNavController().navigate(R.id.action_contactDetailFragment_to_contactListFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


}