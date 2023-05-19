package com.example.thindie.aston_intensive_lesson5.data

import com.example.thindie.aston_intensive_lesson5.data.someData.SomeDataHolder
import com.example.thindie.aston_intensive_lesson5.domain.ContactsRepository
import com.example.thindie.aston_intensive_lesson5.domain.entity.Contact
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
@Singleton
internal class ContactsRepositoryImpl @Inject constructor(private val someDataHolder: SomeDataHolder) :
    ContactsRepository {


    override fun updateContact(oldContact: Contact, newContact: Contact) {
        someDataHolder.updateSomeData(
            oldContact.toRaw(), newContact.toRaw()
        )
    }

    override fun getContacts(): Flow<List<Contact>> {
          return someDataHolder
                .getSomeData()
                .map { someRawStringList ->
                    someRawStringList.map { someRawStrings ->
                        asContact(someRawStrings)
                    }
                }
    }
}

fun Contact.toRaw(): Triple<String, String, String> {
    return Triple(name, surname, phoneNumber)
}

fun asContact(raw: Triple<String, String, String>): Contact {
    return Contact(raw.first, raw.second, raw.third)
}