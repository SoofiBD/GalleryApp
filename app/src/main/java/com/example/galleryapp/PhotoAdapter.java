package com.example.galleryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context context;
    private ArrayList<Photo> photoList;
    private OnPhotoClickListener listener;

    public void filterList(List<Photo> filteredPhotos) {
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }

    public void setOnPhotoClickListener(OnPhotoClickListener listener) {
        this.listener = listener;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView; // image_view öğesine karşılık gelen değişken
        public TextView titleTextView;
        public TextView categoryTextView; // category_text_view öğesine karşılık gelen değişken

        public PhotoViewHolder(View itemView, OnPhotoClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_image_view); // image_view öğesine karşılık gelen ImageView öğesi
            titleTextView = itemView.findViewById(R.id.title_text_view);
            categoryTextView = itemView.findViewById(R.id.category_text_view); // category_text_view öğesine karşılık gelen TextView öğesi

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onPhotoClick(position);
                    }
                }
            });
        }
    }

    public PhotoAdapter(Context context, ArrayList<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentItem = photoList.get(position);

        Glide.with(context)
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView); // image_view öğesine karşılık gelen ImageView öğesine resim yükleniyor

        holder.titleTextView.setText(currentItem.getTitle());
        holder.categoryTextView.setText(currentItem.getCategory()); // category_text_view öğesine karşılık gelen TextView öğesine kategori atanıyor
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
