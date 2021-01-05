package com.judekwashie.contactbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.repo.ContactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactViewModel
@Inject
constructor(private val contactRepository: ContactRepository) : ViewModel() {

    suspend fun insertContact(contact: Contact) = viewModelScope.launch {
        contactRepository.insertContact(contact)
    }

    suspend fun deleteContact(contact: Contact) = viewModelScope.launch {
        contactRepository.deleteContact(contact)
    }

    suspend fun updateContact(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)
    }

    fun getContact(contactId: Long): LiveData<Contact> {
        return contactRepository.getContact(contactId)
    }

    fun getAllContacts(): LiveData<MutableList<Contact>> {
        return contactRepository.getAllContacts()
    }

}