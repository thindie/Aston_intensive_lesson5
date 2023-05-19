package com.example.thindie.aston_intensive_lesson5.domain

import com.example.thindie.aston_intensive_lesson5.di.DispatchersModule
import com.example.thindie.aston_intensive_lesson5.domain.entity.Contact
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class ContactsUseCases @Inject constructor(
    private val repository: ContactsRepository,
    @DispatchersModule.IODispatcher val dispatchersIO: CoroutineDispatcher
) :
    ContactProvider {
    override fun onUpdate(oldContact: Contact, newContact: Contact) {
        repository.updateContact(oldContact, newContact)
    }

    override fun onRequestContacts(): Flow<List<Contact>> {
        return repository
            .getContacts()
            .flowOn(dispatchersIO)
    }
}