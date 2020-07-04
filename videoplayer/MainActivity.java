package com.kashkart.videoplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kashkart.videoplayer.Adapters.VideoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int REQUEST_PERMISSION = 1;
    RecyclerView videoRecyclerView;
    VideoAdapter videoAdapter;
    GridLayoutManager gridLayoutManager;
    File directory;
    public static ArrayList<File> videos;

    boolean boolean_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videos = new ArrayList<>();

        directory = new File("/mnt/");

        gridLayoutManager = new GridLayoutManager(this, 2);
        videoRecyclerView.setLayoutManager(gridLayoutManager);

        permissionForVideo();
    }

    private void permissionForVideo() {
        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            if((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
        }
        else
        {
            boolean_permission = true;
            getFile(directory);
            videoAdapter = new VideoAdapter(this, videos);
            videoRecyclerView.setAdapter(videoAdapter);
        }

    }

    public ArrayList<File>  getFile(File directory) {
        File listFile[] = directory.listFiles();
        if(listFile != null && listFile.length >0){
            for(int i=0; i<listFile.length; i++){

                if(listFile[i].isDirectory()){
                    getFile(listFile[i]);
                }
                else{
                    boolean_permission = false;
                    if(listFile[i].getName().endsWith(".mp4")){
                        for(int j=0; j<videos.size();j++){
                            if(videos.get(j).getName().equals(listFile[i].getName())){
                                boolean_permission = true;
                            }else{

                            }
                        }

                        if(boolean_permission){
                            boolean_permission = false;
                        }
                        else{
                            videos.add(listFile[i]);
                        }
                    }
                }
            }

        }

        return  videos;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION ){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                boolean_permission = true;
                getFile(directory);
                videoAdapter = new VideoAdapter(this, videos);
                videoRecyclerView.setAdapter(videoAdapter);
            }
            else{
                Toast.makeText(getApplicationContext(),"Please allow the permissions", Toast.LENGTH_LONG).show();
            }
        }
    }
}
