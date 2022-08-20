package com.example.contactlist.main.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ContactModel : ViewModel() {
    val putId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}