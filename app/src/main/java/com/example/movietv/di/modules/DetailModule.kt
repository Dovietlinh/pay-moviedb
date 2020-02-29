package com.example.movietv.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.movietv.di.ViewModelKey
import com.example.movietv.repository.movieDetailsRepository.MovieDetailsRepository
import com.example.movietv.view.DetailsActivity
import com.example.movietv.viewModel.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [DetailModule.ProvideViewModel::class])
abstract class DetailModule {
    @ContributesAndroidInjector(
        modules = [InjectViewModel::class]
    )
    abstract fun bind(): DetailsActivity
    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(DetailsViewModel::class)
        fun provideDetailViewModel(movieDetailsRepository: MovieDetailsRepository): ViewModel =
            DetailsViewModel(movieDetailsRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideAddNoteViewModel(
            factory: ViewModelProvider.Factory,
            target: DetailsActivity
        ) = ViewModelProviders.of(target, factory).get(DetailsViewModel::class.java)
    }
}
