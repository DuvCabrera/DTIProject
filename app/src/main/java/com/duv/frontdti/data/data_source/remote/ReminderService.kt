package com.duv.frontdti.data.data_source.remote

import com.duv.frontdti.domain.model.Reminder
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReminderService {
    @GET("reminders")
    suspend fun getReminders(): List<Reminder>

    @GET("reminders/{id}")
    suspend fun getReminderById(@Path("id") id: Int): Reminder

    @POST("reminders")
    suspend fun createReminder(@Body reminder: Reminder)

    @DELETE("reminders")
    suspend fun deleteReminder(@Body reminder: Reminder)

    @PUT("reminders/{id}")
    suspend fun updateReminder(@Path("id") id: Int, @Body reminder: Reminder)

}