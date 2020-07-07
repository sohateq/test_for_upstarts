package com.akameko.testforupstarts;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akameko.testforupstarts.repository.pojos.Jeans;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Jeans> jeansList;
    private List<Jeans> likedJeansList;
    private ViewGroup parent; //для предоставления локальных ресурсов в onBingViewHolder

    private OnLikeClickListener likeClickListener;

    public interface OnLikeClickListener {
        void onLikeClick(Jeans likedJeans);

        // public void onLongItemClick(View view, int position);
    }

    public void setOnLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewMain;
        public ImageView imageViewLike;

        public TextView textViewNew;
        public TextView textViewTitle;
        public TextView textViewPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNew = itemView.findViewById(R.id.text_view_new);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewPrice = itemView.findViewById(R.id.text_view_price);

            imageViewMain = itemView.findViewById(R.id.image_view_main);
            imageViewLike = itemView.findViewById(R.id.image_view_like);


        }
    }

    public MainAdapter(List<Jeans> jeansList, List<Jeans> likedJeansList) {
        this.jeansList = jeansList;
        this.likedJeansList = likedJeansList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewTitle.setText(jeansList.get(position).getTitle());
        holder.textViewPrice.setText(jeansList.get(position).getPrice().toString() + " Р");

        if (jeansList.get(position).getNew()) {
            holder.textViewNew.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(jeansList.get(position).getImage()).into(holder.imageViewMain);

        holder.imageViewLike.setOnClickListener(v -> {
            if (likeClickListener != null ) {
                likeClickListener.onLikeClick(jeansList.get(position));
            }
        });

        if (likedJeansList.contains(jeansList.get(position))){
            holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_true));
        } else {
            holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_false));
        }


    }

    @Override
    public int getItemCount() {
        return jeansList.size();
    }
}