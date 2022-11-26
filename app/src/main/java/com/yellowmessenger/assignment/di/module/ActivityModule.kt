package com.yellowmessenger.assignment.di.module

import com.yellowmessenger.assignment.dataSource.Repository
import com.yellowmessenger.assignment.dataSource.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ActivityModule {

    @Binds
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository
}