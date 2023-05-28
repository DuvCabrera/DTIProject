package com.duv.frontdti.data.repositories

import com.duv.frontdti.data.data_source.cache.ReminderDAO
import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.domain.CreateReminderException
import com.duv.frontdti.domain.DeleteReminderException
import com.duv.frontdti.domain.UpdateReminderException
import com.duv.frontdti.domain.WithoutConnectionException
import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderService: ReminderService,
    private val reminderDao: ReminderDAO
) :
    ReminderRepository {
    override suspend fun createReminder(reminder: Reminder) {
        try {
            val reminderRemote = reminderService.createReminder(reminder)
            reminderDao.insertReminder(reminderRemote)
        } catch (e: Exception) {
            throw CreateReminderException(e.message)
        }
    }

    override suspend fun updateReminder(id: Int, reminder: Reminder) {
        try {
            val remoteReminderUpdated = reminderService.updateReminder(id, reminder)
            reminderDao.updateReminder(remoteReminderUpdated)
        } catch (e: Exception) {
            throw UpdateReminderException(e.message)
        }
    }

    override suspend fun deleteReminder(id: Int) {
        try {
            reminderService.deleteReminder(id)
            val reminderToDelete = reminderDao.getReminderById(id)
            reminderDao.deleteReminder(reminderToDelete)
        } catch (e: Exception) {
            throw DeleteReminderException(e.message)
        }
    }

    override suspend fun getReminderById(id: Int): Reminder {
        return try {
            reminderService.getReminderById(id)
        } catch (e: Exception) {
           throw WithoutConnectionException(e.message)
        }
    }

    override suspend fun getAllReminders(): List<Reminder> {
        var remindersToReturn: List<Reminder>
        try {
            val remoteReminders = reminderService.getReminders()
            val localReminders = reminderDao.getReminderList()

            remindersToReturn = if (remoteReminders.isEmpty() && localReminders.isNotEmpty()) {
                for (reminder in localReminders) {
                    reminderService.createReminder(reminder)
                }
                localReminders
            } else {

                remoteReminders
            }

        } catch (e: Exception) {
            remindersToReturn = reminderDao.getReminderList()
        }
        return remindersToReturn.sortedBy { it.date }
    }
}