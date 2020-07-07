package com.akameko.testforupstarts.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.akameko.testforupstarts.repository.pojos.Jeans;

import java.util.List;

@Dao
public interface JeansDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Jeans> jeanses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Jeans jeans);

    @Delete
    void delete(Jeans jeans);

    @Query("DELETE FROM jeans")
    void deleteAll();

    @Query("SELECT * FROM jeans")
    List<Jeans> getAllItems();

    @Query("SELECT * FROM jeans WHERE id == :id")
    Jeans getItemById(Integer id);
}
