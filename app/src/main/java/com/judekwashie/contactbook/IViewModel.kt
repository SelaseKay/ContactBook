package com.judekwashie.contactbook

import androidx.lifecycle.LiveData
import com.judekwashie.contactbook.data.entities.Contact

interface IViewModel {
    suspend fun insertContact(contact: Contact): Long

    fun deleteContact(contact: Contact) {

    }

    fun updateContact(contact: Contact) {

    }

    fun getAllContacts(): LiveData<MutableList<Contact>>
    fun getContact(contactId: Long): LiveData<Contact>
}