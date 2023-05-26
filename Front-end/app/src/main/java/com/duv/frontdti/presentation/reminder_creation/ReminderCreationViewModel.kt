package com.duv.frontdti.presentation.reminder_creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duv.frontdti.domain.model.Reminder
import com.duv.frontdti.domain.usecases.ReminderUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderCreationViewModel @Inject constructor(
    private val reminderUC: ReminderUC
) : ViewModel() {

    private val _reminder: MutableLiveData<Reminder?> by lazy {
        MutableLiveData<Reminder?>()
    }

    val reminder = _reminder

    private val _date: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val date = _date

    private val _name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    fun getReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _reminder.postValue(reminderUC.getReminderByIdUC.invoke(id))
        }
    }

    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderUC.createReminderUC.invoke(reminder)
        }
    }

    fun updateReminder(id: Int, reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderUC.updateReminderUC.invoke(id, reminder)
        }
    }

    fun setDate(date: String) {
        _date.value = date
    }


}