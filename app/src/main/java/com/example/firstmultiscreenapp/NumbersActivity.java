package com.example.firstmultiscreenapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        //create AudioManager
        mAudioManager=(AudioManager) getSystemService(AUDIO_SERVICE);


      final  ArrayList<Word> words=new ArrayList<Word>();

        words.add(new Word("one","ek",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","do",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tin",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","char",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","paach",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","chae",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","saat",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","aat",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","no",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","dus",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter itemAdapter=new WordAdapter(this,R.layout.list_items,words,R.color.category_numbers);
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
                //  mAudioManager.registerMediaButtonEventReceiver(new

                  //Create mediaPlayer and start it!
                  mMediaPlayer = MediaPlayer.create(NumbersActivity.this, w.getAudioResourceId());
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
        //abundant audioFocus
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}