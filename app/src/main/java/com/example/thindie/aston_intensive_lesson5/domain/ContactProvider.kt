package com.example.thindie.aston_intensive_lesson5.domain

import com.example.thindie.aston_intensive_lesson5.domain.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactProvider {
    fun onUpdate(oldContact: Contact, newContact: Contact)
    fun onRequestContacts(): Flow<List<Contact>>
}