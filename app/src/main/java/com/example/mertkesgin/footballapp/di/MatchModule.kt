package com.example.mertkesgin.footballapp.di

import android.content.Context
import android.widget.ArrayAdapter
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.MatchesAdapter
import com.example.mertkesgin.footballapp.adapters.PlayerAdapter
import com.example.mertkesgin.footballapp.adapters.TeamsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MatchModule {

    @Provides
    fun provideMatchesAdapter() = MatchesAdapter()

    @Provides
    fun provideTeamsAdapter() = TeamsAdapter()

    @Provides
    fun providePlayerAdapter() = PlayerAdapter()

    @Provides
    fun provideSpinnerAdapter(@ApplicationContext context: Context) =
        ArrayAdapter.createFromResource(
            context,
            R.array.leagues,
            android.R.layout.simple_spinner_item
        )
}