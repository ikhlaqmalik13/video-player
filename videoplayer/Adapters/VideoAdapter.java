package com.kashkart.videoplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kashkart.videoplayer.MainActivity;
import com.kashkart.videoplayer.R;
import com.kashkart.videoplayer.VideoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;
import java.util.zip.Inflater;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoItemViewHolder> {

    Context context;
    ArrayList<File> videoList;

    public VideoAdapter(Context context, ArrayList<File> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_video_layout, parent, false);
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoItemViewHolder holder, int position) {
        holder.videoName.setText(MainActivity.videos.get(position).getName());
        Bitmap bitmapThumbnail = ThumbnailUtils.createVideoThumbnail(videoList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.videoThumbnailImage.setImageBitmap(bitmapThumbnail);

        holder.videoThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoItemViewHolder extends RecyclerView.ViewHolder{
        ImageView videoThumbnailImage;
        TextView videoName;
        public VideoItemViewHolder(View itemView) {
            super(itemView);
            videoThumbnailImage = itemView.findViewById(R.id.videoThumbnailImage);
            videoName = itemView.findViewById(R.id.videoName);
        }
    }
}
