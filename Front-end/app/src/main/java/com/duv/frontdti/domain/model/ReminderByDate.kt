package com.duv.frontdti.domain.model

data class ReminderByDate(
    val date: String,
    val reminderList: List<Reminder>
)