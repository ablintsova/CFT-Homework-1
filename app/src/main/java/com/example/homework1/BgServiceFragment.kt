package com.example.homework1

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_bg_service.*

class BgServiceFragment : Fragment() {

    companion object {
        fun newInstance() = BgServiceFragment()
    }

    private val receiver = CustomReceiver(::updateUI)

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

        configureReceiver()
    }

    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        context?.registerReceiver(receiver, filter)
    }

    private fun updateUI(text: String) {
        tvServiceState.text = text
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)
    }

    /*private fun isServiceRunning(serviceClass: JavaLangClass<*>): Boolean {
        val service = ActivityManager.RunningServiceInfo().service ?: return false
        return service.className == serviceClass.name
    }*/
}