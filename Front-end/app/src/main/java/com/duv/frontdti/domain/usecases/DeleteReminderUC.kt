package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository

class DeleteReminderUC(private val repository: ReminderRepository) {

    suspend operator fun invoke(reminder: Reminder){
        repository.deleteReminder(reminder)
    }
}