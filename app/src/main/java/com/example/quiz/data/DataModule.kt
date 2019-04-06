package com.example.quiz.data

import com.example.quiz.data.network.NetworkModule
import com.example.quiz.data.repository.RepositoryBinder
import dagger.Module

@Module(includes = [NetworkModule::class, RepositoryBinder::class])
class DataModule {


}