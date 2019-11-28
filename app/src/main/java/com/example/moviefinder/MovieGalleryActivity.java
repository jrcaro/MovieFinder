package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.moviefinder.APIService.APIClient;
import com.example.moviefinder.APIService.MovieService;
import com.example.moviefinder.JSONToJava.MoviePage;
import com.example.moviefinder.JSONToJava.Search;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static java.lang.Math.round;

public class MovieGalleryActivity extends AppCompatActivity {
    private final static String API_KEY = "b55fd2c4";
    private final static int itemByPage = 10;
    private static int totalPages;
    private List<Search> moviesList;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); //recibe el intent de la main activity
        String txtSearch = intent.getStringExtra(intent.EXTRA_TEXT);
        //txtSearch = txtSearch.substring(0,1).toUpperCase() + txtSearch.substring(1); //pone en mayuscula la primera letra

        //crea el servicio
        MovieService movieService = APIClient.getClient().create(MovieService.class);
        //llama a la implementacion
        loadPageMovie(movieService, txtSearch, 1);
        //Boton siguiente pagina

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private void loadPageMovie(MovieService service, String search, final int page){
        Call<MoviePage> call = service.loadPageMovie(API_KEY, search, page);

        call.enqueue(new Callback<MoviePage>() {
            @Override
            public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                if(page == 1){
                    int tempP = round(Integer.parseInt(response.body().getTotalResults())/itemByPage);
                    int resto = Integer.parseInt(response.body().getTotalResults())%itemByPage;
                    if(resto < 5) tempP++;
                    totalPages = tempP;

                    moviesList = response.body().getSearch();
                    CustomList listAdapter = new CustomList(MovieGalleryActivity.this, moviesList);
                    list = (ListView)findViewById(R.id.list);
                    list.setAdapter(listAdapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(MovieGallery.this, "You Clicked", Toast.LENGTH_SHORT).show();
                            String title = moviesList.get(position).getTitle();
                            Intent intent = new Intent(MovieGalleryActivity.this, OneMovieActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT, title);
                            startActivity(intent);
                        }
                    });
                } else {

                }
            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
