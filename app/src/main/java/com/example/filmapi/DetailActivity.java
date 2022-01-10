package com.example.filmapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmapi.Response.MovieDto;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        MovieDto movie = (MovieDto) intent.getSerializableExtra("movie");

        TextView tvDes = findViewById(R.id.tvDescription);
        ImageView ivCover = findViewById(R.id.ivCover);
        tvDes.setText(movie.getDescription());
        Glide.with(this).load(movie.getThumbnail()).into(ivCover);
    }
}