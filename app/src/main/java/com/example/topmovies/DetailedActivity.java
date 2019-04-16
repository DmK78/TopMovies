package com.example.topmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ImageView imageViewStar;

    private FavoriteMovie favoriteMovie;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemMain:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.itemFavorite:
                Intent intentToFavorite = new Intent(this,FavoriteActivity.class);
                startActivity(intentToFavorite);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

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
        imageViewStar =findViewById(R.id.imageViewAddToFavorite);

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


        setFavorite();

    }

    public void onClickChangeFavorite(View view) {
        favoriteMovie = viewModel.getFavoriteMovieByID(id);
        if(favoriteMovie==null){
            viewModel.insertFavoriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();

        } else {
            viewModel.deleteFavoriteMovie(favoriteMovie);
            Toast.makeText(this, getString(R.string.deleted_from_favorites), Toast.LENGTH_SHORT).show();



        }
setFavorite();

    }

    private void setFavorite(){
        favoriteMovie = viewModel.getFavoriteMovieByID(id);
        if (favoriteMovie==null){
            imageViewStar.setImageResource(R.drawable.stargrey);
        }else {
            imageViewStar.setImageResource(R.drawable.staryellow);
        }

    }
}
