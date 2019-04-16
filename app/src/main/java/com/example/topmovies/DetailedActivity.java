package com.example.topmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topmovies.data.FavoriteMovie;
import com.example.topmovies.data.MainViewModel;
import com.example.topmovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private int id;
    private MainViewModel viewModel;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewOriginalTitle=findViewById(R.id.textViewOriginalTitle);
        textViewRating=findViewById(R.id.textViewRating);
        textViewReleaseDate=findViewById(R.id.textViewReleaseDate);
        textViewOverview=findViewById(R.id.textViewOverview);
        Intent intent = getIntent();
        if (intent!=null && intent.hasExtra("id")){
            id = intent.getIntExtra("id",-1);
        } else {
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = viewModel.getMovieByID(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(String.valueOf(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());

    }

    public void onClickChangeFavorite(View view) {
        FavoriteMovie favoriteMovie = viewModel.getFavoriteMovieByID(id);
        if(favoriteMovie==null){
            viewModel.insertFavoriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavoriteMovie(favoriteMovie);
            Toast.makeText(this, getString(R.string.deleted_from_favorites), Toast.LENGTH_SHORT).show();
            /////////////11111
        }


    }
}
