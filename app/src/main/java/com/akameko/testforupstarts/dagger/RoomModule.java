package com.akameko.testforupstarts.dagger;

import android.app.Application;

import androidx.room.Room;

import com.akameko.testforupstarts.repository.room.JeansDao;
import com.akameko.testforupstarts.repository.room.JeansDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private JeansDatabase db;

    public RoomModule(Application application) {
        db = Room.databaseBuilder(application.getApplicationContext(), JeansDatabase.class, "database").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    JeansDatabase provideJeansDatabase() {
        return db;
    }

    @Singleton
    @Provides
    public JeansDao provideJeansDao(JeansDatabase jeansDatabase){
        return db.getJeansDao();
    }
}