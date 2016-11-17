package com.movieapp.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.modle.R;
import com.movieapp.modle.Result;
import com.movieapp.presenter.listener.IItemClickListener;
import com.movieapp.utility.PaletteTransformation;
import com.movieapp.utility.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 11/17/2016.
 */
public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Result> mDatas;
    private int mScreenWidth;

    private int mDefaultTextColor;
    private int mDefaultBackgroundColor;

    private static String posterBaseURL = "http://image.tmdb.org/t/p/w500/";


    public MovieViewAdapter(List<Result> datas){
        mDatas = datas;
    }

    private IItemClickListener iItemClickListener;

    public void setOnItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_view_item, parent, false);
        mContext = parent.getContext();

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(rowView, iItemClickListener);

        //get the colors
        mDefaultTextColor = ContextCompat.getColor(mContext, R.color.text_without_palette);
        mDefaultBackgroundColor = ContextCompat.getColor(mContext, R.color.text_without_palette);

        //get the screenWidth :D optimize everything :D
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder viewHolder, final int position) {
        Result result = mDatas.get(position);

        viewHolder.movieTitle.setText(result.getTitle());
        viewHolder.movieOverview.setText(result.getOverview());
        //imagesViewHolder.imageView.setDrawingCacheEnabled(true);
        viewHolder.movieThumb.setImageBitmap(null);

        //reset colors so we prevent crazy flashes :D
        viewHolder.movieTitle.setTextColor(mDefaultTextColor);
        viewHolder.movieOverview.setTextColor(mDefaultTextColor);
        viewHolder.imageTextContainer.setBackgroundColor(mDefaultBackgroundColor);


        //cancel any loading images on this view
        Picasso.with(mContext).cancelRequest(viewHolder.movieThumb);
        //load the image
        String path = posterBaseURL+result.getPosterPath();
        Picasso.with(mContext).load(path)
                .transform(PaletteTransformation.instance()).into(viewHolder.movieThumb, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) viewHolder.movieThumb.getDrawable()).getBitmap(); // Ew!

                if (bitmap != null && !bitmap.isRecycled()) {

                    Palette palette = PaletteTransformation.getPalette(bitmap);

                    if (palette != null) {
                        Palette.Swatch s = palette.getVibrantSwatch();
                        if (s == null) {
                            s = palette.getDarkVibrantSwatch();
                        }
                        if (s == null) {
                            s = palette.getLightVibrantSwatch();
                        }
                        if (s == null) {
                            s = palette.getMutedSwatch();
                        }

                        if (s != null && position >= 0 && position < mDatas.size()) {
                            viewHolder.movieTitle.setTextColor(s.getTitleTextColor());
                            viewHolder.movieOverview.setTextColor(s.getTitleTextColor());
                            Util.animateViewColor(viewHolder.imageTextContainer, mDefaultBackgroundColor, s.getRgb());
                        }
                    }
                }

                // just delete the reference again.
                bitmap = null;

                if (Build.VERSION.SDK_INT >= 21) {
                    viewHolder.movieThumb.setTransitionName("cover" + position);
                }
                viewHolder.imageTextContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iItemClickListener.onClick(v, position);
                    }
                });
            }
        });

        viewHolder.movieThumb.setMinimumHeight(150);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    // Used to cache the views within the item layout for fast access
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private final FrameLayout imageTextContainer;
        private final ImageView movieThumb;
        private final TextView movieTitle;
        private final TextView movieOverview;
        IItemClickListener iItemClickListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MovieViewHolder(View itemView, final IItemClickListener iItemClickListener){
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.iItemClickListener = iItemClickListener;

            imageTextContainer = (FrameLayout) itemView.findViewById(R.id.item_image_text_container);
            movieThumb = (ImageView) itemView.findViewById(R.id.item_image_img);
            movieTitle = (TextView) itemView.findViewById(R.id.item_title);
            movieOverview = (TextView) itemView.findViewById(R.id.item_overview);



            movieThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iItemClickListener.onClick(v, getAdapterPosition());
                }
            });

        }

    }
}
