package com.duv.frontdti.data.domain.usecases

import com.duv.frontdti.data.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.GetReminderByIdUC
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class GetReminderByIdUCTest {
    private val repositoryMock = Mockito.mock(ReminderRepository::class.java)
    private val getReminderByIdUC = GetReminderByIdUC(repositoryMock)
    @Test
    fun get_reminder_by_id_uc_return_success() = runBlocking {
        val id = 1
        val reminder = ReminderFactory.reminder

        Mockito.`when`(repositoryMock.getReminderById(id)).thenReturn(reminder)

        val result = getReminderByIdUC.invoke(id)

        Mockito.verify(repositoryMock).getReminderById(id)
        Assert.assertEquals(result, reminder)

    }
}