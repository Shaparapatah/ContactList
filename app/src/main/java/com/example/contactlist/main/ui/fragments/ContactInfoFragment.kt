package com.example.contactlist.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.contactlist.R
import com.example.contactlist.databinding.FragmentContactInfoBinding
import com.example.contactlist.main.model.ContactImpl.contactList
import com.example.contactlist.main.model.ContactModel
import com.example.contactlist.main.model.DataContactList

class ContactInfoFragment : Fragment() {

    private val data: ContactModel by activityViewModels()
    private var newId: Int = 0

    private var _binding: FragmentContactInfoBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        putId()

        if (requireActivity().resources?.configuration?.smallestScreenWidthDp!! >= 600) {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container_holder_tablet, ContactFragment.newInstance())
                ?.commit()
        }
    }

    private fun putId() {
        data.putId.observe(activity as LifecycleOwner) {
            newId = it
        }
        val contact: DataContactList? = contactList.find { it.id == newId }
        with(binding) {
            edFirstName.setText(contact?.firstName)
            edSecondName.setText(contact?.secondName)
            edPhoneNumber.setText(contact?.phoneNumber)

        }
    }

    companion object {
        fun newInstance() = ContactInfoFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}