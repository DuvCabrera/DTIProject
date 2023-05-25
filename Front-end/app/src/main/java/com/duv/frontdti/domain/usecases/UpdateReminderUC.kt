package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository

class UpdateReminderUC(private val repository: ReminderRepository) {

    suspend operator fun invoke(id: Int, reminder: Reminder){
        repository.updateReminder(id, reminder)
    }
}