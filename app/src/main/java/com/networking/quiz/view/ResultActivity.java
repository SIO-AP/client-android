package com.networking.quiz.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.networking.quiz.R;
import com.networking.quiz.controller.Controller;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuizQues,txtCorrectQues,txtWrongQues;

    Button btStartQuiz;
    Button btMainMenu;

    private int highScore;
    public static final String SHARED_PREFERRENCE = "shread_prefrence";
    public static final String SHARED_PREFERRENCE_HIGH_SCORE = "shread_prefrence_high_score";

    private long backPressedTime;


    String CategoryAgainValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        final Controller monController = intent.getParcelableExtra("monController");
        int correctQues = intent.getIntExtra("CorrectQues",0);
        int wrongQues = intent.getIntExtra("WrongQues",0);


        btMainMenu = findViewById(R.id.result_bt_mainmenu);
        btStartQuiz = findViewById(R.id.result_bt_playAgain);
        txtHighScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuizQues = findViewById(R.id.result_total_Ques);
        txtCorrectQues = findViewById(R.id.result_Correct_Ques);
        txtWrongQues = findViewById(R.id.result_Wrong_Ques);




        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity.this, PlayActivity.class);
                startActivity(intent);

            }
        });

        btStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monController.getLaGame().getMyPlayer().setMyScore(0);

                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                intent.putExtra("monController", monController);
                startActivity(intent);
            }
        });


        loadHighScore();





        txtTotalQuizQues.setText("Total Ques: " + String.valueOf(monController.getLaGame().getQuestions().size()));
        txtCorrectQues.setText("Correct: " + String.valueOf(correctQues));
        txtWrongQues.setText("Wrong: " + String.valueOf(wrongQues));

        if (monController.getLaGame().getMyPlayer().getMyScore() > highScore){

            updatHighScore(monController.getLaGame().getMyPlayer().getMyScore());
        }


    }

    private void updatHighScore(int newHighScore) {

        highScore = newHighScore;
        txtHighScore.setText("High Score: " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERRENCE_HIGH_SCORE,highScore);
        editor.apply();


    }

    private void loadHighScore() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE,MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERRENCE_HIGH_SCORE,0);
        txtHighScore.setText("High Score: " + String.valueOf(highScore));

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){

            Intent intent = new Intent(ResultActivity.this,PlayActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG","onStop() in PlayActivity");
        finish();

    }
}
