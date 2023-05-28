package com.duv.frontdti.presentation.reminder_creation

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.duv.frontdti.R
import com.duv.frontdti.databinding.FragmentReminderCreationBinding
import com.duv.frontdti.domain.model.Reminder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReminderCreation : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentReminderCreationBinding
    private lateinit var navController: NavController
    private val args: ReminderCreationArgs by navArgs()
    private val viewModel: ReminderCreationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argsValue = args.reminderId
        if (argsValue != -1) viewModel.getReminder(argsValue)
        navController = findNavController()


        val reminderObserver = Observer<Reminder?> {
            insertReminderInfo(it!!)
        }

        val dateObserver = Observer<String> {
            setDate(it)
        }

        viewModel.reminder.observe(this, reminderObserver)
        viewModel.date.observe(this, dateObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReminderCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onConfirmErrorObserver = Observer<Boolean> {
            confirmHasError(it, view)
        }

        viewModel.confirmError.observe(viewLifecycleOwner, onConfirmErrorObserver)

        binding.ibBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.tvDate.setOnClickListener {
            showDatePikerDialog()
        }
        binding.ibCalendar.setOnClickListener {
            showDatePikerDialog()
        }
        binding.btConfirm.setOnClickListener {
            confirmReminderCheck(view)
        }
        binding.llTextBox.setOnClickListener {
            binding.etName.requestFocus()
        }
    }

    private fun insertReminderInfo(reminder: Reminder) {
        binding.tvDate.text = reminder.date
        binding.etName.setText(reminder.description)
    }

    private fun showDatePikerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, dayOfMonth)

        datePickerDialog.datePicker.minDate = currentDate.timeInMillis + 24 * 60 * 60 * 1000

        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, day)
        val formattedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)

        viewModel.setDate(formattedDate.toString())
    }

    private fun setDate(date: String) {
        binding.tvDate.text = date
    }

    private fun confirmReminderCheck(view: View) {
        val date = binding.tvDate.text
        val name = binding.etName.text
        if (date.isNotEmpty() && name.isNotEmpty()) {
            val id = args.reminderId
            if (id != -1) viewModel.updateReminder(
                id,
                Reminder(
                    id = id,
                    date = date.toString(),
                    description = name.toString()
                )
            ) else viewModel.saveReminder(
                Reminder(
                    date = date.toString(),
                    description = name.toString()
                )
            )

        } else {
            val snackbar =
                Snackbar.make(view, getString(R.string.empty_field), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }
    }

    private fun confirmHasError(hasError: Boolean, view: View) {
        if(hasError){
            val snackbar =
                Snackbar.make(view, getString(R.string.impossible_without_connection), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        } else {
            navController.popBackStack()
        }
    }


}