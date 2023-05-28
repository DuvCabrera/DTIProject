package com.duv.frontdti.data.data_source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duv.frontdti.domain.model.Reminder

@Database(entities = [Reminder::class], version = 1)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDAO

    companion object {
        const val DATABASE_NAME = "reminder_db"
    }
}