package com.example.filmapi.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmapi.Model.Movie;
import com.example.filmapi.R;
import com.example.filmapi.Response.MovieDto;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter{

    private Activity activity;
    // nhan vao listmovie
    private List<MovieDto> listMovie;
    private String sectionTitle;

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public MovieAdapter(Activity activity, List<MovieDto> listMovie) {
        this.activity = activity;
        this.listMovie = listMovie;
    }

    public MovieAdapter(List<MovieDto> list) {
        this.listMovie = list;
    }

    public void reloadData(List<MovieDto> list)
    {
        this.listMovie = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(sectionTitle.equals("Hot") || sectionTitle.equals("Watch")) {
            itemView = ((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_movie_2, parent, false);
        }
        else
        {
            itemView = ((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_movie, parent, false);
        }
        MovieHolder movieHolder = new MovieHolder(itemView);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieDto model = listMovie.get(position);
        MovieHolder vh = (MovieHolder) holder;
        vh.tvTitle.setText(model.getName());
        Glide.with(activity).load(model.getThumbnail()).into(vh.ivCover);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    // b1 : tao holder
    public class MovieHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivCover = itemView.findViewById(R.id.ivCover);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieDto movie = listMovie.get(getAdapterPosition());
                    EventBus.getDefault().post(new MovieEvent(movie.getName() , getAdapterPosition() , movie));
                }
            });
        }
    }

    public static class MovieEvent {
        public String message;
        public int position;
        public MovieDto movie;
        public MovieEvent() {
        }

        public MovieEvent(String message, int position, MovieDto movie) {
            this.message = message;
            this.position = position;
            this.movie = movie;
        }
    }
}
