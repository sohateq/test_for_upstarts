package com.akameko.testforupstarts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akameko.testforupstarts.R
import com.akameko.testforupstarts.repository.pojos.Jeans
import com.akameko.testforupstarts.utils.Notificator
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass, shows details of chosen [Jeans] on [MainFragment]
 */
class DetailFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var imageView: ImageView
    private lateinit var imageViewWrapper: ConstraintLayout // image view layout-wrapper used for notification

    private lateinit var textViewTitle: TextView
    private lateinit var textViewPrice: TextView

    private lateinit var buttonLike: Button
    private lateinit var buttonBack: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        imageView = root.findViewById(R.id.image_view_detail)
        imageViewWrapper = root.findViewById(R.id.image_view_wrapper)
        textViewTitle = root.findViewById(R.id.text_view_detail_title)
        textViewPrice = root.findViewById(R.id.text_detail_price)
        buttonLike = root.findViewById(R.id.button_detail_like)
        buttonBack = root.findViewById(R.id.button_detail_back)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        init()

        return root
    }

    private fun init() {
        val jeansToShow = sharedViewModel.activeJeans ?: return

        var liked = sharedViewModel.getAllFavouritesJeansList().contains(jeansToShow)

        resolveLikeButton(buttonLike, liked)

        textViewTitle.text = jeansToShow.title
        textViewPrice.text = String.format(getString(R.string.text_view_price), jeansToShow.price)

        Picasso.get().load(jeansToShow.image).into(imageView)

        buttonBack.setOnClickListener { activity?.onBackPressed() }

        buttonLike.setOnClickListener {
            if (liked) {
                liked = false
                sharedViewModel.removeFromFavourite(jeansToShow)
                Notificator.showNotification(imageViewWrapper, getString(R.string.removed_from_favourite))
            } else {
                liked = true
                sharedViewModel.addToFavourite(jeansToShow)
                Notificator.showNotification(imageViewWrapper, getString(R.string.added_to_favourite))
            }
            resolveLikeButton(buttonLike, liked)
        }
    }

    private fun resolveLikeButton(button: Button, liked: Boolean) {
        if (liked) {
            button.foreground = resources.getDrawable(R.drawable.like_true, null)
        } else {
            button.foreground = resources.getDrawable(R.drawable.like_false, null)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onStop() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onStop()
    }
}