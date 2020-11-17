package com.example.homework1.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CustomReceiver(private val onUpdate: (String) -> Unit) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val text = p1?.getStringExtra("service_state").toString()
        onUpdate(text)
    }
}