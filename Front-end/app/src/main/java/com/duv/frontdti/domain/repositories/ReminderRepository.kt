package com.duv.frontdti.domain.repositories

import com.duv.frontdti.domain.model.Reminder

interface ReminderRepository {
    suspend fun createReminder(reminder: Reminder)

    suspend fun updateReminder(id: Int, reminder: Reminder)

    suspend fun deleteReminder(id: Int)

    suspend fun getReminderById(id: Int): Reminder

    suspend fun getAllReminders(): List<Reminder>
}