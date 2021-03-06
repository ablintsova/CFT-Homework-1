package com.example.homework1.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.homework1.R
import com.example.homework1.contacts.database.ContactDao
import com.example.homework1.contacts.database.ContactsDatabase
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : Fragment() {

    companion object {
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100

        fun newInstance() = ContactsFragment()
    }

    private var contactsAdapter = ContactRecyclerViewAdapter(mutableListOf())
    private lateinit var database: ContactsDatabase
    private lateinit var dao: ContactDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.setBackgroundColor(Color.WHITE)

        setRecyclerView()
        setDatabase()

        btnGetContacts.setOnClickListener { loadContacts() }
    }

    private fun setRecyclerView() {
        rvContacts.layoutManager = LinearLayoutManager(context)
        rvContacts.addItemDecoration(DividerItemDecoration(requireContext(),
            LinearLayoutManager.VERTICAL))
        rvContacts.adapter = contactsAdapter
    }

    private fun setDatabase() {
        database = Room.databaseBuilder(requireContext(), ContactsDatabase::class.java, "database")
            .allowMainThreadQueries() //TODO: queries in the background
            .build()
        dao = database.contactDao()
    }

    @SuppressLint("WrongConstant")
    private fun loadContacts() {
        Log.d("CONTACTS", "Enter loadContacts")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && (PackageManager.PERMISSION_GRANTED != checkSelfPermission(this.requireContext(),
                Manifest.permission.READ_CONTACTS))
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            val contactsList = getContacts()
            insertContactsToDatabase(contactsList)
            val databaseContacts = getContactsFromDatabase()
            contactsAdapter.update(databaseContacts)
        }
        Log.d("CONTACTS", "Exit loadContacts")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("CONTACTS", "Enter onRequestPermissionsResult")
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadContacts()
        } else {
            Log.d("CONTACTS", "onRequestPermissionsResult: Permission is not granted")
        }
        Log.d("CONTACTS", "Exit onRequestPermissionsResult")
    }

    private fun insertContactsToDatabase(contactsList: MutableList<Contact>) {
        database.runInTransaction {
            dao.insert(contactsList)
        }
    }

    private fun getContactsFromDatabase(): MutableList<Contact> {
        var contactsList: MutableList<Contact> = mutableListOf()
        database.runInTransaction {
            contactsList = dao.getAllContacts()
        }
        return contactsList
    }

    private fun getContacts(): MutableList<Contact> {
        Log.d("CONTACTS", "Enter getContacts")
        val contacts: MutableList<Contact> = mutableListOf()
        val resolver = context?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)
        cursor?.let {
            if (it.count > 0) {
                while (it.moveToNext()) {
                    val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (it.getString(
                        it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(id),
                            null)

                        cursorPhone?.let {
                            if (cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                        cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    val contact = Contact(id, name, phoneNumValue)
                                    contacts.add(contact)
                                    Log.d("CONTACTS", "Added $name $phoneNumValue to the list")
                                }
                            }
                            cursorPhone.close()
                        }
                    }
                }
            } else {
                Log.d("CONTACTS", "NO CONTACTS")
            }
        }
        cursor?.close()
        Log.d("CONTACTS", "Exit getContacts")
        return contacts
    }
}