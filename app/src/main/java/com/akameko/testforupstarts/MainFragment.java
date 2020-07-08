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

    public static MainFragment newInstance() {
        return new MainFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.main_fragment, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initFragment();
        return root;
    }

    private  void initFragment(){
        sharedViewModel.loadJeans();
        sharedViewModel.jeansList.observe(getViewLifecycleOwner(), jeansList -> {
                initRecycler(jeansList);
        });
    }



    public void initRecycler(List<Jeans> jeansList) {

        RecyclerView recyclerView = getActivity().findViewById(R.id.main_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        List<Jeans> liked = sharedViewModel.jeansDatabase.getJeansDao().getAllItems();
        MainAdapter mainAdapter = new MainAdapter(jeansList, liked);

        mainAdapter.setOnLikeClickListener((likedJeans, position, addToFavourite) -> {
            if (addToFavourite){
                sharedViewModel.jeansDatabase.getJeansDao().insert(likedJeans);
                Log.d("123", "Добавлено в избранное");
                //Log.d("123", jeansDatabase.getJeansDao().getItemById(likedJeans.getId()).toString());
            } else {
                sharedViewModel.jeansDatabase.getJeansDao().delete(likedJeans);
                Log.d("123", "Убрано из избранного");
            }


        });

        mainAdapter.setOnItemClickListener((jeansToShow, position) -> {
            sharedViewModel.setActiveJeans(jeansToShow);
            sharedViewModel.setPositionToShow(position);
            ((MainActivity)getActivity()).showDetails();
        });

        recyclerView.setAdapter(mainAdapter);
    }

}
