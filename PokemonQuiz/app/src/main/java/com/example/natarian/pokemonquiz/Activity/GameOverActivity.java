package com.example.natarian.pokemonquiz.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.natarian.pokemonquiz.Activity.HighScoreActivity;
import com.example.natarian.pokemonquiz.Activity.PlayGameActivity;
import com.example.natarian.pokemonquiz.Activity.StartScreenActivity;
import com.example.natarian.pokemonquiz.DBContext;
import com.example.natarian.pokemonquiz.HighScoreListViewAdapter;
import com.example.natarian.pokemonquiz.R;
import com.example.natarian.pokemonquiz.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameOverActivity extends Activity implements View.OnClickListener {

    //Constant
    private final static int POPUP_HEIGHT = 720;
    private final static int POPUP_WIDTH = 550;

    TextView txtGameOver, txtPlayer, txtPlayerName, txtScore, txtPlayerScore;
    ImageButton btnAgain, btnFinish, btnHighScore;
    private int score;
    private String name;
    private HighScoreListViewAdapter adapter;

    private List<Score> list;
    private DBContext dbContext;
    MediaPlayer mpGameOver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        settingPopup();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = dbContext.getAllScore();
        adapter.swap(list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent openMainActivity= new Intent(this, StartScreenActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
    }

    public void init() {
        txtGameOver = (TextView) findViewById(R.id.txtGameOver);
        txtPlayer = (TextView) findViewById(R.id.txtPlayer);
        txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtPlayerScore = (TextView) findViewById(R.id.txtPlayerScore);

        btnAgain = (ImageButton) findViewById(R.id.btnAgain);
        btnFinish = (ImageButton) findViewById(R.id.btnFinish);
        btnHighScore = (ImageButton) findViewById(R.id.btnHighScore);

        mpGameOver = MediaPlayer.create(this, R.raw.gameover);

        if (mpGameOver.isPlaying()) {
            mpGameOver.stop();
        }

        mpGameOver.start();

        Typeface type1 = Typeface.createFromAsset(getAssets(),"GoodDog.otf");
        txtGameOver.setTypeface(type1);

        Typeface type2 = Typeface.createFromAsset(getAssets(),"LondrinaSolid-Regular.otf");
        txtPlayer.setTypeface(type2);
        txtScore.setTypeface(type2);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"TitanOne-Regular.ttf");
        txtPlayerName.setTypeface(type3);
        txtPlayerScore.setTypeface(type3);

        list = new ArrayList<>();

        adapter = new HighScoreListViewAdapter(this, list);
        dbContext = DBContext.getInst();
        list = dbContext.getAllScore();

        getData();
        addDataToDB();

        btnAgain.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnHighScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int GUI = v.getId();
        switch (GUI) {
            case R.id.btnHighScore:
                Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
                startActivity(intent);
                mpGameOver.stop();
                break;
            case R.id.btnAgain:
                Intent intent1 = new Intent(getApplicationContext(), PlayGameActivity.class);
                intent1.putExtra("name", name);
                startActivity(intent1);
                mpGameOver.stop();
                break;
            case R.id.btnFinish:
                backToStartScreen();
                break;
            default:
        }
    }




    //============================================================
    //Get Data From Intent
    public void getData() {
        name = getIntent().getStringExtra("name");
        score = getIntent().getIntExtra("score", score);

        txtPlayerName.setText(name);
        txtPlayerScore.setText(score + "");
    }


    //============================================================
    //Add Data to DB
    public void addDataToDB() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        String playTime = df.format(c.getTime());

        dbContext.addPlayerScore(Score.create(playTime, name, score));
        adapter.swap(list);
    }




    //============================================================
    //Back button of device
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backToStartScreen();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void backToStartScreen() {
        Intent intent2 = new Intent(getApplicationContext(), StartScreenActivity.class);
        startActivity(intent2);
        mpGameOver.stop();
    }



    //============================================================
    //Setting Popup
    public void settingPopup() {
        setFinishOnTouchOutside(false);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = POPUP_HEIGHT;
        params.width = POPUP_WIDTH;

        this.getWindow().setAttributes(params);
    }

}

