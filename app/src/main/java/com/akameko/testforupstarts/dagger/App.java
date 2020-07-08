package com.akameko.testforupstarts.dagger;

import android.app.Application;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .roomModule(new RoomModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

}