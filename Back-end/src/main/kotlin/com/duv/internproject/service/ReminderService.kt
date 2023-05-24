package com.duv.internproject.service

import com.duv.internproject.model.Reminder
import java.util.*

interface ReminderService {
    fun create(reminder: Reminder): Reminder

    fun getAll(): List<Reminder>

    fun getById(id: Long): Optional<Reminder>

    fun update(id: Long, reminder: Reminder): Optional<Reminder>

    fun delete(id: Long)
}