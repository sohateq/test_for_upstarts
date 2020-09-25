package com.akameko.testforupstarts.dagger

import com.akameko.testforupstarts.repository.retrofit.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }
}