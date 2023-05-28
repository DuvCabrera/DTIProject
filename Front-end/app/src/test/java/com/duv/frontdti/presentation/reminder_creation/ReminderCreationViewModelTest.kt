package com.duv.frontdti.presentation.reminder_creation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.*
import com.duv.frontdti.presentation.main_fragment.getOrAwaitValue
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ReminderCreationViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var reminderRepository: ReminderRepository

    @Mock
    private lateinit var reminderObserver: Observer<Reminder?>
    @Mock
    private lateinit var dateObserver: Observer<String>
    @Mock
    private lateinit var confirmErrorObserver: Observer<Boolean>


    private lateinit var viewModel: ReminderCreationViewModel
    private lateinit var reminderUCMock: ReminderUC

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        reminderUCMock = ReminderUC(
            GetRemindersUC(reminderRepository),
            GetReminderByIdUC(reminderRepository),
            DeleteReminderUC(reminderRepository),
            CreateReminderUC(reminderRepository),
            UpdateReminderUC(reminderRepository),
        )

        viewModel = ReminderCreationViewModel(reminderUCMock)
        viewModel.confirmError.observeForever(confirmErrorObserver)
        viewModel.reminder.observeForever(reminderObserver)
        viewModel.date.observeForever(dateObserver)
    }

    @Test
    fun get_reminder_with_success() = runBlocking {
        val id = 1

        whenever(reminderUCMock.getReminderByIdUC.invoke(id)).thenReturn(ReminderFactory.reminder)
        viewModel.getReminder(id)

        val value = viewModel.reminder.getOrAwaitValue()

        Assert.assertEquals(ReminderFactory.reminder, value)
        verify(reminderObserver).onChanged(ReminderFactory.reminder)
    }

    @Test
    fun save_reminder_with_success(): Unit = runBlocking {

        val reminder = ReminderFactory.reminder.copy(id = null)
        whenever(reminderUCMock.createReminderUC.invoke(reminder)).thenReturn(
            null
        )
        viewModel.saveReminder(reminder)

        delay(200)

        verify(reminderRepository).createReminder(reminder)
        verify(confirmErrorObserver).onChanged(false)

    }

    @Test
    fun save_reminder_with_error_invoke_error_observer(): Unit = runBlocking {

        val reminder = ReminderFactory.reminder.copy(id = null)
        whenever(reminderUCMock.createReminderUC.invoke(reminder)).thenThrow(
            MockitoException("")
        )
        viewModel.saveReminder(reminder)

        delay(200)

        verify(confirmErrorObserver).onChanged(true)

    }

    @Test
    fun update_reminder_with_success(): Unit = runBlocking {
        val id = 1
        val reminder = ReminderFactory.reminder
        whenever(reminderUCMock.updateReminderUC.invoke(id, reminder)).thenReturn(
            null
        )
        viewModel.updateReminder(id, reminder)

        delay(200)

        verify(reminderRepository).updateReminder(id, reminder)
        verify(confirmErrorObserver).onChanged(false)

    }

    @Test
    fun update_reminder_with_error_invoke_error_observer(): Unit = runBlocking {
        val id = 1
        val reminder = ReminderFactory.reminder
        whenever(reminderUCMock.updateReminderUC.invoke(id, reminder)).thenThrow(
            MockitoException("")
        )
        viewModel.updateReminder(id, reminder)

        delay(200)

        verify(confirmErrorObserver).onChanged(true)

    }

    @Test
    fun set_date_with_success() = runBlocking {
        val date = "22/10/1988"

        viewModel.setDate(date)

        val value = viewModel.date.getOrAwaitValue()

        Assert.assertEquals(date, value)
        verify(dateObserver).onChanged(value)
    }

}