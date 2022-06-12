package com.example.firstmultiscreenapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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

        words.add(new Word("father","papa",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","mummi",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("older Brother","bada bhai",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Younger brother","chota bhai",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister ","badi bhen",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","choti bhen",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grand ma","dadi",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grand pa","dada",R.drawable.family_grandfather,R.raw.family_grandfather));


        WordAdapter itemAdapter=new WordAdapter(this,R.layout.list_items,words,R.color.category_family);
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

                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, w.getAudioResourceId());
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