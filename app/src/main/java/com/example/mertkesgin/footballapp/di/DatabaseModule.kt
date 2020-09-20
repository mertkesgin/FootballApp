package com.example.mertkesgin.footballapp.di

import android.content.Context
import androidx.room.Room
import com.example.mertkesgin.footballapp.data.local.AppDatabase
import com.example.mertkesgin.footballapp.utils.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideMatchDao(database: AppDatabase) = database.getMatchDao()

    @Singleton
    @Provides
    fun provideTeamDao(database: AppDatabase) = database.getTeamDao()

    @Singleton
    @Provides
    fun providePlayerDao(database: AppDatabase) = database.getPlayerDao()
}