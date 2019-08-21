package com.example.moviecatalogue;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.viewmodels.FavouriteViewModel;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA ="extra_data";
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    ImageView ivPhoto;
    TextView tvName,tvRating,tvDescription;
    ProgressBar progressBar;
    ImageButton ibLove,ibDontLove;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);
        ivPhoto = findViewById(R.id.iv_photo);
        tvName = findViewById(R.id.tv_name);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_overview);
        progressBar.setVisibility(View.VISIBLE);
        ibLove = findViewById(R.id.btnLove);
        ibDontLove = findViewById(R.id.btnDontLove);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MoviesTv mtv = getIntent().getParcelableExtra(EXTRA_DATA);

        final FavouriteViewModel fViewModel = new FavouriteViewModel(this);

        if(fViewModel.getCatalogueLoveOrNot(mtv.getName()) || fViewModel.getCatalogueLoveOrNot(mtv.getTvName())){
            ibLove.setVisibility(View.VISIBLE);
            ibDontLove.setVisibility(View.GONE);
        }
        else{
            ibLove.setVisibility(View.GONE);
            ibDontLove.setVisibility(View.VISIBLE);
        }

        ibLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                if(mtv.getChooseTarget() == 1){
                    name = mtv.getName();
                }
                else{
                    name = mtv.getTvName();
                }
                fViewModel.deleteLove(name);
                ibLove.setVisibility(View.GONE);
                ibDontLove.setVisibility(View.VISIBLE);
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.successDelete), Toast.LENGTH_SHORT).show();


            }
        });

        ibDontLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                if(mtv.getChooseTarget() == 1){
                    name = mtv.getName();
                }
                else{
                    name = mtv.getTvName();
                }
                boolean check = fViewModel.insertLove(name,mtv.getRating(),mtv.getOverview(),mtv.getPoster(),mtv.getChooseTarget());
                if(check){
                    ibLove.setVisibility(View.VISIBLE);
                    ibDontLove.setVisibility(View.GONE);
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.successAdd), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(mtv.getPoster() != null || mtv.getOverview() != null ) {
            progressBar.setVisibility(View.GONE);
            if (mtv.getChooseTarget() == 1) {
                tvName.setText(mtv.getName());
            } else {
                tvName.setText(mtv.getTvName());
            }
            tvRating.setText(String.valueOf(mtv.getRating()));
            tvDescription.setText(mtv.getOverview());
            Glide.with(this)
                    .load(BASE_URL + mtv.getPoster()).apply(new RequestOptions().override(150, 230))
                    .into(ivPhoto);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
