package com.akameko.testforupstarts;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.akameko.testforupstarts.repository.pojos.Jeans;
import com.akameko.testforupstarts.repository.retrofit.Repository;
import com.akameko.testforupstarts.repository.room.JeansDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    Repository repository  = new Repository();
    JeansDatabase jeansDatabase ;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        jeansDatabase = Room.databaseBuilder(getContext(), JeansDatabase.class, "database").allowMainThreadQueries().build();

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

        RecyclerView recyclerView = getActivity().findViewById(R.id.main_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        List<Jeans> liked = jeansDatabase.getJeansDao().getAllItems();
        MainAdapter mainAdapter = new MainAdapter(jeansList, liked);
//        Log.d("123", "here1");
//        for (Jeans j: liked             ) {
//            Log.d("123", j.toString());
//        }
        mainAdapter.setOnLikeClickListener((likedJeans, position, addToFavourite) -> {
            if (addToFavourite){
                jeansDatabase.getJeansDao().insert(likedJeans);

                //mainAdapter.notifyItemChanged(position);
                //mainAdapter.notifyDataSetChanged();
                Log.d("123", "Добавлено в избранное");
                Log.d("123", jeansDatabase.getJeansDao().getItemById(likedJeans.getId()).toString());
            } else {
                jeansDatabase.getJeansDao().delete(likedJeans);

                //mainAdapter.notifyItemChanged(position);
                // mainAdapter.notifyDataSetChanged();

                Log.d("123", "Убрано из избранного");
            }


        });

        mainAdapter.setOnItemClickListener((jeansToShow, position) -> {
            sharedViewModel.setActiveJeans(jeansToShow);
            ((MainActivity)getActivity()).showDetails();
        });

//        Log.d("123", "here2");
        recyclerView.setAdapter(mainAdapter);



    }

}
