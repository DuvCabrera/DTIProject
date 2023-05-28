package com.duv.frontdti.presentation.main_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.duv.frontdti.R
import com.duv.frontdti.databinding.FragmentMainBinding
import com.duv.frontdti.domain.model.ReminderByDate
import com.duv.frontdti.presentation.util.ConnectionVerifier
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageStateObserver = Observer<MainPageState> { state ->
            initPage(state)
        }

        viewModel.mainPageState.observe(this, pageStateObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReminders()
        navController = findNavController()
        binding.fabAddReminder.setOnClickListener {
            navigateToCreationPage(-1, view)
        }
        val remindersObserver = Observer<List<ReminderByDate>> { reminders ->
            initRecyclerViewAdapter(reminders, view)
        }

        viewModel.reminderList.observe(viewLifecycleOwner, remindersObserver)

        val onDeleteErrorObserver = Observer<Boolean> {
            onDeleteError(it,view)
        }

        viewModel.onDeleteError.observe(viewLifecycleOwner, onDeleteErrorObserver)
    }

    private fun initRecyclerViewAdapter(reminders: List<ReminderByDate>, view: View) {
        val adapter = MainFragmentAdapter(reminders,
            onItemClick = { id ->
                navigateToCreationPage(id, view)
            }, onDeleteClick = { id ->
                viewModel.deleteReminder(id)
            })

        val rvPai = binding.rvPai
        rvPai.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPai.adapter = adapter
    }

    private fun navigateToCreationPage(id: Int, view: View) {
        val hasConnection = ConnectionVerifier().isNetworkConnected(requireContext())
        if(hasConnection){
            viewModel.setDeleteErrorFalse()
            val action = MainFragmentDirections.actionMainFragmentToReminderCreation(id)
            navController.navigate(action)
        } else {
            val snackbar =
                Snackbar.make(view, getString(R.string.cant_navigate_without_connection), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
        }
    }

    private fun onDeleteError(isError: Boolean, view: View) {
        if (isError) {
            val snackbar =
                Snackbar.make(view, getString(R.string.cant_delete_without_connection), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
        }
    }

    private fun initPage(pageState: MainPageState) {
        val tvWithoutReminders = binding.tvWithoutReminders
        val ivReminders = binding.ivReminders
        val rvReminder = binding.rvPai
        val pbLoading = binding.pbLoading

        when (pageState) {
            MainPageState.WITH_DATA -> {
                tvWithoutReminders.visibility = View.GONE
                ivReminders.visibility = View.GONE
                rvReminder.visibility = View.VISIBLE
                pbLoading.visibility = View.GONE
            }
            MainPageState.WITHOUT_DATA -> {
                tvWithoutReminders.visibility = View.VISIBLE
                ivReminders.visibility = View.VISIBLE
                rvReminder.visibility = View.GONE
                pbLoading.visibility = View.GONE
            }
            else -> {
                tvWithoutReminders.visibility = View.GONE
                ivReminders.visibility = View.GONE
                rvReminder.visibility = View.GONE
                pbLoading.visibility = View.VISIBLE
            }
        }
    }

}