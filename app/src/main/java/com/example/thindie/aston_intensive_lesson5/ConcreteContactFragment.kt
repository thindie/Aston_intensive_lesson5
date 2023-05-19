package com.example.thindie.aston_intensive_lesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ConcreteContactFragment : Fragment() {
    private val viewModel: ContactsScreenViewModel by activityViewModels()
    private val router: ContactRouter? by lazy { requireActivity() as? ContactRouter }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concrete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(
            ViewHolder(
                name = view.findViewById(R.id.input_name),
                surname = view.findViewById(R.id.input_surname),
                phone = view.findViewById(R.id.input_phone),
            )
        )

        val button: Button = view.findViewById(R.id.button_save)

        button.setOnClickListener {
            viewModel.onUpdateContact()
            requireNotNull(router).onShowAllContacts()
        }
    }

    private fun observe(viewHolder: ViewHolder) {
        lifecycleScope.launch {
            viewModel.contacts.collect(

            ) { contact ->
                contact
                    .contactList
                    .first()
                    .apply {

                        viewHolder.name
                            .findViewById<TextInputEditText>(R.id.text_input_edit)
                            .addTextChangedListener { changingText ->
                                viewModel.onUpdateContactName(changingText.toString())
                            }
                        viewHolder.name
                            .findViewById<TextView>(R.id.textview_input_field_id)
                            .text = getString(R.string.contact_name).plus(name)


                        viewHolder.surname
                            .findViewById<TextInputEditText>(R.id.text_input_edit)
                            .addTextChangedListener { changingText ->
                                viewModel.onUpdateContactSurname(changingText.toString())
                            }
                        viewHolder.surname
                            .findViewById<TextView>(R.id.textview_input_field_id)
                            .text = getString(R.string.contact_surname).plus(surname)

                        viewHolder.phone
                            .findViewById<TextInputEditText>(R.id.text_input_edit)
                            .addTextChangedListener { changingText ->
                                viewModel.onUpdateContactPhone(changingText.toString())
                            }
                        viewHolder.phone
                            .findViewById<TextView>(R.id.textview_input_field_id)
                            .text = getString(R.string.contact_phone).plus(phoneNumber)
                    }
            }
        }


    }

    data class ViewHolder(
        val name: LinearLayout,
        val surname: LinearLayout,
        val phone: LinearLayout,
    )

}