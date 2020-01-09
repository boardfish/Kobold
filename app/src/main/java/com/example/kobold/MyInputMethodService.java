package com.example.kobold;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.Random;

public class MyInputMethodService extends android.inputmethodservice.InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private AudioManager am;
    private MediaPlayer mp;
    private PlaybackParams params;

    private boolean caps = false;
    private int resources[] = new int[]{R.raw.kazooie1, R.raw.kazooie2};

    {
        params = new PlaybackParams();
        params.setPitch(1f);
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keys_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.kazooie1);
        mp.setPlaybackParams(params);
        am = (AudioManager)getSystemService(AUDIO_SERVICE);
        return keyboardView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            playSound(primaryCode);
            switch(primaryCode) {
                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                    break;
                default :
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);

            }
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void setSound() {
        // Randomise pitch
        // Either use existing or create new media player
        Random r = new Random();
        if (r.nextInt(2) == 1) {
            // TODO: change sound?
            mp.stop();
            mp.release();
            mp = MediaPlayer.create(getApplicationContext(), resources[r.nextInt(resources.length)]);
        }
        float newPitch = 1.1f + r.nextFloat() * (1.25f - 1.1f);
        float newSpeed = 1.0f + r.nextFloat() * (1.3f - 1.0f);
        params = new PlaybackParams();
        params.setPitch(newPitch);
        params.setSpeed(newSpeed);
        mp.setPlaybackParams(params);
    }

    private void playSound(int keyCode) {
        setSound();
        am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: mp.start();
        }
    }
}
