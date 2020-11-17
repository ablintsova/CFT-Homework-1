package com.example.homework1.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contact(
    @PrimaryKey
    var id: String,
    var name: String,
    var number: String
)