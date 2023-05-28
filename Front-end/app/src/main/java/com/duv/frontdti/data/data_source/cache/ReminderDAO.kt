package com.duv.frontdti.data.data_source.cache

import androidx.room.*
import com.duv.frontdti.domain.model.Reminder

@Dao
interface ReminderDAO {

    @Query("SELECT * FROM reminder")
    suspend fun getReminderList(): List<@JvmSuppressWildcards Reminder>

    @Query("SELECT * FROM reminder WHERE reminder.id = :id")
    suspend fun getReminderById(id: Int): Reminder

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)


}