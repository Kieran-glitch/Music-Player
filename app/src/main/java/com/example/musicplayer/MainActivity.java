package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView noSong;
    ArrayList<AudioModel> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv);
        noSong=findViewById(R.id.Nosong);
        if(!checkPermission()){
            requestPermissions();
            return;
        }
        String[] projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        String Selection=MediaStore.Audio.Media.IS_MUSIC;
        Cursor cursor=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,Selection,null,null);
        while(cursor.moveToNext()){
            AudioModel songsdata=new AudioModel(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            if(new File(songsdata.getPath()).exists()){
                songList.add(songsdata); //adding songsdata to the list
            }
        }
        if(songList.size()==0) { //if there is no song then show no songs
            noSong.setVisibility(View.VISIBLE);
        }
        else { //recycler-view new songs
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songList,getApplicationContext()));
        }}



    private void requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Permission required. Please enable in settings",Toast.LENGTH_LONG).show();
        } else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }

    }

    private boolean checkPermission() {
        int result= ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(PackageManager.PERMISSION_GRANTED==result){
            return true;
        }else{
            return false;
        }
    }
}