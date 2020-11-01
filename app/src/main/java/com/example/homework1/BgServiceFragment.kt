package com.example.homework1

import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_bg_service.*
import java.lang.Class as JavaLangClass

class BgServiceFragment : Fragment() {

    companion object {
        fun newInstance() = BgServiceFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bg_service, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.setBackgroundColor(Color.WHITE)

        val intent = Intent(context, BgService::class.java)

        btnStartService.setOnClickListener {
            context?.startService(intent)
            btnStartService.isEnabled = false
        }

        btnStopService.setOnClickListener {
            context?.stopService(intent)
            btnStartService.isEnabled = true
        }
    }

    /*private fun isServiceRunning(serviceClass: JavaLangClass<*>): Boolean {
        val service = ActivityManager.RunningServiceInfo().service ?: return false
        return service.className == serviceClass.name
    }*/


}