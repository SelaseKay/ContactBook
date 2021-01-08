package com.judekwashie.contactbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.judekwashie.contactbook.R
import com.judekwashie.contactbook.RecyclerItemClickListener
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.utils.ContactDiffCallback
import kotlinx.android.synthetic.main.contact_item.view.*
import javax.inject.Inject

class ContactRecyclerAdapter
@Inject
constructor(private val recyclerItemClickListener: RecyclerItemClickListener) :
    ListAdapter<Contact, ContactRecyclerAdapter.ContactViewHolder>(ContactDiffCallback()),
    Filterable {

    var contactListCopy: ArrayList<Contact> = ArrayList()
    private val searchResultList: ArrayList<Contact> = ArrayList()


    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                recyclerItemClickListener.onClick(getItem(adapterPosition), itemView)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        if (contact.firstName.trim().isEmpty() && contact.lastName.trim().isEmpty()){
            holder.itemView.full_name.text = "(No Name)"
            holder.itemView.first_name_initial.text = "#"
        }
        if (contact.firstName.trim().isNotEmpty() && contact.lastName.trim().isEmpty()){
            holder.itemView.full_name.text = contact.firstName.trim()
            holder.itemView.first_name_initial.text = contact.firstName[0].toString()
        }
        if (contact.firstName.trim().isEmpty() && contact.lastName.trim().isNotEmpty()){
            holder.itemView.full_name.text = contact.lastName.trim()
            holder.itemView.first_name_initial.text = contact.lastName[0].toString()
        }
        if (contact.firstName.trim().isNotEmpty() && contact.lastName.trim().isNotEmpty()){
            holder.itemView.full_name.text = contact.firstName.trim() + " " + contact.lastName.trim()
            holder.itemView.first_name_initial.text = contact.firstName[0].toString()
        }
        holder.itemView.contact_image_view.setBackgroundColor(contact.color!!)
    }

    override fun getFilter(): Filter {
        return contactFilter
    }

    private val contactFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Contact>()
            if (constraint!!.isEmpty()) {
                filteredList.addAll(contactListCopy)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in contactListCopy) {
                    val fullName = "${item.firstName.trim()} ${item.lastName.trim()}"
                    if (fullName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            searchResultList.clear()
            searchResultList.addAll(results?.values as List<Contact>)
            submitList(searchResultList)
        }

    }

    override fun submitList(list: MutableList<Contact>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


}