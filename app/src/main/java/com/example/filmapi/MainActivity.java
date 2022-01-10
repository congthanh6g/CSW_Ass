package com.example.filmapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.filmapi.Adapter.MovieAdapter;
import com.example.filmapi.Model.Movie;
import com.example.filmapi.Network.ApiManager;
import com.example.filmapi.Response.BaseResponseDto;
import com.example.filmapi.Response.HomeContentDto;
import com.example.filmapi.Response.MovieDto;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // b1 :
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image};
    List<MovieDto> listMovie = new ArrayList<>();
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCarouselView();
        initRecycleView();
        initData();
    }

// BI : carouselview home
    private void initCarouselView()
    {
        //b2 :
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length); // tra ve so anh

        //b3 : click
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this , DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecycleView()
    {
        // b1 : chuan bi nguon du lieu

        // b2 : chuan bi adapter (nhan datasource + layout )
        adapter = new MovieAdapter(this , listMovie);

        // b3 : chuan bi layoutmanager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);
        // b4 : chuan bi recyclerview
        RecyclerView rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(layoutManager);
        rvTrending.setHasFixedSize(true);
        rvTrending.setAdapter(adapter);
    }


    // goi api
    private void initData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager service = retrofit.create(ApiManager.class);
        service.apiHome().enqueue(new Callback<BaseResponseDto<HomeContentDto>>() {
            @Override
            public void onResponse(Call<BaseResponseDto<HomeContentDto>> call, Response<BaseResponseDto<HomeContentDto>> response) {
               BaseResponseDto<HomeContentDto> model = response.body();
               HomeContentDto homeContentDto = model.getData();
               listMovie = homeContentDto.getListSuggest();
               adapter.reloadData(listMovie);
            }
            @Override
            public void onFailure(Call<BaseResponseDto<HomeContentDto>> call, Throwable t) {

            }
        });
    }
}