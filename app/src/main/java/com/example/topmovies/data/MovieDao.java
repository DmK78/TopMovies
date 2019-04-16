package com.example.topmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovies();

    @Query("SELECT * FROM movies WHERE id==:movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM favorite_movies WHERE id==:movieId")
    FavoriteMovie getFavoriteMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deletAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertFavoriteMovie(FavoriteMovie movie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie movie);
}
