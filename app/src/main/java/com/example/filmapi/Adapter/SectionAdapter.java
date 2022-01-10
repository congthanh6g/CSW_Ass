package com.example.filmapi.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmapi.Model.Section;
import com.example.filmapi.R;
import com.example.filmapi.Response.MovieDto;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private List<Section> listSection;

    public SectionAdapter(Activity activity, List<Section> listSection) {
        this.activity = activity;
        this.listSection = listSection;
    }

    public void reloadData(List<Section> listSection) {
        this.listSection = listSection;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_section , parent , false);
        SectionHolder holder = new SectionHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         Section model = listSection.get(position);
         SectionHolder vh = (SectionHolder) holder;
         vh.tvSection.setText(model.getTitle());

         // b1 : datasource
         List<MovieDto> listMovies = model.getListMovies();
         // b2
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity , LinearLayoutManager.HORIZONTAL , false);
        // b3
        MovieAdapter adapter = model.getAdapter();
        adapter.setSectionTitle(model.getTitle());

        //b4:
        vh.rvMovie.setLayoutManager(layoutManager);
        vh.rvMovie.setHasFixedSize(true);
        vh.rvMovie.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return listSection.size();
    }

    public class SectionHolder extends RecyclerView.ViewHolder {

        TextView tvSection;
        RecyclerView rvMovie;

        public SectionHolder(@NonNull View itemView) {
            super(itemView);
            tvSection = itemView.findViewById(R.id.tvTitle);
            rvMovie = itemView.findViewById(R.id.rvMovie);
        }
    }
}
