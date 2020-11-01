package com.example.homework1

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b.*

class FragmentB : Fragment() {

    companion object {
        fun newInstance() = FragmentB()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.setBackgroundColor(Color.WHITE)

        btnFragmentC.setOnClickListener { openFragment(FragmentC.newInstance()) }
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
