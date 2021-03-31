package com.jayyaj.hikebuddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.jayyaj.hikebuddy.R;
import com.jayyaj.hikebuddy.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider>{
    private List<Images> imageList;

    public ViewPagerAdapter(List<Images> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pager_row, parent, false);
        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ImageSlider holder, int position) {
        Picasso.get().load(imageList.get(position).getUrl())
                .fit()
                .placeholder(android.R.drawable.stat_sys_download)
                .into(holder.parkImage);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder {
        public ImageView parkImage;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            parkImage = itemView.findViewById(R.id.viewPagerImageView);
        }
    }
}
