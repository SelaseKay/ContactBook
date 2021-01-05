package com.judekwashie.contactbook.ui.contactedit

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

class ContactEditFragment : Fragment() {

    private var _binding: FragmentContactEditBinding? = null
    private val binding get() = _binding!!

    private val args: ContactEditFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar
    private var _contactId by Delegates.notNull<Long>()

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
            } ?: kotlin.run { toolbar.title = getString(R.string.create_contact) }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_item -> {
                contactEditViewModel.deleteContact(getContact().apply {
                    contactId = _contactId
                })
                navigateToListFragment()
                true
            }
            R.id.save_item -> {
                CoroutineScope(Dispatchers.IO).launch {
                    if (_contactId == -1L) {
                        val contact = getContact()
                        _contactId = contactEditViewModel.insertContact(contact)
                        navigateToDetailFragment()
                    } else {
                        contactEditViewModel.updateContact(getContact().apply {
                            contactId = _contactId
                        })
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

    private fun setViewValues(contact: Contact){
        binding.firstNameTextLayout.editText?.setText(contact.firstName)
        binding.lastNameTextLayout.editText?.setText(contact.lastName)
        binding.emailTextLayout.editText?.setText(contact.email)
        binding.phoneNumberTextLayout.editText?.setText(contact.phoneNumbers.get(0))
    }

    private fun getContact(): Contact {
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

    private fun navigateToDetailFragment() {
        val action = ContactEditFragmentDirections.actionContactEditFragmentToContactDetailFragment(
           _contactId
        )
        findNavController().navigate(action)
    }

    private fun navigateToListFragment(){
        findNavController().navigate(R.id.action_contactEditFragment_to_contactListFragment)
    }
}