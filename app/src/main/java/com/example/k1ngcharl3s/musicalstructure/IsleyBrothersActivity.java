package com.example.k1ngcharl3s.musicalstructure;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class IsleyBrothersActivity {
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
        songs.add(new Song(" Isley Brothers", "\n Hello", R.drawable.isleybrothers, R.raw.hello));
        songs.add(new Song(" Isley Brothers", "\n Voyage To Atlantis", R.drawable.isleybrothers, R.raw.voyage_to_atlantis));
        songs.add(new Song(" Isley Brothers", "\n For The Love of You", R.drawable.isleybrothers, R.raw.for_the_love_of_you));
        songs.add(new Song(" Isley Brothers", "\n Choosey Lover", R.drawable.isleybrothers, R.raw.choosey_lover));
        songs.add(new Song(" Isley Brothers", "\n Between The Sheets", R.drawable.isleybrothers, R.raw.between_the_sheets));
        songs.add(new Song(" Isley Brothers", "\n Summer Breeze", R.drawable.isleybrothers, R.raw.summer_breeze));
        songs.add(new Song(" Isley Brothers", "\n I Once Had Your Love", R.drawable.isleybrothers, R.raw.once_had_your_love));
        songs.add(new Song(" Isley Brothers", "\n I Once Had Your Love", R.drawable.isleybrothers, R.raw.brown_eyed_girl));
        songs.add(new Song(" Isley Brothers", "\n I Once Had Your Love", R.drawable.isleybrothers, R.raw.fall_in_love));
        songs.add(new Song(" Isley Brothers", "\n I Once Had Your Love", R.drawable.isleybrothers, R.raw.make_me_say));
        songs.add(new Song(" Isley Brothers", "\n I Once Had Your Love", R.drawable.isleybrothers, R.raw.goodnight));
        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songsListView = findViewById(R.id.list);
        songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(IsleyBrothersActivity.this, song.getAudioResourceId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                mMediaPlayer.start();
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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

}
