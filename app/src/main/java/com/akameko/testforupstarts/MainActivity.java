package com.akameko.testforupstarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.akameko.testforupstarts.repository.pojos.Jeans;
import com.akameko.testforupstarts.repository.retrofit.Repository;
import com.akameko.testforupstarts.repository.room.JeansDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Repository repository  = new Repository();
    JeansDatabase jeansDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jeansDatabase = Room.databaseBuilder(this, JeansDatabase.class, "database").allowMainThreadQueries().build();

        loadJeans();
    }

    private void loadJeans(){
        Disposable disposable = repository.getJeans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    initRecycler(result);
                    //updateDatabase(result.getItems());
                    //getViewState().updateRecycler(result.getItems());
                    //Log.d("123", result.getItems().get(0).toString());
                    Log.d("123", "Items loaded!!");


                }, throwable -> {
                    Log.d("123", "Items loading failed", throwable);
                    //Toast.makeText(this,"load error", Toast.LENGTH_SHORT).show();
                });
    }

    public void initRecycler(List<Jeans> jeansList) {

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        List<Jeans> liked = jeansDatabase.getJeansDao().getAllItems();
        MainAdapter mainAdapter = new MainAdapter(jeansList, liked);
        Log.d("123", "here1");
        for (Jeans j: liked             ) {
            Log.d("123", j.toString());
        }
        mainAdapter.setOnLikeClickListener(likedJeans -> {
            jeansDatabase.getJeansDao().insert(likedJeans);
            mainAdapter.notifyDataSetChanged();
            Log.d("123", jeansDatabase.getJeansDao().getItemById(likedJeans.getId()).toString());
        });
        Log.d("123", "here2");
        recyclerView.setAdapter(mainAdapter);



    }
}
