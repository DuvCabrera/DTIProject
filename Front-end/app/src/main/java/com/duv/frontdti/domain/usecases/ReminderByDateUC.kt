package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.model.ReminderByDate

class ReminderByDateUC {
    operator fun invoke(reminders: List<Reminder>): List<ReminderByDate> =
        reminders.groupBy { it.date }.map { (date, reminder) ->
            ReminderByDate(date, reminder)
        }
}