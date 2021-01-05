package com.judekwashie.contactbook.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.database.ContactDatabase
import com.judekwashie.contactbook.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.math.exp

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private lateinit var contactDao: ContactDao
    private lateinit var contactDatabase: ContactDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        contactDatabase = Room.inMemoryDatabaseBuilder(
            context, ContactDatabase::class.java).allowMainThreadQueries().build()

        contactDao = contactDatabase.getDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        contactDatabase.close()
    }


    @Test
    fun test_insertContact() {
        val contact = Contact("Jude", "Kwashie", phoneNumbers = arrayListOf("054","024"))
        runBlockingTest {
            contactDao.addContact(contact)

            val allContacts = contactDao.getAllContacts().getOrAwaitValue()
            assertThat(allContacts).contains(contact)
        }
    }

    @Test
    fun test_deleteContact() {
        val contact = Contact("Jay", "Kay")
        contact.contactId = 1
        runBlockingTest {
            contactDao.addContact(contact)
            contactDao.deleteContact(contact)
            val allContacts = contactDao.getAllContacts().getOrAwaitValue()
            assertThat(allContacts).doesNotContain(contact)
        }
    }

    @Test
    fun test_updateContact() {
        val contact = Contact("Jay", "Kwashie")
        contact.contactId = 2
        runBlockingTest {
            contactDao.addContact(contact)
            contact.firstName = "Kwashie"
            contactDao.updateContact(contact)
            val allContacts = contactDao.getAllContacts().getOrAwaitValue()
            assertThat(allContacts).contains(contact)
        }
    }

    @Test
    fun test_getContact() {
        val expected = Contact("Jude", "Kwashie").apply {
            contactId = 3
        }
        runBlockingTest {
            contactDao.addContact(expected)
            val actual = contactDao.getContact(3).getOrAwaitValue()
            assertThat(expected).isEqualTo(actual)
        }

    }

    @Test
    fun test_getAllContacts() {
        val contact1 = Contact("Jude", "Kwashie").apply {
            contactId = 1
        }
        val contact2 = Contact("Jude", "Kwashie").apply {
            contactId = 2
        }
        val contact3 = Contact("Jude", "Kwashie").apply {
            contactId = 3
        }
        val contact4 = Contact("Jude", "Kwashie").apply {
            contactId = 4
        }
        val actual = listOf(contact1, contact2, contact3, contact4)

        runBlockingTest {
            contactDao.addContact(contact1)
            contactDao.addContact(contact2)
            contactDao.addContact(contact3)
            contactDao.addContact(contact4)

            val expected = contactDao.getAllContacts().getOrAwaitValue()
            assertThat(expected).isEqualTo(actual)
        }

    }
}