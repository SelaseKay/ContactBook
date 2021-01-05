package com.judekwashie.contactbook

import android.content.Context
import android.view.View
import com.judekwashie.contactbook.data.entities.Contact

interface RecyclerItemClickListener {
    fun onClick(contact: Contact, view: View)
}