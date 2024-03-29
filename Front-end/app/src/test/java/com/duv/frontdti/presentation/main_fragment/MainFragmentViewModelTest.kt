package com.duv.frontdti.presentation.main_fragment

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.duv.frontdti.domain.model.ReminderByDate
import com.duv.frontdti.domain.model.ReminderFactory
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.*
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@Config(manifest= Config.NONE)
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainFragmentViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var reminderRepository: ReminderRepository

    @Mock
    private lateinit var observeReminderList: Observer<List<ReminderByDate>>
    @Mock
    private lateinit var observeMainPageState: Observer<MainPageState>
    @Mock
    private lateinit var observeOnDeleteError: Observer<Boolean>

    private lateinit var viewModel: MainFragmentViewModel
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

        viewModel = MainFragmentViewModel(reminderUCMock)
        viewModel.onDeleteError.observeForever(observeOnDeleteError)
        viewModel.mainPageState.observeForever(observeMainPageState)
        viewModel.reminderList.observeForever(observeReminderList)

    }


    @Test
    fun get_reminders_with_success() = runBlocking {

        whenever(reminderUCMock.getRemindersUC.invoke())
            .thenReturn(ReminderFactory.reminderList)

        viewModel.getReminders()

        val value = viewModel.reminderList.getOrAwaitValue()

        verify(observeOnDeleteError, times(2)).onChanged(false)
        verify(observeMainPageState).onChanged(MainPageState.LOADING)
        verify(observeMainPageState).onChanged(MainPageState.WITH_DATA)
        Assert.assertEquals(ReminderFactory.reminderByDateList, value)
    }
    @Test
    fun get_reminders_without_data_invoke_main_page_observer() = runBlocking {

        whenever(reminderUCMock.getRemindersUC.invoke())
            .thenReturn(listOf())

        viewModel.getReminders()
        delay(200)

        verify(observeMainPageState).onChanged(MainPageState.WITHOUT_DATA)
    }
    @Test
    fun delete_reminders_with_success() = runBlocking {
        val id = 1
        whenever(reminderUCMock.deleteReminderUC(id)).thenReturn(null)
        whenever(reminderUCMock.getRemindersUC.invoke())
            .thenReturn(ReminderFactory.reminderList)

        viewModel.deleteReminder(id)

        val value = viewModel.reminderList.getOrAwaitValue()
        verify(observeReminderList).onChanged(ReminderFactory.reminderByDateList)
        verify(observeOnDeleteError, times(3)).onChanged(false)
        Assert.assertEquals(ReminderFactory.reminderByDateList, value)
    }
    @Test
    fun set_delete_error_false() = runBlocking {
        viewModel.setDeleteErrorFalse()

        verify(observeOnDeleteError, times(2)).onChanged(false)
    }

}


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserver: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserver.invoke()

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set")
        }
    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}