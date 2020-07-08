package com.akameko.testforupstarts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

    public DetailFragment() {
        // Required empty public constructor
    }


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

    private void init() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        jeansToShow = sharedViewModel.getActiveJeans();

        textViewTitle.setText(jeansToShow.getTitle());
        textViewPrice.setText(jeansToShow.getPrice().toString() + " Р");
        Picasso.get().load(jeansToShow.getImage()).into(imageView);

        buttonBack.setOnClickListener(v -> {
            //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().onBackPressed();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onStop() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onStop();
    }
}
