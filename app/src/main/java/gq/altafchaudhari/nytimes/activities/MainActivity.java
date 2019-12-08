package gq.altafchaudhari.nytimes.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

import gq.altafchaudhari.nytimes.R;
import gq.altafchaudhari.nytimes.base.MyApplication;
import gq.altafchaudhari.nytimes.base.BaseAppCompatActivity;
import gq.altafchaudhari.nytimes.fragments.PopularListFragment;
import gq.altafchaudhari.nytimes.utils.MyDialogBox;

public class MainActivity extends BaseAppCompatActivity implements MyDialogBox.MyDialogListener {

    private final int REQ_CODE_SPEECH_INPUT = 1000;
    private  static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        getMostPopular();
    }

    @Override
    public void onBackPressed() {
           super.onBackPressed();
    }


    private void getMostPopular() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, new PopularListFragment()).commit();
    }

    public void changeStatusBarColor()
    {
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void getVoiceInput(View v)
    {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak \"Load Data\" to load new articles");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            /*Toast.makeText(getApplicationContext(),
                    "Voice recognition not supported",
                    Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    if(result.get(0).equals("load data"))
                    {
                        openDialog();
                    }
                }
                break;
            }
        }
    }

    public void openDialog() {
        MyDialogBox myDialogBox = new MyDialogBox();
        myDialogBox.show(getSupportFragmentManager(), "Enter Details");
    }

    @Override
    public void applyTexts(String mSection, String mPassword) {
        MyApplication.SECTION = mSection;
        MyApplication.PERIOD = mPassword;
        getMostPopular();
        Log.e(TAG,"New Selection:"+ MyApplication.SECTION+", "+ MyApplication.PERIOD);
    }
}