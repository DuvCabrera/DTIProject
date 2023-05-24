package com.duv.internproject.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "reminders")
data class Reminder(
    @Id @GeneratedValue
    val id: Long? = null,
    val date: String,
    val description: String
)
