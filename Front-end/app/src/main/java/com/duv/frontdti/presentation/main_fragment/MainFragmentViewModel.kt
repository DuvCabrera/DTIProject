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

    private val _mainPageState: MutableLiveData<MainPageState> by lazy {
        MutableLiveData<MainPageState>()
    }
    private val _onDeleteError: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>(false)

    val reminderList: LiveData<List<ReminderByDate>> get() = remindersByDateList

    val onDeleteError: LiveData<Boolean> get() = _onDeleteError

    val mainPageState: LiveData<MainPageState> = _mainPageState

    fun getReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainPageState.postValue(MainPageState.LOADING)
            _onDeleteError.postValue(false)
            remindersByDateList.postValue(
                reminderUC.getRemindersUC.invoke()
                    .groupBy { it.date }
                    .map { (date, reminder) ->
                        ReminderByDate(date, reminder)
                    }.also {
                        if (it.isNotEmpty()) _mainPageState.postValue(MainPageState.WITH_DATA)
                        else _mainPageState.postValue(MainPageState.WITHOUT_DATA)
                    })
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                reminderUC.deleteReminderUC(id)
                _onDeleteError.postValue(false)
                getReminders()
            } catch (e: Exception) {
                _onDeleteError.postValue(true)
            }
        }
    }

    fun setDeleteErrorFalse(){
        _onDeleteError.value = false
    }

}