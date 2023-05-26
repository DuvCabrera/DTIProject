package com.duv.frontdti.presentation.main_fragment

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
import com.duv.frontdti.databinding.FragmentMainBinding
import com.duv.frontdti.domain.model.ReminderByDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val remindersObserver = Observer<List<ReminderByDate>> { reminders ->
            initRecyclerViewAdapter(reminders)
        }

        viewModel.reminderList.observe(this, remindersObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReminders()
        navController = findNavController()
        binding.fabAddReminder.setOnClickListener {
            navigateToCreationPage(-1)
        }
    }

    private fun initRecyclerViewAdapter(reminders: List<ReminderByDate>) {
        val adapter = MainFragmentAdapter(reminders,
            onItemClick = { id ->
                navigateToCreationPage(id)
            }, onDeleteClick = { id ->
                viewModel.deleteReminder(id)
            })

        val rvPai = binding.rvPai
        rvPai.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPai.adapter = adapter
    }

    private fun navigateToCreationPage(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToReminderCreation(id)
        navController.navigate(action)
    }

}