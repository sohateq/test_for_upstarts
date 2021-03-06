package com.akameko.testforupstarts.dagger

import android.app.Application
import com.akameko.testforupstarts.ui.SharedViewModel
import com.akameko.testforupstarts.repository.retrofit.Repository
import com.akameko.testforupstarts.repository.room.JeansDatabase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class, RepositoryModule::class])
interface AppComponent {
    fun injectSharedViewModel(sharedViewModel: SharedViewModel)

    val jeansDatabase: JeansDatabase

    val repository: Repository

    val application: Application
}