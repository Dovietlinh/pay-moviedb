package com.example.movietv.di.component

import com.example.movietv.MyApplication
import com.example.movietv.di.modules.LocalModule
import com.example.movietv.di.modules.ModuleDetailViewModelFactory
import com.example.movietv.di.modules.RemoteModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [RemoteModule::class, LocalModule::class,
        AndroidInjectionModule::class, ModuleDetailViewModelFactory::class]
)
@Singleton
interface AppComponent {
    fun inject(application: MyApplication)
}
