
package com.example.mediaplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class audio_Fragment extends Fragment {


    TextView playerPosition, playerDuration,nowPlaying;
    SeekBar seekBar;
    ImageView rewind,play,pause,forward;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
//    Uri path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View audioView = inflater.inflate(R.layout.audio_fragment, container, false);

        initialise(audioView); //method to initialise all the widgets and path//
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.jazzjam);
        nowPlaying.setText("Now Playing : Give Thanks Jazz by Josh");

        runnable = new Runnable() {
            @Override
            public void run() {
                //setting progress bar//
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //Handler delay for 0.5 seconds//
                handler.postDelayed(this,500);
            }
        };

        //getting the time of the media Player//
        int duration = mediaPlayer.getDuration();

        //Converting it into proper time//
        String sDuration = convertFormat(duration);

        //Displaying the Time in the Fragment//
        playerDuration.setText(sDuration);

        play.setOnClickListener(v -> {
            //Converting play button into pause button and play the song using media Player//
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            mediaPlayer.start();

            //Setting max duration in progressBar and start the handler//
            seekBar.setMax(mediaPlayer.getDuration());
            handler.postDelayed(runnable,0);
        });

        pause.setOnClickListener(v -> {
            //Covert Pause button to Play Button and pause the song using Media Player//
            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
            mediaPlayer.pause();

            //Stopping the handler//
            handler.removeCallbacks(runnable);
        });

        forward.setOnClickListener(v -> {
            //Getting the current position and duration of the mediaPlayer//
            int currentPosition = mediaPlayer.getCurrentPosition();
            int currentDuration =  mediaPlayer.getDuration();

            //Checking if audio is being played and we are not at the end of the song//
            if(mediaPlayer.isPlaying() && currentDuration != currentPosition){
                //Code to skip 5 seconds ahead and assign it to the mediaPlayer and textView//
                currentPosition += 5000;
                playerPosition.setText(convertFormat(currentPosition));

                //assign progress on seekBar//
                mediaPlayer.seekTo(currentPosition);
            }
        });

        rewind.setOnClickListener(v -> {
            //Getting current position and duration of the mediaplayer//
            int currentPosition = mediaPlayer.getCurrentPosition();
            int currentDuration = mediaPlayer.getDuration();

            //Checking if audio is playing and we are not at the beginning of the song//
            if(mediaPlayer.isPlaying() &&  currentPosition > 5000){
                //Code to skip 5 seconds behind  and assign it to the textView and mediaPlayer//
                currentPosition -= 5000;
                playerPosition.setText(convertFormat(currentDuration));
                //assign progress on seekbar//
                mediaPlayer.seekTo(currentPosition);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Checking so that when we drag the progressBar it changes the location of the song//
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                //assigning the new position in textView//
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //When a song has completed Playing//
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Hide pause button and display play button//
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);

                //Assigning The media player and progress to the initial position 00:00//
                mediaPlayer.seekTo(0);
            }
        });

        return (audioView);
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    void initialise(View audioView){
        playerPosition = audioView.findViewById(R.id.player_position);
        playerDuration = audioView.findViewById(R.id.player_duration);
        seekBar = audioView.findViewById(R.id.seek_bar);
        rewind = audioView.findViewById(R.id.bt_rew);
        play = audioView.findViewById(R.id.bt_play);
        pause = audioView.findViewById(R.id.bt_pause);
        forward = audioView.findViewById(R.id.bt_ff);
        nowPlaying = audioView.findViewById(R.id.currentSong);
//        path = Uri.parse("/storage/emulated/0/Music/NewPipe/Give Thanks.m4a");
    }
}