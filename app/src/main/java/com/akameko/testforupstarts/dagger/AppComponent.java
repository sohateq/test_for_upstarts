package com.akameko.testforupstarts.dagger;

import android.app.Application;

import com.akameko.testforupstarts.repository.retrofit.Repository;
import com.akameko.testforupstarts.repository.room.JeansDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class, RepositoryModule.class})
public interface AppComponent {

    //void injectMainPresenter(MainPresenter mainPresenter);

    JeansDatabase getJeansDatabase();

    Repository getRepository();

    Application getApplication();
}
