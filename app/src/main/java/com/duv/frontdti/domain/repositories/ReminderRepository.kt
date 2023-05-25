package com.duv.frontdti.domain.repositories

import com.duv.frontdti.domain.model.Reminder

interface ReminderRepository {
    fun createReminder(reminder: Reminder)

    fun updateReminder(id: Int, reminder: Reminder)

    fun deleteReminder(reminder: Reminder)

    fun getReminderById(id: Int): Reminder

    fun getAllReminders(): List<Reminder>
}