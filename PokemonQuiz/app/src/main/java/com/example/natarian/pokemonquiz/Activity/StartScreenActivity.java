package com.example.natarian.pokemonquiz.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natarian.pokemonquiz.R;

public class StartScreenActivity extends Activity implements View.OnClickListener {

    Button btnPlay, btnHighScore, btnExit;
    EditText edtName;
    MediaPlayer mpOpening = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    public void init() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnHighScore = (Button) findViewById(R.id.btnHighScore);
        btnExit = (Button) findViewById(R.id.btnExit);

        edtName = (EditText) findViewById(R.id.edtName);

        mpOpening = MediaPlayer.create(this, R.raw.opening);

        if (mpOpening.isPlaying()) {
            mpOpening.stop();
        }

        mpOpening.start();
        mpOpening.setLooping(true);

        btnPlay.setOnClickListener(this);
        btnHighScore.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int GUI = v.getId();
        switch (GUI) {
            case R.id.btnPlay:
                String name = edtName.getText().toString();
                if (name.equals("") || name == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                } else {
                    Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    mpOpening.stop();
                }
                break;
            case R.id.btnHighScore:
                Intent intent1 = new Intent(getApplicationContext(), HighScoreActivity.class);
                startActivity(intent1);
                mpOpening.stop();
                break;
            case R.id.btnExit:
                exitApp();
                break;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mpOpening.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mpOpening.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent openMainActivity= new Intent(this, StartScreenActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
        mpOpening.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            exitApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitApp() {
        final Dialog dialog = new Dialog(this, R.style.cust_dialog);
        dialog.setContentView(R.layout.custom_dialog);

        TextView txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);
        Button btnYesDialog = (Button) dialog.findViewById(R.id.btnYesDialog);
        Button btnNoDialog = (Button) dialog.findViewById(R.id.btnNoDialog);

        btnYesDialog.setText("Yes");
        btnNoDialog.setText("No");
        txtMsg.setText("Do you want to Exit Game?");

        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpOpening.stop();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        btnNoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
