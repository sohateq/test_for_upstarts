package com.akameko.testforupstarts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akameko.testforupstarts.dagger.App
import com.akameko.testforupstarts.dagger.DaggerAppComponent
import com.akameko.testforupstarts.repository.pojos.Jeans
import com.akameko.testforupstarts.repository.retrofit.Repository
import com.akameko.testforupstarts.repository.room.JeansDatabase
import com.akameko.testforupstarts.ui.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SharedViewModel : ViewModel() {

    companion object {
        const val TAG = "SharedViewModel"
    }

    init {
        App.component.injectSharedViewModel(this)
    }
    
    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var jeansDatabase: JeansDatabase

    var jeansList = MutableLiveData<List<Jeans>>()

    var activeJeans: Jeans? = null
    var positionToShow: Int? = null

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Loads [Jeans] List from server to [jeansList] asynchronously
     */
    fun loadJeans() {
        val disposable = repository.jeans
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: List<Jeans> ->
                    jeansList.value = result
                    Log.d(TAG, "Items loaded!!")
                }) { throwable: Throwable? -> Log.d(TAG, "Items loading failed", throwable) }
        compositeDisposable.add(disposable)
    }

    /**
     * Adds [Jeans] to app database
     *
     * @param likedJeans is [Jeans] to be added to favourite
     */
    fun addToFavourite(likedJeans: Jeans) {
        jeansDatabase.jeansDao.insert(likedJeans)
    }

    /**
     * Deletes [Jeans] from app database
     *
     * @param dislikedJeans is [Jeans] to be deleted
     */
    fun removeFromFavourite(dislikedJeans: Jeans) {
        jeansDatabase.jeansDao.delete(dislikedJeans)
    }

    /**
     * Provides all liked [Jeans] stored in app database
     */
    fun getAllFavouritesJeansList(): List<Jeans> {
        return jeansDatabase.jeansDao.allItems
    }

    /**
     * Prepares this [SharedViewModel] for [DetailFragment]. Should be used before navigate to [DetailFragment]
     *
     * @param jeansToShow is [Jeans] object to be shown
     * @param positionToShow is position of chosen is main RecyclerView [jeansToShow]
     */
    fun setDataForDetailFragment(jeansToShow: Jeans, position: Int) {
        activeJeans = jeansToShow
        positionToShow = position
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}