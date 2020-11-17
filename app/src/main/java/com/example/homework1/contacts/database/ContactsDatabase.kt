package com.example.homework1.contacts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework1.contacts.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}