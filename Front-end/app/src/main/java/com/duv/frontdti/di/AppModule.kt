package com.duv.frontdti.di

import com.duv.frontdti.data.data_source.remote.ReminderService
import com.duv.frontdti.data.repositories.ReminderRepositoryImpl
import com.duv.frontdti.domain.repositories.ReminderRepository
import com.duv.frontdti.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReminderService(): ReminderService =
        Retrofit.Builder().baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ReminderService::class.java)

    @Provides
    @Singleton
    fun provideReminderRepository(service: ReminderService): ReminderRepository =
        ReminderRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUC =
        ReminderUC(
            createReminderUC = CreateReminderUC(repository),
            deleteReminderUC = DeleteReminderUC(repository),
            getRemindersUC = GetRemindersUC(repository),
            getReminderByIdUC = GetReminderByIdUC(repository),
            updateReminderUC = UpdateReminderUC(repository)
        )
}