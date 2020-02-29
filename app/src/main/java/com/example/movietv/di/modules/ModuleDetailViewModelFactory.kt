package com.example.movietv.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietv.di.AppViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider


@Module(includes = [DetailModule::class])
class ModuleDetailViewModelFactory {
    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory =
        AppViewModelFactory(providers)
}