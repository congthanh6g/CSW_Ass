package com.example.filmapi.Model;

import com.example.filmapi.Adapter.MovieAdapter;
import com.example.filmapi.Response.MovieDto;

import java.util.List;

public class Section {
    private String title;
    private List<MovieDto> listMovies;
    private MovieAdapter adapter;


    public Section(String title, List<MovieDto> listMovies, MovieAdapter adapter) {
        this.title = title;
        this.listMovies = listMovies;
        this.adapter = adapter;
    }

    public MovieAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MovieAdapter adapter) {
        this.adapter = adapter;
    }

    public Section(String title, List<MovieDto> listMovies) {
        this.title = title;
        this.listMovies = listMovies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieDto> getListMovies() {
        return listMovies;
    }

    public void setListMovies(List<MovieDto> listMovies) {
        this.listMovies = listMovies;
    }
}
