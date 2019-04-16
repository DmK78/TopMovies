package com.example.topmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class,FavoriteMovie.class},version = 2,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase database;
    private static final String DB_NAME = "movies.db";
    private static final Object Lock = new Object();

    public static MovieDatabase detInstance(Context context) {
        synchronized (Lock) {
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }

    public abstract MovieDao movieDao();

}
