package com.duv.frontdti.data.domain.usecases

import com.duv.frontdti.data.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.UpdateReminderUC
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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

        `when`(repositoryMock.updateReminder(id, reminder)).thenReturn(reminder)

        val result = updateReminderUC.invoke(id, reminder)

        Mockito.verify(repositoryMock).updateReminder(id, reminder)
        Assert.assertEquals(result, reminder)

    }
}