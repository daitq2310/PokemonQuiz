package com.example.natarian.pokemonquiz.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.natarian.pokemonquiz.DBContext;
import com.example.natarian.pokemonquiz.HighScoreListViewAdapter;
import com.example.natarian.pokemonquiz.R;
import com.example.natarian.pokemonquiz.Score;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends Activity implements View.OnClickListener {

    private HighScoreListViewAdapter adapter;
    private List<Score> list;
    private DBContext dbContext;
    Button btnHome;
    TextView txtHighScore;
    ListView lvHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = dbContext.getAllScore();
        adapter.swap(list);
    }

    public void init() {
        btnHome = (Button) findViewById(R.id.btnHome);

        txtHighScore = (TextView) findViewById(R.id.txtHighScore);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"Aller_Bd.ttf");
        txtHighScore.setTypeface(type1);

        lvHighScore = (ListView) findViewById(R.id.lvHighScore);

        list = new ArrayList<>();
        adapter = new HighScoreListViewAdapter(this, list);
        adapter.swap(list);

        lvHighScore.setAdapter(adapter);
        dbContext = DBContext.getInst();

        btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int GUI = v.getId();
        switch (GUI) {
            case R.id.btnHome:
                backToStartScreen();
                break;

            default:
        }
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
        Intent intent = new Intent(getApplicationContext(), StartScreenActivity.class);
        startActivity(intent);
    }
}
