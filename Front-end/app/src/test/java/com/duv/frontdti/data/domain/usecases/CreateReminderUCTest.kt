package com.duv.frontdti.data.domain.usecases

import com.duv.frontdti.data.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.CreateReminderUC
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CreateReminderUCTest {

    private val repositoryMock = Mockito.mock(ReminderRepository::class.java)
    private val createReminderUC = CreateReminderUC(repositoryMock)

    @Test
    fun create_reminder_uc_return_success() = runBlocking {

        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(repositoryMock.createReminder(reminder)).thenReturn(ReminderFactory.reminder)
        val result = createReminderUC.invoke(reminder)

        Mockito.verify(repositoryMock).createReminder(reminder)
        Assert.assertEquals(result.date, ReminderFactory.reminder.date)

    }
}