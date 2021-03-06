package com.example.firstmultiscreenapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                toRelease();
            }

        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            toRelease();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list_for_all);
        mAudioManager=(AudioManager) getSystemService(AUDIO_SERVICE);

       final ArrayList<Word> words=new ArrayList<Word>();

        words.add(new Word("red","lal",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("mustard yellow","mustard vala pila",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow","ganda pila",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("brown","bhura",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("white","safed",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("black","kala",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("gray","islati",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("green","hara",R.drawable.color_green,R.raw.color_green));


        WordAdapter itemAdapter=new WordAdapter(this,R.layout.list_items,words,R.color.category_colors);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);
        //setting a quick listener for the list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w= words.get(i);
                //release the media player if it currently exist bcoz we gonna play a different audio now.
                toRelease();
                //request AudioFocus to play
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, w.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        toRelease();
    }
    void toRelease() {
        //to release the source becoz we no longer need it!
        if (mMediaPlayer != null) mMediaPlayer.release();
        //set the media player back to null. It is a good way to tell that media player is not configurated to play the audio at a moment.
        mMediaPlayer = null;
    }
}