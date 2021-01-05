package com.judekwashie.contactbook.repo

import androidx.lifecycle.LiveData
import com.judekwashie.contactbook.IRepository
import com.judekwashie.contactbook.dao.ContactDao
import com.judekwashie.contactbook.data.entities.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Callable
import javax.inject.Inject

class ContactRepository
@Inject
constructor(private val contactDao: ContactDao) : IRepository {

    override suspend fun insertContact(contact: Contact): Long {
        return withContext(Dispatchers.IO){
            contactDao.addContact(contact)
        }
    }

    override suspend fun deleteContact(contact: Contact) {
        return withContext(Dispatchers.IO) {
            contactDao.deleteContact(contact)
        }
    }

    override suspend fun updateContact(contact: Contact) {
        return withContext(Dispatchers.IO) {
            contactDao.updateContact(contact)
        }
    }

    override fun getAllContacts(): LiveData<MutableList<Contact>> {
        return contactDao.getAllContacts()
    }

    override fun getContact(contactId: Long): LiveData<Contact> {
        return contactDao.getContact(contactId)
    }

}