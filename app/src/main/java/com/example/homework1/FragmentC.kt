package com.example.homework1

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_c.*

class FragmentC : Fragment() {

    companion object {
        fun newInstance() = FragmentC()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.setBackgroundColor(Color.WHITE)

        btnMainFragment.setOnClickListener { clearBackStack() }
    }

    private fun clearBackStack() {
        fragmentManager?.let {
            if (it.backStackEntryCount > 0) {
                it.popBackStack(
                    it.getBackStackEntryAt(0).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }
    }
}