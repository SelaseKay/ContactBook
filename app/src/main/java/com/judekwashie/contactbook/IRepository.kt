package com.judekwashie.contactbook

import androidx.lifecycle.LiveData
import com.judekwashie.contactbook.data.entities.Contact


interface IRepository {
    suspend fun insertContact(contact: Contact): Long

    suspend fun deleteContact(contact: Contact) {

    }

    suspend fun updateContact(contact: Contact) {

    }

    fun getAllContacts(): LiveData<MutableList<Contact>>
    fun getContact(contactId: Long): LiveData<Contact>
}