package com.insiderser.mars.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class IO

// You can add other schedulers if needed.

@Module
@InstallIn(ApplicationComponent::class)
object SchedulersModule {

    @Provides
    @IO
    fun provideIOScheduler(): Scheduler = Schedulers.io()
}
