package com.example.contactlist.main.model

import kotlin.random.Random

object ContactImpl {
    var contactList = generate()

    private fun generate(): ArrayList<DataContactList> {
        val contactListDiff = arrayListOf<DataContactList>()
        for (i in 1..100) {
            contactListDiff.add(
                DataContactList(
                    id = Random.nextInt(150),
                    firstName = "Ivan$i",
                    secondName = "Ivanov$i",
                    phoneNumber = "+7932323141$i",
                    url = "https://picsum.photos/id/1$i/200/300"
                )
            )
        }
        return contactListDiff
    }
}