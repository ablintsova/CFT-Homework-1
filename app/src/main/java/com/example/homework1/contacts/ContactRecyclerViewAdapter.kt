package com.example.homework1.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework1.R


class ContactRecyclerViewAdapter(private var values: List<Contact>) :
    RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_contacts_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = values[position]
        holder.tvName.text = contact.name
        holder.tvNumber.text = contact.number
    }

    override fun getItemCount(): Int = values.size

    fun update(contacts: List<Contact>) {
        values = contacts
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvContactName)
        val tvNumber: TextView = view.findViewById(R.id.tvContactNumber)

        override fun toString(): String {
            return super.toString() + " '" + tvName.text + " " + tvNumber.text + "'"
        }
    }
}