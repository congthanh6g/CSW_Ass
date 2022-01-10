package com.example.filmapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.filmapi.Adapter.MovieAdapter;
import com.example.filmapi.Adapter.SectionAdapter;
import com.example.filmapi.Model.Section;
import com.example.filmapi.Network.ApiManager;
import com.example.filmapi.Response.BaseResponseDto;
import com.example.filmapi.Response.HomeContentDto;
import com.example.filmapi.Response.MovieDto;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image};
    List<MovieDto> listMovie = new ArrayList<>();
    List<Section> listSection = new ArrayList<>();
    RecyclerView rvHome;
    SectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initCarouselView();
        initView();
    }

    private void initView()
    {
        //b1
        callApi();
        //b2
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);

       sectionAdapter = new SectionAdapter(this , listSection);

        rvHome = findViewById(R.id.rvHome);
        rvHome.setLayoutManager(layoutManager);
        rvHome.setHasFixedSize(true);
        rvHome.setAdapter(sectionAdapter);

    }

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
                Toast.makeText(HomeActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this , DetailActivity.class);
                startActivity(intent);
            }
        });
    }


    private void callApi()
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
                listSection.add(new Section("Suggest" , model.getData().getListSuggest(), new MovieAdapter(HomeActivity.this ,model.getData().getListSuggest())));
                listSection.add(new Section("Watch" , model.getData().getListWatch(), new MovieAdapter(HomeActivity.this ,model.getData().getListWatch())));
                listSection.add(new Section("Trending" , model.getData().getListTrending() , new MovieAdapter(HomeActivity.this ,model.getData().getListTrending())));
                listSection.add(new Section("Hot" , model.getData().getListHot() , new MovieAdapter(HomeActivity.this ,model.getData().getListHot())));
                sectionAdapter.reloadData(listSection);
            }
            @Override
            public void onFailure(Call<BaseResponseDto<HomeContentDto>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MovieAdapter.MovieEvent event) {
        Toast.makeText(this , event.movie.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , DetailActivity.class);
        intent.putExtra("movie", event.movie);
        startActivity(intent);
    }
}