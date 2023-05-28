package com.duv.frontdti.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val date: String,
    val description: String
)
