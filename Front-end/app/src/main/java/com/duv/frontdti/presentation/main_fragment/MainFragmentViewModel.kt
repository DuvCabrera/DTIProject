package com.duv.frontdti.presentation.main_fragment

import androidx.lifecycle.ViewModel
import com.duv.frontdti.domain.usecases.ReminderUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val reminderUC: ReminderUC
) : ViewModel() {
}