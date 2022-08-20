package com.example.contactlist.main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactlist.R
import com.example.contactlist.databinding.ItemContactBinding
import com.example.contactlist.main.model.DataContactList

class ContactAdapter(
    private var contactList: ArrayList<DataContactList>,
    private val onItemClicked: (id: Int) -> Unit,
    private val onLongItemClicked: (id: Int) -> Unit
) : ListAdapter<DataContactList, ContactAdapter.ContactViewHolder>(ContactItemCallback) {

    inner class ContactViewHolder(
        view: View,
        onItemClicked: (id: Int) -> Unit,
        onLongItemClicked: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemContactBinding.bind(view)
        private val imageView = binding.contactImage
        private var itemId: Int? = null

        init {
            view.setOnClickListener {
                itemId?.let { onItemClicked(it) }
            }
            view.setOnLongClickListener {
                itemId?.let { onLongItemClicked(it) }
                true
            }
        }

        fun bind(contact: DataContactList) {
            itemId = contact.id
            binding.contactName.text = contact.firstName
            Glide.with(itemView.context)
                .load(contact.url)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, onItemClicked, onLongItemClicked)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount() = contactList.size

    fun filteredList(filteredContacts: ArrayList<DataContactList>) {
        this.contactList = filteredContacts
        notifyDataSetChanged()
    }

    fun removeContact(removeContact: ArrayList<DataContactList>) {
        this.contactList = removeContact
        notifyDataSetChanged()
    }
}

object ContactItemCallback : DiffUtil.ItemCallback<DataContactList>() {
    override fun areItemsTheSame(oldItem: DataContactList, newItem: DataContactList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataContactList, newItem: DataContactList): Boolean {
        return oldItem == newItem
    }
}