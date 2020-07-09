package com.akameko.testforupstarts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akameko.testforupstarts.dagger.App
import com.akameko.testforupstarts.repository.pojos.Jeans
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SharedViewModel : ViewModel() {
    var repository = App.getComponent().repository
    var jeansDatabase = App.getComponent().jeansDatabase

    var jeansList = MutableLiveData<List<Jeans>>()

    var activeJeans: Jeans? = null
    //    public void setPositionToUpdate(Integer position){
//        positionToUpdate.setValue(position);
//    }
    var positionToShow: Int? = null

    //private MutableLiveData<Integer> positionToUpdate = new MutableLiveData<>();
    fun loadJeans() {
        val disposable = repository.jeans
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: List<Jeans> ->
                    jeansList.value = result
                    Log.d("123", "Items loaded!!")
                }) { throwable: Throwable? -> Log.d("123", "Items loading failed", throwable) }
    }

    fun addToFavourite(likedJeans: Jeans?) {
        jeansDatabase.jeansDao.insert(likedJeans)
    }

    fun removeFromFavourite(dislikedJeans: Jeans?) {
        jeansDatabase.jeansDao.delete(dislikedJeans)
    }

    override fun onCleared() {
        super.onCleared()
    }
}