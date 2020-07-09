package com.akameko.testforupstarts.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var application: Application) {
    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

}