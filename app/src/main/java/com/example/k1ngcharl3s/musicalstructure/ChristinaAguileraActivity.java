package com.example.k1ngcharl3s.musicalstructure;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChristinaAguileraActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(android.media.MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song(" Christina Aguilera", "\n Understand", R.drawable.christinaaguilera, R.raw.understand));
        songs.add(new Song(" Christina Aguilera", "\n Loving Me For Me", R.drawable.christinaaguilera, R.raw.loving_me_for_me));
        songs.add(new Song(" Christina Aguilera", "\n Impossible", R.drawable.christinaaguilera, R.raw.impossible));
        songs.add(new Song(" Christina Aguilera", "\n Slow Down Baby", R.drawable.christinaaguilera, R.raw.slow_down_baby));
        songs.add(new Song(" Christina Aguilera", "\n Makes Me Wanna Pray", R.drawable.christinaaguilera, R.raw.makes_me_wanna_pray));
        songs.add(new Song(" Christina Aguilera", "\n UnderAppreciated", R.drawable.christinaaguilera, R.raw.underappreciated));
        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songsListView = findViewById(R.id.list);
        songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(ChristinaAguileraActivity.this, song.getAudioResourceId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                mMediaPlayer.start();
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}

