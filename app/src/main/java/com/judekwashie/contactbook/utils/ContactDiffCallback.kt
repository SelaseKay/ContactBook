package com.judekwashie.contactbook.utils

import androidx.recyclerview.widget.DiffUtil
import com.judekwashie.contactbook.data.entities.Contact

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId && oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
                && oldItem.phoneNumbers == newItem.phoneNumbers && oldItem.email == newItem.email
                && oldItem.color == newItem.color && oldItem.image == newItem.image
    }
}