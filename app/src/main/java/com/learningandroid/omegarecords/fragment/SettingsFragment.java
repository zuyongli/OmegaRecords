package com.learningandroid.omegarecords.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.learningandroid.omegarecords.NavigationPane;
import com.learningandroid.omegarecords.R;
import com.learningandroid.omegarecords.service.BackgroundMusic;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * a simple settings fragment to turn on and turn off the background music
 */
public class SettingsFragment extends Fragment {

    private Boolean isMusicOn;
    private final String MUSIC_KEY = "MusicKey";

    public SettingsFragment() { }
    public SettingsFragment(Boolean isMusicOn) {
        this.isMusicOn = isMusicOn;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(MUSIC_KEY, isMusicOn);
        Log.d("save fragment", String.valueOf(isMusicOn));
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            Log.d("restore fragment", String.valueOf(isMusicOn));
            isMusicOn = savedInstanceState.getBoolean(MUSIC_KEY);
        }

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // use a switch to turn on/off the background music
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch aSwitch = view.findViewById(R.id.setting_background_music_switch);
        aSwitch.setChecked(isMusicOn);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) { // turn on background music
                Intent backgroundMusicIntent = new Intent(getContext(), BackgroundMusic.class);
                requireActivity().startService(backgroundMusicIntent);
                NavigationPane.IS_BACKGROUND_MUSIC_ON = true;
                Log.d("background music", "start background music");
            } else { // turn off background music
                Intent backgroundMusicIntent = new Intent(getContext(), BackgroundMusic.class);
                requireActivity().stopService(backgroundMusicIntent);
                NavigationPane.IS_BACKGROUND_MUSIC_ON = false;
                Log.d("background music", "stop background music");
            }
        });

        // apply changes, it has no effects on the settings
        // it simply remove the setting fragment when OK button is clicked
        Button applySettings = view.findViewById(R.id.setting_applay);
        applySettings.setOnClickListener((View v) ->{
            Log.d("setting apply", "apply button is clicked");
            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.setting_fragment_container);
            if(fragment != null) {
                Log.d("remove fragment", "remove the setting fragment");
                requireActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });

        return view;
    }
}