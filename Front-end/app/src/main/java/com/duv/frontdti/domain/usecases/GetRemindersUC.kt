package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository

class GetRemindersUC(private val repository: ReminderRepository) {
    suspend operator fun invoke(): List<Reminder>{
        return repository.getAllReminders()
    }
}