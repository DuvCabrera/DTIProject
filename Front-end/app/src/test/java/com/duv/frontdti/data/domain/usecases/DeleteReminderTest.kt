package com.duv.frontdti.data.domain.usecases

import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.DeleteReminderUC
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class DeleteReminderTest {
    private val repositoryMock = Mockito.mock(ReminderRepository::class.java)
    private val deleteReminderUC = DeleteReminderUC(repositoryMock)

    @Test
    fun delete_reminder_uc_return_success() = runBlocking {
        val id = 1

        Mockito.`when`(repositoryMock.deleteReminder(id)).thenReturn(null)
        deleteReminderUC.invoke(id)

        Mockito.verify(repositoryMock).deleteReminder(id)

    }
}