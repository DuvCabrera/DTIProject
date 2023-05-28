package com.duv.frontdti.data.repositories

import com.duv.frontdti.data.data_source.cache.ReminderDAO
import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.domain.CreateReminderException
import com.duv.frontdti.domain.DeleteReminderException
import com.duv.frontdti.domain.UpdateReminderException
import com.duv.frontdti.domain.WithoutConnectionException
import com.duv.frontdti.domain.model.ReminderFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.exceptions.base.MockitoException

class ReminderRepositoryImplTest {

    private val reminderServiceMock = mock(ReminderService::class.java)
    private val reminderDAOMock = mock(ReminderDAO::class.java)
    private val repository = ReminderRepositoryImpl(reminderServiceMock, reminderDAOMock)

    @Test
    fun get_all_reminders_return_with_success() = runBlocking {
        val reminderMock = ReminderFactory.reminderList

        `when`(reminderServiceMock.getReminders()).thenReturn(reminderMock)
        `when`(reminderDAOMock.getReminderList()).thenReturn(reminderMock)
        val reminders = repository.getAllReminders()

        Assert.assertEquals(reminders.size, reminderMock.size)

    }

    @Test
    fun get_all_reminders_without_data_return_local_data() = runBlocking {
        val reminderMock = ReminderFactory.reminderList

        `when`(reminderServiceMock.getReminders()).thenReturn(listOf())
        `when`(reminderDAOMock.getReminderList()).thenReturn(reminderMock)
        val reminders = repository.getAllReminders()

        Assert.assertEquals(reminders.size, reminderMock.size)

    }

    @Test
    fun get_all_reminders_exception_should_return_local_data() = runBlocking {
        val reminderMock = ReminderFactory.reminderList

        `when`(reminderServiceMock.getReminders()).thenThrow(MockitoException(""))
        `when`(reminderDAOMock.getReminderList()).thenReturn(reminderMock)
        val reminders = repository.getAllReminders()

        Assert.assertEquals(reminders.size, reminderMock.size)

    }

    @Test
    fun get_reminder_by_id_return_with_success() = runBlocking {
        val reminderMock = ReminderFactory.reminder
        val id = 1
        `when`(reminderServiceMock.getReminderById(id)).thenReturn(reminderMock)

        val reminders = repository.getReminderById(id)

        Assert.assertEquals(reminders, reminderMock)

    }

    @Test(expected = WithoutConnectionException::class)
    fun get_reminder_by_id_return_with_exception(): Unit = runBlocking {

        val id = 1
        val exception = MockitoException("Not Founded")
        `when`(reminderServiceMock.getReminderById(id)).thenThrow(exception)

        repository.getReminderById(id)

    }

    @Test
    fun delete_reminder_return_success() = runBlocking {

        val id = 1

        repository.deleteReminder(id)

        verify(reminderServiceMock).deleteReminder(id)
    }

    @Test(expected = DeleteReminderException::class)
    fun delete_reminder_return_with_exception() = runBlocking {

        val id = 1
        `when`(reminderServiceMock.deleteReminder(id)).thenThrow(MockitoException(""))

        repository.deleteReminder(id)

        verify(reminderServiceMock).deleteReminder(id)
    }

    @Test
    fun create_reminder_return_success() = runBlocking {
        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(reminderServiceMock.createReminder(reminder)).thenReturn(reminder)
        `when`(reminderDAOMock.insertReminder(reminder)).thenReturn(null)

        repository.createReminder(reminder)

        verify(reminderServiceMock).createReminder(reminder)
        verify(reminderDAOMock).insertReminder(reminder)
    }

    @Test(expected = CreateReminderException::class)
    fun create_reminder_return_with_exception() = runBlocking {
        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(reminderServiceMock.createReminder(reminder)).thenThrow(MockitoException(""))
        `when`(reminderDAOMock.insertReminder(reminder)).thenReturn(null)

        repository.createReminder(reminder)
    }
    @Test
    fun update_reminder_return_success(): Unit = runBlocking {
        val reminder = ReminderFactory.reminder
        val id = 1
        `when`(reminderServiceMock.updateReminder(id, reminder)).thenReturn(reminder.copy(id = 1))
        `when`(reminderDAOMock.updateReminder( reminder.copy(id = 1))).thenReturn(null)
        repository.updateReminder(id, reminder)

        verify(reminderServiceMock).updateReminder(id, reminder)
        verify(reminderDAOMock).updateReminder( reminder.copy(id = 1))

    }

    @Test(expected = UpdateReminderException::class)
    fun update_reminder_return_with_exception() = runBlocking {
        val reminder = ReminderFactory.reminder.copy(id = null)

        `when`(reminderServiceMock.updateReminder(1,reminder)).thenThrow(MockitoException(""))
        `when`(reminderDAOMock.updateReminder(reminder)).thenReturn(null)

        repository.updateReminder(1,reminder)

    }

}