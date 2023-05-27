package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class GetRemindersUCTest {
    private val repositoryMock = Mockito.mock(ReminderRepository::class.java)
    private val getRemindersUC = GetRemindersUC(repositoryMock)
    @Test
    fun get_reminders_uc_return_success() = runBlocking {

        val reminder = ReminderFactory.reminderList

        Mockito.`when`(repositoryMock.getAllReminders()).thenReturn(reminder)

        val result = getRemindersUC.invoke()

        Mockito.verify(repositoryMock).getAllReminders()
        Assert.assertEquals(result, reminder)

    }
}