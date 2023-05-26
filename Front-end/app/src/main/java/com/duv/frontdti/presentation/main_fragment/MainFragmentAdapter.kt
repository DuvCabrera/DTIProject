package com.duv.frontdti.presentation.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duv.frontdti.R
import com.duv.frontdti.domain.model.ReminderByDate

class MainFragmentAdapter(
    private val dataSet: List<ReminderByDate>,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ReminderByDate) {
            val txtView = itemView.findViewById<TextView>(R.id.tv_reminder_date)
            val rcView = itemView.findViewById<RecyclerView>(R.id.rv_reminder_child)
            val childRecyclerViewAdapter = MainFragmentChildAdapter(item.reminderList,
                onItemClick = { id ->
                    onItemClick(id)
                }, onDeleteClick = { id ->
                    onDeleteClick(id)
                })

            rcView.layoutManager = LinearLayoutManager(itemView.context)
            rcView.adapter = childRecyclerViewAdapter

            txtView.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reminder_father_item, parent, false)
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}
