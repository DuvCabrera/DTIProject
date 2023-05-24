package com.duv.internproject.repository

import com.duv.internproject.model.Reminder
import org.springframework.data.jpa.repository.JpaRepository

interface ReminderRepository : JpaRepository<Reminder, Long> {
}