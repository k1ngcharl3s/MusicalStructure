package com.example.k1ngcharl3s.musicalstructure;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TupacActivity {
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
        songs.add(new Song(" Tupac", "\n Picture Me Rollin", R.drawable.tupac1, R.raw.picture_me_rollin));
        songs.add(new Song(" Tupac", "\n Holla At Me", R.drawable.tupac1, R.raw.holla_at_me));
        songs.add(new Song(" Tupac", "\n White Man'z World", R.drawable.tupac1, R.raw.white_manz_world));
        songs.add(new Song(" Tupac" ,"\n Skandalouz", R.drawable.tupac1, R.raw.skandalouz));
        songs.add(new Song(" Tupac", "\n No More Pain", R.drawable.tupac1, R.raw.no_more_pain));
        songs.add(new Song(" Tupac", "\n Heartz of Men?", R.drawable.tupac1, R.raw.heartz_of_men));
        songs.add(new Song(" Tupac", "\n Only God Can Judge Me", R.drawable.tupac1, R.raw.only_god_can_judge_me));
        songs.add(new Song(" Tupac", "\n Tradin' War Stories?", R.drawable.tupac1, R.raw.tradin_war_stories));
        songs.add(new Song(" Tupac", "\n Life's SO Hard", R.drawable.tupac1, R.raw.lifes_so_hard));
        songs.add(new Song(" Tupac", "\n Staring Through My Rearview", R.drawable.tupac1, R.raw.staring_through_my_rearview));
        songs.add(new Song(" Tupac", "\n If I Die 2Nite", R.drawable.tupac1, R.raw.if_i_die_tonite));
        songs.add(new Song(" Tupac", "\n Temptations", R.drawable.tupac1, R.raw.temptations));
        songs.add(new Song(" Tupac", "\n Unconditional Love", R.drawable.tupac1, R.raw.unconditional_love));
        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songsListView = findViewById(R.id.list);
        songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(TupacActivity.this, song.getAudioResourceId());
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
