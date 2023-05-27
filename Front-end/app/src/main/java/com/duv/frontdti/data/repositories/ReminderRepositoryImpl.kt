package com.duv.frontdti.data.repositories

import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.domain.ReminderNotFoundedException
import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor (private val reminderService: ReminderService) : ReminderRepository {
    override suspend fun createReminder(reminder: Reminder): Reminder {
        return reminderService.createReminder(reminder)
    }

    override suspend fun updateReminder(id: Int, reminder: Reminder): Reminder {
        return reminderService.updateReminder(id,reminder)
    }

    override suspend fun deleteReminder(id: Int) {
        reminderService.deleteReminder(id)
    }

    override suspend fun getReminderById(id: Int): Reminder {
        return try {
            reminderService.getReminderById(id)
        } catch (e: Exception){
            throw ReminderNotFoundedException(e.message)
        }
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return try {
            reminderService.getReminders()
        } catch (e: Exception) {
            listOf()
        }
    }
}