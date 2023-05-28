package com.duv.frontdti.domain.usecases

import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UpdateReminderUCTest {

    private val repositoryMock = mock(ReminderRepository::class.java)
    private val updateReminderUC = UpdateReminderUC(repositoryMock)
    @Test
    fun update_reminder_uc_return_success() = runBlocking {
        val id = 1
        val reminder = ReminderFactory.reminder

        `when`(repositoryMock.updateReminder(id, reminder)).thenReturn(null)

        updateReminderUC.invoke(id, reminder)

        Mockito.verify(repositoryMock).updateReminder(id, reminder)


    }
}