package com.example.natarian.pokemonquiz.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natarian.pokemonquiz.CustomAnimation;
import com.example.natarian.pokemonquiz.Question;
import com.example.natarian.pokemonquiz.QuestionList;
import com.example.natarian.pokemonquiz.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class PlayGameActivity extends Activity implements View.OnClickListener {

    //Constant
    private final static int LIFE = 3;
    private final static int SCORE = 0;
    private final static int HELP = 10;
    private final static int QUESTION_FINISH_DELAY = 1500;
    private final static int COLOR_GREEN_800 = Color.rgb(46, 125, 50);
    private final static int COLOR_RED_800 = Color.rgb(198, 40, 40);
    private final static int COLOR_BLUE_500 = Color.rgb(33, 150, 243);
    private final static int COLOR_BLACK_ALPHA_255 = Color.argb(255, 0, 0, 0);
    private final static int COLOR_BLACK_ALPHA_0 = Color.argb(0, 0, 0, 0);

    TextView txtScore, txtLife, txtResult, txtHelp;
    Button btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD;
    ImageButton btnHelp;
    ImageView imvQuiz;

    private QuestionList questionList;
    private Random random;
    private ArrayList<String> imageNameArr;
    private ArrayList<String> multipleChoiceArr;
    private ArrayList<String> storageArr;
    private ArrayList<String> pokemonNameArr;
    private ArrayList<String> answerArr;
    private int rdContent;
    private boolean isCorrect = false;
    private boolean needHelp = false;
    private String question;
    private String finalAnswer;
    private int life;
    private int score;
    private int help;
    private String name;

    MediaPlayer mpCorrect = null;
    MediaPlayer mpIncorrect = null;
    MediaPlayer mpHelp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        parseJson();
    }

    public void init() {
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtLife = (TextView) findViewById(R.id.txtLife);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtHelp = (TextView) findViewById(R.id.txtHelp);

        showDefaultResult();

        btnAnswerA = (Button) findViewById(R.id.btnAnswerA);
        btnAnswerB = (Button) findViewById(R.id.btnAnswerB);
        btnAnswerC = (Button) findViewById(R.id.btnAnswerC);
        btnAnswerD = (Button) findViewById(R.id.btnAnswerD);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);

        mpCorrect = MediaPlayer.create(this, R.raw.correct);
        mpIncorrect = MediaPlayer.create(this, R.raw.incorrect);
        mpHelp = MediaPlayer.create(this, R.raw.help);

        imvQuiz = (ImageView) findViewById(R.id.imvQuiz);
        imvQuiz.setColorFilter(COLOR_BLACK_ALPHA_255);
        random = new Random();

        name = getIntent().getStringExtra("name");

        life = LIFE;
        score = SCORE;
        help = HELP;

        txtScore.setText("" + score);
        txtLife.setText("" + life);
        txtHelp.setText("" + help);

        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int GUI = v.getId();
        try {
            switch (GUI) {
                case R.id.btnAnswerA:
                    runButtonAnimation(btnAnswerA);
                    checkAnswer(btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD);
                    playAnswerSound();
                    showPokemon();
                    showResult();
                    final Handler handlerA = new Handler();
                    handlerA.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestion();
                        }
                    }, QUESTION_FINISH_DELAY);

                    break;
                case R.id.btnAnswerB:
                    runButtonAnimation(btnAnswerB);
                    checkAnswer(btnAnswerB, btnAnswerA, btnAnswerC, btnAnswerD);
                    playAnswerSound();
                    showPokemon();
                    showResult();
                    final Handler handlerB = new Handler();
                    handlerB.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestion();
                        }
                    }, QUESTION_FINISH_DELAY);

                    break;
                case R.id.btnAnswerC:
                    runButtonAnimation(btnAnswerC);
                    checkAnswer(btnAnswerC, btnAnswerA, btnAnswerB, btnAnswerD);
                    playAnswerSound();
                    showPokemon();
                    showResult();
                    final Handler handlerC = new Handler();
                    handlerC.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestion();
                        }
                    }, QUESTION_FINISH_DELAY);

                    break;
                case R.id.btnAnswerD:
                    runButtonAnimation(btnAnswerD);
                    checkAnswer(btnAnswerD, btnAnswerA, btnAnswerB, btnAnswerC);
                    playAnswerSound();
                    showPokemon();
                    showResult();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestion();
                        }
                    }, QUESTION_FINISH_DELAY);

                    break;
                case R.id.btnHelp:
                    useHelp();
                    playHelpSound();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //============================================================
    //Load Random JSON File
    private void parseJson() {
        try {
            imageNameArr = new ArrayList<>();
            storageArr = new ArrayList<>();
            pokemonNameArr = new ArrayList<>();

            String jsonName = randomJSONPart();
            Gson gson = new Gson();
            questionList = gson.fromJson(loadJSONFromAsset(jsonName), QuestionList.class);
            Log.d("Bank", jsonName + " ==== Size: " + questionList.getQuestionList().size());
            for (Question q : questionList.getQuestionList()) {
                imageNameArr.add(q.getImgName().toLowerCase().toString());
                storageArr.add(q.getImgName().toLowerCase().toString());
                pokemonNameArr.add(q.getName().toString());
            }

            imvQuiz.setImageResource(getResources().getIdentifier(randomImage(), "drawable", getPackageName()));
            setTextForButton();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String jsonFileName) {
        String json;
        try {

            InputStream is = getAssets().open(jsonFileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String randomJSONPart() {
        String jsonPart;
        int max = 7;
        int min = 1;
        int randomNumber = random.nextInt(max) + min; //[1, 7]
        jsonPart = new String("bank" + randomNumber + ".json");
        return jsonPart;
    }


    //============================================================
    //Question Screen
    public String randomImage() {
        Random rd = new Random();
        String content;
        rdContent = rd.nextInt(imageNameArr.size());
        content = imageNameArr.get(rdContent).toLowerCase().toString();
        return content;
    }

    public void setTextForButton() {
        try {
            setDefaultQuestion();
            answerArr = new ArrayList<>();
            Random rd = new Random();
            multipleChoiceArr = new ArrayList<>(storageArr);

            Log.d("MultipleSize", multipleChoiceArr.size() + "===" + storageArr.size() + "===" + imageNameArr.size());

            question = imageNameArr.get(rdContent).toLowerCase().toString();
            String answer1, answer2, answer3, answer4;


            finalAnswer = pokemonNameArr.get(multipleChoiceArr.indexOf(question)).toString();
            answer1 = finalAnswer;
            multipleChoiceArr.remove(answer1);

            for (int i = 0; i < multipleChoiceArr.size(); i++) {
                String duplicate = multipleChoiceArr.get(i).toString();
                if (duplicate.equals(answer1)) {
                    multipleChoiceArr.remove(duplicate);
                }
            }

            int randomIndex1 = rd.nextInt(multipleChoiceArr.size());
            answer2 = pokemonNameArr.get(randomIndex1).toString();
            multipleChoiceArr.remove(answer2);

            int randomIndex2 = rd.nextInt(multipleChoiceArr.size());
            answer3 = pokemonNameArr.get(randomIndex2).toString();
            multipleChoiceArr.remove(answer3);

            int randomIndex3 = rd.nextInt(multipleChoiceArr.size());
            answer4 = pokemonNameArr.get(randomIndex3).toString();
            multipleChoiceArr.remove(answer4);

            answerArr.add(answer1);
            answerArr.add(answer2);
            answerArr.add(answer3);
            answerArr.add(answer4);

            int randomIndexAnswer1 = rd.nextInt(answerArr.size());
            btnAnswerA.setText(answerArr.get(randomIndexAnswer1).toString());
            answerArr.remove(randomIndexAnswer1);

            int randomIndexAnswer2 = rd.nextInt(answerArr.size());
            btnAnswerB.setText(answerArr.get(randomIndexAnswer2).toString());
            answerArr.remove(randomIndexAnswer2);

            int randomIndexAnswer3 = rd.nextInt(answerArr.size());
            btnAnswerC.setText(answerArr.get(randomIndexAnswer3).toString());
            answerArr.remove(randomIndexAnswer3);

            btnAnswerD.setText(answerArr.get(0).toString());

            Log.d("Answers", "Question: " + question + " ==== Answers: " + answer1 + "--" + answer2 + "--" + answer3 + "--" + answer4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //============================================================
    //Set Question
    public void setQuestion() {
        if (life > 0) {
            imvQuiz.setImageResource(getResources().getIdentifier(randomImage(), "drawable", getPackageName()));
            setTextForButton();
            showDefaultResult();
            setEnableButton(true);
            imageNameArr.remove(rdContent);
        }
    }




    //============================================================
    //Set Question
    public void useHelp() {
        try {
            String answerA = btnAnswerA.getText().toString();
            String answerB = btnAnswerB.getText().toString();
            String answerC = btnAnswerC.getText().toString();
            String answerD = btnAnswerD.getText().toString();

            if (answerA.equals(finalAnswer)) {
                removeAnswer(btnAnswerB, btnAnswerC, btnAnswerD);
            } else if (answerB.equals(finalAnswer)) {
                removeAnswer(btnAnswerA, btnAnswerC, btnAnswerD);
            } else if (answerC.equals(finalAnswer)) {
                removeAnswer(btnAnswerA, btnAnswerB, btnAnswerD);
            } else if (answerD.equals(finalAnswer)) {
                removeAnswer(btnAnswerA, btnAnswerB, btnAnswerC);
            }

            btnHelp.setEnabled(false);
            btnHelp.setBackgroundResource(R.drawable.custom_button_6_disable);

            if (help > 0) {
                help--;
                txtHelp.setText("" + help);
            } else {
                help = 0;
                txtHelp.setText("" + help);
            }

        } catch (Exception e) {

        }
    }

    public void removeAnswer(Button btnIncorrectAnswer1, Button btnIncorrectAnswer2, Button btnIncorrectAnswer3) {
        ArrayList<Button> buttonArr = new ArrayList<>();

        buttonArr.add(btnIncorrectAnswer1);
        buttonArr.add(btnIncorrectAnswer2);
        buttonArr.add(btnIncorrectAnswer3);

        Random rd = new Random();
        int btnIndex = rd.nextInt(buttonArr.size());
        buttonArr.remove(btnIndex);

        for (int i = 0; i < buttonArr.size(); i++) {
            buttonArr.get(i).setText("");
            buttonArr.get(i).setEnabled(false);
        }

        needHelp = true;
    }




    //============================================================
    //Answer Screen
    public void showResult() {
        if (isCorrect == true) {
            txtResult.setText("CORRECT");
            txtResult.setTextColor(COLOR_GREEN_800);
            runTextViewAnimation();
            score += 100;
            txtScore.setText("" + score);
        } else {
            txtResult.setText("INCORRECT");
            txtResult.setTextColor(COLOR_RED_800);
            runTextViewAnimation();
            life -= 1;
            txtLife.setText("" + life);
        }
        if (life <= 0 || imageNameArr.size() <= 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameOver();
                }
            }, QUESTION_FINISH_DELAY);
        }

    }

    private void showPokemon() {
        imvQuiz.setColorFilter(COLOR_BLACK_ALPHA_0);
    }

    public void checkAnswer(Button chosenButton, Button otherButton1, Button otherButton2, Button otherButton3) {
        try {
            String chosenButtonTitle = chosenButton.getText().toString();
            String otherButtonTitle1 = otherButton1.getText().toString();
            String otherButtonTitle2 = otherButton2.getText().toString();
            String otherButtonTitle3 = otherButton3.getText().toString();

            if (chosenButtonTitle.equalsIgnoreCase(finalAnswer)) {
                isCorrect = true;
                chosenButton.setBackgroundResource(R.drawable.custom_button_4_correct);
            } else {
                isCorrect = false;
                chosenButton.setBackgroundResource(R.drawable.custom_button_4_incorrect);
                if (otherButtonTitle1.equalsIgnoreCase(finalAnswer)) {
                    otherButton1.setBackgroundResource(R.drawable.custom_button_4_correct);
                } else if (otherButtonTitle2.equalsIgnoreCase(finalAnswer)) {
                    otherButton2.setBackgroundResource(R.drawable.custom_button_4_correct);
                } else if (otherButtonTitle3.equalsIgnoreCase(finalAnswer)) {
                    otherButton3.setBackgroundResource(R.drawable.custom_button_4_correct);
                }
            }
        } catch (Exception e) {
            isCorrect = false;
        }
    }

    public void playAnswerSound() {
        if (mpCorrect.isPlaying() || mpIncorrect.isPlaying()) {
            mpCorrect.stop();
            mpIncorrect.stop();
        }

        if (isCorrect == true) {
            mpCorrect.start();
        } else {
            mpIncorrect.start();
        }
    }

    public void playHelpSound() {
        if (needHelp == true) {
            mpHelp.start();
        }
    }


    //============================================================
    //Game Over
    public void gameOver() {
        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("name", name);
        startActivity(intent);

    }


    //============================================================
    //Animation
    public void runButtonAnimation(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.increase);

        // Use animation with amplitude 0.5 and frequency 50
        CustomAnimation animation = new CustomAnimation(0.5, 50);
        myAnim.setInterpolator(animation);

        button.startAnimation(myAnim);

        setEnableButton(false);

    }

    public void runTextViewAnimation() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.scale_text_view);

        // Use animation with amplitude 0.3 and frequency 25
        CustomAnimation animation = new CustomAnimation(0.3, 25);
        myAnim.setInterpolator(animation);

        txtResult.startAnimation(myAnim);

        setEnableButton(false);
    }


    //============================================================
    //Set Enable Button
    public void setEnableButton(boolean status) {
        btnAnswerA.setEnabled(status);
        btnAnswerB.setEnabled(status);
        btnAnswerC.setEnabled(status);
        btnAnswerD.setEnabled(status);
        btnHelp.setEnabled(status);
    }


    //============================================================
    //Default Screen
    public void showDefaultResult() {
        txtResult.setText("WHO'S THAT POKÉMON?");
        txtResult.setTextColor(COLOR_BLUE_500);
    }

    public void setDefaultQuestion() {
        imvQuiz.setColorFilter(COLOR_BLACK_ALPHA_255);

        if (help > 0) {
            btnHelp.setEnabled(true);
            btnHelp.setBackgroundResource(R.drawable.custom_button_6);
            needHelp = false;
        }

        btnAnswerA.setEnabled(true);
        btnAnswerB.setEnabled(true);
        btnAnswerC.setEnabled(true);
        btnAnswerD.setEnabled(true);

        btnAnswerA.setBackgroundResource(R.drawable.custom_button_4);
        btnAnswerB.setBackgroundResource(R.drawable.custom_button_4);
        btnAnswerC.setBackgroundResource(R.drawable.custom_button_4);
        btnAnswerD.setBackgroundResource(R.drawable.custom_button_4);
    }


    //============================================================
    //Back button of device
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quitGame();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void quitGame() {
        final Dialog dialog = new Dialog(this, R.style.cust_dialog);
        dialog.setContentView(R.layout.custom_dialog);

        TextView txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);
        Button btnYesDialog = (Button) dialog.findViewById(R.id.btnYesDialog);
        Button btnNoDialog = (Button) dialog.findViewById(R.id.btnNoDialog);

        btnYesDialog.setText("Yes");
        btnNoDialog.setText("No");
        txtMsg.setText("Do you want to Give up?");

        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOver();
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