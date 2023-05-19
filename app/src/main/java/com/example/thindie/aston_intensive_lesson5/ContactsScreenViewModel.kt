package com.example.thindie.aston_intensive_lesson5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.aston_intensive_lesson5.domain.ContactProvider
import com.example.thindie.aston_intensive_lesson5.domain.entity.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsScreenViewModel @Inject constructor(private val provider: ContactProvider) :
    ViewModel() {
    private val _contacts: MutableStateFlow<ContactsUIState> = MutableStateFlow(ContactsUIState())
    val contacts: StateFlow<ContactsUIState> = _contacts
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            ContactsUIState()
        )


    private val _focusedContactNewVersion: MutableStateFlow<Contact?> = MutableStateFlow(null)
    private val _focusedContactOldVersion: MutableStateFlow<Contact?> = MutableStateFlow(null)

    fun onFetchContact() {
        viewModelScope.launch {
            provider.onRequestContacts()
                .onEach { contacts -> _contacts.value = ContactsUIState(contacts) }
                .launchIn(this)
        }
    }

    fun onUpdateContact() {
        viewModelScope.launch {
            provider.onUpdate(
                requireNotNull(_focusedContactOldVersion.value),
                requireNotNull(_focusedContactNewVersion.value)
            )
        }
    }

    fun onUpdateContactName(name: String) {
        val contact = _focusedContactNewVersion.value
        _focusedContactNewVersion.value = requireNotNull(contact).copy(name = name)
    }

    fun onUpdateContactSurname(surname: String) {
        val contact = _focusedContactNewVersion.value
        _focusedContactNewVersion.value = requireNotNull(contact).copy(surname = surname)
    }

    fun onUpdateContactPhone(phone: String) {
        val contact = _focusedContactNewVersion.value
        _focusedContactNewVersion.value = requireNotNull(contact).copy(phoneNumber = phone)
    }

    fun onShowConcreteContact(contact: Contact) {
        viewModelScope.launch {
            _focusedContactOldVersion.value = contact
            _focusedContactNewVersion.value = contact
            _contacts.value = ContactsUIState(listOf(contact))
        }
    }

    data class ContactsUIState(
        val contactList: List<Contact> = emptyList()
    )

}