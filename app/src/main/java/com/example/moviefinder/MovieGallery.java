package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.round;

public class MovieGallery extends AppCompatActivity {
    private final static String API_KEY = "b55fd2c4";
    private final static int itemByPage = 10;
    private static int totalPages;
    private List<Search> moviesList;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);
        Intent intent = getIntent(); //recibe el intent de la main activity
        String txtSearch = intent.getStringExtra(intent.EXTRA_TEXT);
        //txtSearch = txtSearch.substring(0,1).toUpperCase() + txtSearch.substring(1); //pone en mayuscula la primera letra

        //crea el servicio
        MovieService movieService = APIClient.getClient().create(MovieService.class);
        //llama a la implementacion
        loadPageMovie(movieService, txtSearch, 1);
        //Boton siguiente pagina

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
                    CustomList listAdapter = new CustomList(MovieGallery.this, moviesList);
                    list = (ListView)findViewById(R.id.list);
                    list.setAdapter(listAdapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(MovieGallery.this, "You Clicked", Toast.LENGTH_SHORT).show();
                            //crea el servicio
                            MovieService movieService = APIClient.getClient().create(MovieService.class);
                            getMovie(movieService, moviesList.get(position).getTitle());
                        }
                    });
                } else {

                }
                //CustomList listAdapter = new CustomList(MovieGallery.this, )

            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getMovie(MovieService service, String search){
        Call<MovieComplete> call = service.getMovie(API_KEY, search, "full");
        call.enqueue(new Callback<MovieComplete>() {
            @Override
            public void onResponse(Call<MovieComplete> call, Response<MovieComplete> response) {
            }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
