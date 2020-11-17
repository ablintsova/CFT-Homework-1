package com.example.homework1.service

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.homework1.R
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
            configureReceiver()
            btnStartService.isEnabled = false
        }

        btnStopService.setOnClickListener {
            context?.stopService(intent)
            LocalBroadcastManager.getInstance(this.requireContext()).unregisterReceiver(receiver)
            updateUI(getString(R.string.finished_to_count_manuls))
            btnStartService.isEnabled = true
        }
    }

    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("send_broadcast")
        LocalBroadcastManager.getInstance(this.requireContext()).registerReceiver(receiver, filter)
    }

    private fun updateUI(text: String) {
        tvServiceState.text = text
    }

    // Just in case
    /*private fun isServiceRunning(serviceClass: JavaLangClass<*>): Boolean {
        val service = ActivityManager.RunningServiceInfo().service ?: return false
        return service.className == serviceClass.name
    }*/
}