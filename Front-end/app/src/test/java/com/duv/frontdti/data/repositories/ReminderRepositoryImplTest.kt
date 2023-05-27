package com.duv.frontdti.data.repositories

import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.ReminderNotFoundedException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.exceptions.base.MockitoException

class ReminderRepositoryImplTest {

    private val reminderServiceMock = mock(ReminderService::class.java)
    private val repository = ReminderRepositoryImpl(reminderServiceMock)

    @Test
    fun get_all_reminders_return_with_success() = runBlocking {
        val reminderMock = ReminderFactory.reminderList

        `when`(reminderServiceMock.getReminders()).thenReturn(reminderMock)

        val reminders = repository.getAllReminders()

        Assert.assertEquals(reminders.size, reminderMock.size)

    }

    @Test
    fun get_all_reminders_return_exception() = runBlocking {

        `when`(reminderServiceMock.getReminders()).thenThrow(MockitoException(""))

        val reminders = repository.getAllReminders()

        Assert.assertEquals(reminders.size, 0)

    }

    @Test
    fun get_reminder_by_id_return_with_success() = runBlocking {
        val reminderMock = ReminderFactory.reminder
        val id = 1
        `when`(reminderServiceMock.getReminderById(id)).thenReturn(reminderMock)

        val reminders = repository.getReminderById(id)

        Assert.assertEquals(reminders, reminderMock)

    }

    @Test(expected = ReminderNotFoundedException::class)
    fun get_reminder_by_id_return_with_exception() = runBlocking {

        val id = 1
        val exception = MockitoException("Not Founded")
        `when`(reminderServiceMock.getReminderById(id)).thenThrow(exception)

        val reminders = repository.getReminderById(id)

    }

    @Test
    fun delete_reminder_return_success() = runBlocking {

        val id = 1

        repository.deleteReminder(id)

        Mockito.verify(reminderServiceMock).deleteReminder(id)
    }

    @Test
    fun create_reminder_return_success() = runBlocking {
        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(reminderServiceMock.createReminder(reminder)).thenReturn(reminder)
        val result = repository.createReminder(reminder)

        Assert.assertEquals(result, reminder)
    }

    @Test
    fun update_reminder_return_success() = runBlocking {
        val reminder = ReminderFactory.reminder
        val id = 1
        `when`(reminderServiceMock.updateReminder(id, reminder)).thenReturn(reminder)
        val result = repository.updateReminder(id,reminder)

        Assert.assertEquals(result.id, reminder.id)
    }

}