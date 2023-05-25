package com.duv.frontdti.presentation.reminder_creation

import androidx.lifecycle.ViewModel
import com.duv.frontdti.domain.usecases.ReminderUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderCreationViewModel @Inject constructor(
    private val reminderUC: ReminderUC
) : ViewModel() {


}