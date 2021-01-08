package com.judekwashie.contactbook.ui.contactedit

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.judekwashie.contactbook.R
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.databinding.FragmentContactEditBinding
import com.judekwashie.contactbook.utils.getRandomColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ContactEditFragment : Fragment() {

    private var _binding: FragmentContactEditBinding? = null
    private val binding get() = _binding!!

    private val args: ContactEditFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar
    private var _contactId by Delegates.notNull<Long>()

    private var onContactUpdateCallback: (() -> Contact)? = null

    private val contactEditViewModel: ContactEditViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        _contactId = args.contactId
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_save_delete, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.contact_edit_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        contactEditViewModel.getContact(_contactId).observe(viewLifecycleOwner, Observer {
            it?.let {
                toolbar.title = getString(R.string.edit_contact)
                setViewValues(it)
                setOnContactUpdateCallback {
                    it.apply {
                        firstName = binding.firstNameTextLayout.editText?.text.toString()
                        lastName = binding.lastNameTextLayout.editText?.text.toString()
                        email = binding.emailTextLayout.editText?.text.toString()
                        phoneNumbers.add(0, binding.phoneNumberTextLayout.editText?.text.toString())
                    }
                }
            } ?: kotlin.run { toolbar.title = getString(R.string.create_contact) }
        })

        toolbar.setNavigationOnClickListener {
            navigateToListFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_item -> {
                contactEditViewModel.deleteContact(getNewContact().apply {
                    contactId = _contactId
                })
                navigateToListFragment()
                true
            }
            R.id.save_item -> {
                CoroutineScope(Dispatchers.IO).launch {
                    if (_contactId == -1L) {
                        val contact = getNewContact()
                        _contactId = contactEditViewModel.insertContact(contact)
                        navigateToDetailFragment()
                    } else {
                        val contact = onContactUpdateCallback?.let { it() }!!
                        contactEditViewModel.updateContact(contact)
                        navigateToDetailFragment()
                    }
                }
                true
            }
            else -> {
                false
            }
        }
    }

    private fun setViewValues(contact: Contact) {
        binding.firstNameTextLayout.editText?.setText(contact.firstName)
        binding.lastNameTextLayout.editText?.setText(contact.lastName)
        binding.emailTextLayout.editText?.setText(contact.email)
        binding.phoneNumberTextLayout.editText?.setText(contact.phoneNumbers[0])
    }

    private fun getNewContact(): Contact {
        val contact = Contact()
        contact.apply {
            firstName = binding.firstNameTextLayout.editText?.text.toString()
            lastName = binding.lastNameTextLayout.editText?.text.toString()
            email = binding.emailTextLayout.editText?.text.toString()
            phoneNumbers.add(binding.phoneNumberTextLayout.editText?.text.toString())
            color = getRandomColor(requireContext())
        }
        return contact
    }

    private fun setOnContactUpdateCallback(function: (() -> Contact)?) {
        this.onContactUpdateCallback = function
    }

    private fun navigateToDetailFragment() {
        val action = ContactEditFragmentDirections.actionContactEditFragmentToContactDetailFragment(
            _contactId
        )
        findNavController().navigate(action)
    }

    private fun navigateToListFragment() {
        findNavController().navigate(R.id.action_contactEditFragment_to_contactListFragment)
    }
}