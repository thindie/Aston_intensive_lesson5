package com.example.thindie.aston_intensive_lesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsListFragment : Fragment() {
    private val viewModel: ContactsScreenViewModel by activityViewModels()
    private val router: ContactRouter? by lazy { requireActivity() as? ContactRouter }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.onFetchContact()
        return inflater.inflate(R.layout.fragment_all, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val persons: List<LinearLayout> = listOf(
            view.findViewById(R.id.first_person),
            view.findViewById(R.id.second_person),
            view.findViewById(R.id.third_person),
        )
        observe(persons)
    }

    private fun observe(persons: List<LinearLayout>) {

        lifecycleScope.launch {
            viewModel.contacts.collect() { contacts ->
                for (index in 0 until contacts.contactList.size) {
                    persons[index]
                        .findViewById<TextView>(R.id.textview_name)
                        .text = contacts.contactList[index].name

                    persons[index]
                        .findViewById<TextView>(R.id.textview_surname)
                        .text = contacts.contactList[index].surname

                    persons[index]
                        .findViewById<TextView>(R.id.textview_phone)
                        .text = contacts.contactList[index].phoneNumber

                    persons[index].setOnClickListener {
                        requireNotNull(router).onChoseContact()
                        viewModel.onShowConcreteContact(contacts.contactList[index])
                    }
                }
            }
        }

    }

}