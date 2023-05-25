package com.duv.frontdti.domain.usecases

data class ReminderUC(
    val getRemindersUC: GetRemindersUC,
    val getReminderByIdUC: GetReminderByIdUC,
    val deleteReminderUC: DeleteReminderUC,
    val createReminderUC: CreateReminderUC,
    val updateReminderUC: UpdateReminderUC,
    val reminderByDateUC: ReminderByDateUC
)

