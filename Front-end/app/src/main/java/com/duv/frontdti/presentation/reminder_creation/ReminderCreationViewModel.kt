package com.duv.frontdti.presentation.reminder_creation

import androidx.lifecycle.LiveData
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

    val reminder: LiveData<Reminder?> get() = _reminder

    private val _date: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val date: LiveData<String> get()  = _date


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