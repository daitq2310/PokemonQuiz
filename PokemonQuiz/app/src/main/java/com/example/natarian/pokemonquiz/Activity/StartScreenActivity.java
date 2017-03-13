package com.example.natarian.pokemonquiz.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natarian.pokemonquiz.R;

public class StartScreenActivity extends Activity implements View.OnClickListener {

    TextView txtQuiz;
    Button btnPlay, btnHighScore, btnExit;
    EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    public void init() {
//        txtQuiz = (TextView) findViewById(R.id.txtQuiz);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnHighScore = (Button) findViewById(R.id.btnHighScore);
        btnExit = (Button) findViewById(R.id.btnExit);

        edtName = (EditText) findViewById(R.id.edtName);

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
                }
                break;
            case R.id.btnHighScore:
                Intent intent1 = new Intent(getApplicationContext(), HighScoreActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnExit:
                exitApp();
                break;
        }


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
