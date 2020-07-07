package com.akameko.testforupstarts.repository.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.akameko.testforupstarts.repository.pojos.Jeans;

@Database(entities = {Jeans.class}, version = 1,
        exportSchema = false)
public abstract class JeansDatabase extends RoomDatabase {
    public abstract JeansDao getJeansDao();
}