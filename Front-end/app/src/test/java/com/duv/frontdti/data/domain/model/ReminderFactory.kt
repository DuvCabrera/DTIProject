package com.duv.frontdti.data.domain.model

import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.model.ReminderByDate

object ReminderFactory {
    val reminderList = listOf<Reminder>(
        Reminder(
            id = 1, date = "11/10/2022",
            description = "mock de teste"
        ),
        Reminder(
            id = 2, date = "12/10/2022",
            description = "mock de teste 2"
        )
    )
    val reminder = Reminder(
        id = 1, date = "11/10/2022",
        description = "mock de teste"
    )

    val reminderByDateList = listOf(
        ReminderByDate(date = "11/10/2022", reminderList= listOf( Reminder(
            id = 1, date = "11/10/2022",
            description = "mock de teste"
        ))),
        ReminderByDate(date = "12/10/2022", reminderList= listOf( Reminder(
            id = 2, date = "12/10/2022",
            description = "mock de teste 2"
        )) )

    )
}