package com.rejowan.ottmovies.di

import com.rejowan.ottmovies.repository.MovieRepository
import com.rejowan.ottmovies.repositoryImpl.MovieRepositoryImpl
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    single<MovieRepository> { MovieRepositoryImpl() }
    viewModel { MovieViewModel(get()) }
}