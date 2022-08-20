package com.example.contactlist.main.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlist.R
import com.example.contactlist.databinding.FragmentContactBinding
import com.example.contactlist.main.model.ContactImpl.contactList
import com.example.contactlist.main.model.ContactModel
import com.example.contactlist.main.model.DataContactList
import com.example.contactlist.main.ui.adapter.ContactAdapter
import com.example.contactlist.main.utils.ItemDecorator

class ContactFragment : Fragment() {

    private val data: ContactModel by activityViewModels()
    private val adapter = ContactAdapter(contactList,
        { id -> scaleScreen(id) },
        { id -> dialogDelete(id) })

    private var _binding: FragmentContactBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        binding.etContactFragment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvContactFragment.layoutManager = LinearLayoutManager(requireContext())
            rvContactFragment.adapter = adapter
            rvContactFragment.addItemDecoration(ItemDecorator())
            rvContactFragment.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun scaleScreen(id: Int) {
        val screenMode = context?.resources?.configuration?.smallestScreenWidthDp
        if (screenMode!! >= 600) {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container_holder_tablet_two, ContactInfoFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()

        } else {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container_holder_tablet, ContactInfoFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
        data.putId.value = id
    }

    private fun filter(text: String) {
        val filteredContacts = ArrayList<DataContactList>()
        contactList.filterTo(filteredContacts) {
            it.firstName.lowercase().contains(text.lowercase())
        }
        adapter.filteredList(filteredContacts)
    }

    private fun dialogDelete(id: Int) {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.dialog_delete_text))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, i -> removeContact(id) }
            .setNegativeButton(getString(R.string.dialog_no), null)
            .show()
    }

    private fun removeContact(id: Int) {
        val removedContacts = java.util.ArrayList<DataContactList>()
        val contactToRemove = contactList.find { it.id == id }
        removedContacts.addAll(contactList)
        removedContacts.remove(contactToRemove)
        adapter.removeContact(removedContacts)
    }

    companion object {
        fun newInstance() = ContactFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}