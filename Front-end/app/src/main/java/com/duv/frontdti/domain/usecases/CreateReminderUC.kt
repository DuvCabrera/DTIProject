package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository

class CreateReminderUC(private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder: Reminder): Reminder{
        return repository.createReminder(reminder)
    }
}