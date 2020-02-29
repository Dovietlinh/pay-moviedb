package com.example.movietv

import android.app.Activity
import android.app.Application
import com.example.movietv.di.component.DaggerAppComponent
import com.example.movietv.di.modules.LocalModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .localModule(LocalModule(this))
            .build().inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}