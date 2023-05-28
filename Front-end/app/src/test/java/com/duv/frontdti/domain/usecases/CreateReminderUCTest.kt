package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CreateReminderUCTest {

    private val repositoryMock = Mockito.mock(ReminderRepository::class.java)
    private val createReminderUC = CreateReminderUC(repositoryMock)

    @Test
    fun create_reminder_uc_return_success() = runBlocking {

        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(repositoryMock.createReminder(reminder)).thenReturn(null)
        createReminderUC.invoke(reminder)

        Mockito.verify(repositoryMock).createReminder(reminder)

    }
}