package com.example.homework1.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework1.R
import com.example.homework1.contacts.ContactsFragment
import com.example.homework1.navigation.FragmentB
import com.example.homework1.service.BgServiceFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnFragmentB.setOnClickListener { openFragment(FragmentB.newInstance()) }
        btnBgService.setOnClickListener { openFragment(BgServiceFragment.newInstance()) }
        btnContacts.setOnClickListener { openFragment(ContactsFragment.newInstance()) }
    }

    private fun openFragment(fragment: Fragment) {
        fragmentManager?.let {
            it.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
