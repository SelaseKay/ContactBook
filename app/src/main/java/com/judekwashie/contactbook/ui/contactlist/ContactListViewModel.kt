package com.judekwashie.contactbook.ui.contactlist

import android.widget.Filter
import android.widget.Filterable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judekwashie.contactbook.IViewModel
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.repo.ContactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactListViewModel
@ViewModelInject
constructor(private val contactRepository: ContactRepository) : ViewModel(), IViewModel{


    override suspend fun insertContact(contact: Contact): Long {
        val result = viewModelScope.async {
            contactRepository.insertContact(contact)
        }
        return result.await()
    }

    override fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactRepository.deleteContact(contact)
        }
    }

    override fun updateContact(contact: Contact) {
        viewModelScope.launch {
            contactRepository.updateContact(contact)
        }
    }

    override fun getContact(contactId: Long): LiveData<Contact> {
        return contactRepository.getContact(contactId)
    }

    override fun getAllContacts(): LiveData<MutableList<Contact>> {
        return contactRepository.getAllContacts()
    }

}