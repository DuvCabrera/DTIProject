package com.duv.frontdti.data.repositories

import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor (private val reminderService: ReminderService) : ReminderRepository {
    override suspend fun createReminder(reminder: Reminder) {
        reminderService.createReminder(reminder)
    }

    override suspend fun updateReminder(id: Int, reminder: Reminder) {
        reminderService.updateReminder(id,reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderService.deleteReminder(reminder)
    }

    override suspend fun getReminderById(id: Int): Reminder {
        return reminderService.getReminderById(id)
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return reminderService.getReminders()
    }
}