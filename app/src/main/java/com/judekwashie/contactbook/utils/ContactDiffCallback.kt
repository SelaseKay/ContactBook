package com.judekwashie.contactbook.utils

import androidx.recyclerview.widget.DiffUtil
import com.judekwashie.contactbook.data.entities.Contact

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }
}