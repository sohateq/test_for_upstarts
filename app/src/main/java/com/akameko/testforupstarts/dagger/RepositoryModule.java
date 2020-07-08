package com.akameko.testforupstarts.dagger;

import com.akameko.testforupstarts.repository.retrofit.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    Repository provideRepository() {
        return new Repository();
    }
}