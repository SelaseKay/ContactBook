package com.judekwashie.contactbook.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.judekwashie.contactbook.data.entities.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact): Long

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY firstName ASC")
    fun getAllContacts(): LiveData<MutableList<Contact>>

    @Query("SELECT * FROM contact_table WHERE :contactId = contactId")
    fun getContact(contactId: Long): LiveData<Contact>

}