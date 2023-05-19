package com.example.thindie.aston_intensive_lesson5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.window.layout.WindowMetricsCalculator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ContactRouter {
    private var isWide: Boolean = false
    private var currentDestination: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isWide = checkIsWide()
        currentDestination = savedInstanceState?.getString(DESTINATION)
        if (currentDestination == null) onShowAllContacts()
        else restoreRoute()
    }

    override val onShowAllContacts: () -> Unit
        get() = {
            currentDestination = ContactsListFragment::class.java.name
            supportFragmentManager.beginTransaction()
                .replace(
                     R.id.activity_fragment_container,
                    ContactsListFragment(),
                    currentDestination
                )
                .commit()
            supportFragmentManager.popBackStack()
        }
    override val onChoseContact: () -> Unit
        get() = {
            currentDestination = ConcreteContactFragment::class.java.name
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_fragment_container,
                    ConcreteContactFragment(),
                    currentDestination
                )
                .addToBackStack(null)
                .commit()
        }
    private fun checkIsWide(): Boolean{
        return findViewById<FragmentContainerView>(R.id.activity_fragment_container) == null
    }


    private fun restoreRoute() {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.activity_fragment_container,
                requireNotNull(supportFragmentManager.findFragmentByTag(currentDestination))
            ).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(DESTINATION, currentDestination)
    }

    companion object {
        private const val DESTINATION = "destination"
    }
}

interface ContactRouter {
    val onChoseContact: () -> Unit
    val onShowAllContacts: () -> Unit
}
