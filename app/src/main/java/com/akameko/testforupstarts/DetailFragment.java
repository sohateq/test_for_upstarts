package com.akameko.testforupstarts;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akameko.testforupstarts.repository.pojos.Jeans;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private Jeans jeansToShow;

    private ImageView imageView;

    private TextView textViewTitle;
    private TextView textViewPrice;

    private Boolean liked;
    private Button buttonLike;
    private Button buttonBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = root.findViewById(R.id.image_view_detail);
        textViewTitle = root.findViewById(R.id.text_view_detail_title);
        textViewPrice = root.findViewById(R.id.text_detail_price);
        buttonLike = root.findViewById(R.id.button_detail_like);
        buttonBack = root.findViewById(R.id.button_detail_back);

        init();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        jeansToShow = sharedViewModel.getActiveJeans();

        liked = sharedViewModel.jeansDatabase.getJeansDao().getAllItems().contains(jeansToShow);
        if (liked){
           buttonLike.setForeground(getResources().getDrawable(R.drawable.like_true));
        } else {
            buttonLike.setForeground(getResources().getDrawable(R.drawable.like_false));
        }

        textViewTitle.setText(jeansToShow.getTitle());
        textViewPrice.setText(jeansToShow.getPrice().toString() + " Р");
        Picasso.get().load(jeansToShow.getImage()).into(imageView);

        buttonBack.setOnClickListener(v -> {

            getActivity().onBackPressed();
        });

        buttonLike.setOnClickListener(v -> {
            if (liked){
                liked = false;
                buttonLike.setForeground(getResources().getDrawable(R.drawable.like_false));
                sharedViewModel.removeFromFavourite(jeansToShow);
            } else {
                liked = true;
                buttonLike.setForeground(getResources().getDrawable(R.drawable.like_true));
                sharedViewModel.addToFavourite(jeansToShow);
                showLikeNotification();
            }





        });
    }

    private void showLikeNotification(){
        CardView cardViewNotification = getActivity().findViewById(R.id.card_view_notification_detail);
        cardViewNotification.setVisibility(View.VISIBLE);

        Handler h = new Handler();
        h.postDelayed(() -> cardViewNotification.setVisibility(View.GONE), 3000);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onStop() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onStop();
    }
}
