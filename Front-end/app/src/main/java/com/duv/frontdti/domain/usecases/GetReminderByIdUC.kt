package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.repositories.ReminderRepository

class GetReminderByIdUC(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Int): Reminder{
        return repository.getReminderById(id)
    }
}