package com.duv.frontdti.domain.model

object ReminderFactory {
    val reminderList = listOf(
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
        Reminder(
            id = 1, date = "11/10/2022",
            description = "mock de teste"
        ),
        Reminder(
            id = 2, date = "12/10/2022",
            description = "mock de teste 2"
        )
    ).groupBy { it.date }
        .map { (date, reminder) ->
            ReminderByDate(date, reminder)

        }


}