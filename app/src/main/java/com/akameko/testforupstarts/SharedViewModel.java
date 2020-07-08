package com.akameko.testforupstarts;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akameko.testforupstarts.dagger.App;
import com.akameko.testforupstarts.repository.pojos.Jeans;
import com.akameko.testforupstarts.repository.retrofit.Repository;
import com.akameko.testforupstarts.repository.room.JeansDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SharedViewModel extends ViewModel {

    public Repository repository  = App.getComponent().getRepository();
    public JeansDatabase jeansDatabase = App.getComponent().getJeansDatabase();
    public MutableLiveData<List<Jeans>> jeansList = new MutableLiveData<>();

    private Jeans activeJeans;
    private Integer positionToShow;

    //private MutableLiveData<Integer> positionToUpdate = new MutableLiveData<>();

    public void loadJeans(){
        Disposable disposable = repository.getJeans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    jeansList.setValue(result);


                    Log.d("123", "Items loaded!!");


                }, throwable -> {
                    Log.d("123", "Items loading failed", throwable);
                    //Toast.makeText(this,"load error", Toast.LENGTH_SHORT).show();
                });
    }

    public void addToFavourite(Jeans likedJeans){
        jeansDatabase.getJeansDao().insert(likedJeans);
    }
    public void removeFromFavourite(Jeans dislikedJeans){
        jeansDatabase.getJeansDao().delete(dislikedJeans);
    }




    public void setActiveJeans(Jeans activeJeans) {
        this.activeJeans = activeJeans;
    }

    public Jeans getActiveJeans() {
        return activeJeans;
    }

//    public void setPositionToUpdate(Integer position){
//        positionToUpdate.setValue(position);
//    }

    public Integer getPositionToShow() {
        return positionToShow;
    }

    public void setPositionToShow(Integer positionToShow) {
        this.positionToShow = positionToShow;
    }

    @Override
    protected void onCleared() {

        super.onCleared();
    }
}