package com.example.moviecatalogue;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.example.moviecatalogue.fragment.FavouriteFragment;
import com.example.moviecatalogue.fragment.MovieFragment;
import com.example.moviecatalogue.fragment.TvFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tv_show:
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorit :
                    fragment = new FavouriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState == null){
            navView.setSelectedItemId(R.id.navigation_movie);
        }
    }
}
