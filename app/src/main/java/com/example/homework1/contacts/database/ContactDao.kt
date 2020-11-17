package com.example.homework1.contacts.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework1.contacts.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contactList: MutableList<Contact>)

    @Query("SELECT * FROM contact")
    fun getAllContacts(): MutableList<Contact>
}