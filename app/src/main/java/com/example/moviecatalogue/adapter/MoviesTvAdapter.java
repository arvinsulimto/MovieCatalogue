package com.example.moviecatalogue.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.DetailActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.MoviesTv;

import java.util.List;

public class MoviesTvAdapter extends RecyclerView.Adapter<MoviesTvAdapter.ViewHolder> {

    List<MoviesTv> listData;
    int choose;
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    public MoviesTvAdapter(List<MoviesTv> listData,int choose){
        this.choose = choose;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MoviesTv mtv = listData.get(i);
        if(choose == 1) {
            viewHolder.tvName.setText(mtv.getName());
        }
        else{
            viewHolder.tvName.setText(mtv.getTvName());
        }
        viewHolder.tvRating.setText(String.valueOf(mtv.getRating()));
        Glide.with(viewHolder.itemView.getContext())
                .load(BASE_URL+mtv.getPoster()).apply(new RequestOptions().override(150,230))
                .into(viewHolder.ivPhoto);

        viewHolder.cvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoviesTv movtv = new MoviesTv();

                if(choose == 1) {
                    movtv.setName(mtv.getName());
                }
                else{
                    movtv.setTvName(mtv.getTvName());
                }
                movtv.setRating(mtv.getRating());
                movtv.setPoster(mtv.getPoster());
                movtv.setOverview(mtv.getOverview());
                movtv.setChooseTarget(choose);
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DATA,movtv);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvRating,tvName;
        CardView cvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_poster);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvName = itemView.findViewById(R.id.tv_name);
            cvContent = itemView.findViewById(R.id.cvContent);
        }

    }
}
