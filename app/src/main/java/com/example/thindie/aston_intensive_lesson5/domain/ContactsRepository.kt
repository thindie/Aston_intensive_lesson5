package com.example.thindie.aston_intensive_lesson5.domain

import com.example.thindie.aston_intensive_lesson5.domain.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun updateContact(oldContact: Contact, newContact: Contact)
    fun getContacts(): Flow<List<Contact>>
}