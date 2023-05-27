package com.duv.frontdti.presentation.main_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duv.frontdti.domain.model.ReminderByDate
import com.duv.frontdti.domain.usecases.ReminderUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val reminderUC: ReminderUC
) : ViewModel() {

    private val remindersByDateList: MutableLiveData<List<ReminderByDate>> by lazy {
        MutableLiveData<List<ReminderByDate>>()
    }

    val reminderList: LiveData<List<ReminderByDate>> get()  =  remindersByDateList

    fun getReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            remindersByDateList.postValue(
                reminderUC.getRemindersUC.invoke()
                    .groupBy { it.date }
                    .map { (date, reminder) ->
                        ReminderByDate(date, reminder)
                    })
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderUC.deleteReminderUC(id)
        }.invokeOnCompletion {
            getReminders()
        }
    }
}