package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.repositories.ReminderRepository

class DeleteReminderUC(private val repository: ReminderRepository) {

    suspend operator fun invoke(id: Int){
        repository.deleteReminder(id)
    }
}