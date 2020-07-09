package com.akameko.testforupstarts

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akameko.testforupstarts.repository.pojos.Jeans
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    private var sharedViewModel: SharedViewModel? = null

    private var imageView: ImageView? = null

    private var textViewTitle: TextView? = null
    private var textViewPrice: TextView? = null

    private var buttonLike: Button? = null
    private var buttonBack: Button? = null

//    private var jeansToShow: Jeans? = null
//    private var liked: Boolean? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        imageView = root.findViewById(R.id.image_view_detail)
        textViewTitle = root.findViewById(R.id.text_view_detail_title)
        textViewPrice = root.findViewById(R.id.text_detail_price)
        buttonLike = root.findViewById(R.id.button_detail_like)
        buttonBack = root.findViewById(R.id.button_detail_back)

        init()

        return root
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun init() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        var jeansToShow = sharedViewModel!!.activeJeans

        var liked = sharedViewModel!!.jeansDatabase.jeansDao.allItems.contains(jeansToShow)
        if (liked) {
            buttonLike!!.foreground = resources.getDrawable(R.drawable.like_true)
        } else {
            buttonLike!!.foreground = resources.getDrawable(R.drawable.like_false)
        }

        textViewTitle!!.text = jeansToShow?.getTitle()
        textViewPrice!!.text = jeansToShow?.getPrice().toString() + " Р"

        Picasso.get().load(jeansToShow?.getImage()).into(imageView)

        buttonBack!!.setOnClickListener { v: View? -> activity!!.onBackPressed() }
        buttonLike!!.setOnClickListener { v: View? ->
            if (liked) {
                liked = false
                buttonLike!!.foreground = resources.getDrawable(R.drawable.like_false)
                sharedViewModel!!.removeFromFavourite(jeansToShow)
            } else {
                liked = true
                buttonLike!!.foreground = resources.getDrawable(R.drawable.like_true)
                sharedViewModel!!.addToFavourite(jeansToShow)
                showLikeNotification()
            }
        }
    }

    private fun showLikeNotification() {
        val cardViewNotification: CardView = activity!!.findViewById(R.id.card_view_notification_detail)
        cardViewNotification.visibility = View.VISIBLE

        val h = Handler()
        h.postDelayed({ cardViewNotification.visibility = View.GONE }, 3000)
    }

    override fun onStart() {
        super.onStart()
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onStop() {
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onStop()
    }
}