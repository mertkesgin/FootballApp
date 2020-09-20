package com.example.mertkesgin.footballapp.di

import com.example.mertkesgin.footballapp.utils.ImageHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ImageModule {

    @Singleton
    @Provides
    fun provideImageHelper() =
        ImageHelper()
}