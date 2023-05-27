package com.duv.frontdti.data.domain.usecases

import com.duv.frontdti.data.domain.model.ReminderFactory
import com.duv.frontdti.domain.usecases.ReminderByDateUC
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ReminderByDateUCTest {

    @Test
    fun reminder_by_date_uc_return_success() = runBlocking {
        val reminder = ReminderFactory.reminderList
        val reminderByDate = ReminderFactory.reminderByDateList

        val result = ReminderByDateUC().invoke(reminder)

        Assert.assertEquals(result, reminderByDate)
    }
}