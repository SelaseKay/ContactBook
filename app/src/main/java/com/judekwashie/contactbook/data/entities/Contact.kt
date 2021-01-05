package com.judekwashie.contactbook.data.entities

import androidx.room.*

@Entity(tableName = "contact_table")
data class Contact(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var image: Int? = null,
    var color: Int? = null,
    var phoneNumbers: ArrayList<String> = ArrayList(2)
) {

    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0
}