package com.duv.frontdti.presentation.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duv.frontdti.R
import com.duv.frontdti.domain.model.Reminder

class MainFragmentChildAdapter(private val dataSet: List<Reminder>,
                               private val onItemClick: (Int) -> Unit,
                               private val onDeleteClick: (Int) -> Unit) :
    RecyclerView.Adapter<MainFragmentChildAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Reminder) {
            val txtView = itemView.findViewById<TextView>(R.id.tv_reminder_name)
            txtView.text = item.description
            txtView.setOnClickListener {
                onItemClick(item.id!!)
            }

            val ibDelete = itemView.findViewById<ImageButton>(R.id.ib_delete)
            ibDelete.setOnClickListener {
                onDeleteClick(item.id!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_child_item, parent, false)
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}