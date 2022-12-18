package com.networking.quiz.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.networking.quiz.R;
import com.networking.quiz.controller.Controller;
import com.networking.quiz.model.Question;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class QuizActivity extends AppCompatActivity {


    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;


    
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;


    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Question currentQuestions;
    private boolean answerd;
    
    
    private final Handler handler = new Handler();




    private int correctAns = 0, wrongAns = 0;

    private TimerDialog timerDialog;
    private CorrectDialog correctDialog;
    private WrongDialog wrongDialog;

    private PlayAudioForAnswers playAudioForAnswers;

    int FLAG = 0;

    int score =0;

    private int totalSizeofQuiz=0;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMillis;

    private long backPressedTime;

    private String categoryValue;

    private Controller monController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        setupUI();

        Intent intentCategory = getIntent();
        categoryValue = intentCategory.getStringExtra("Category");

        monController = intentCategory.getParcelableExtra("monController");

        startQuiz();
        Log.i("BUGBUG","onCreate() in QuizActivity");


        timerDialog = new TimerDialog(this);
        correctDialog = new CorrectDialog(this);
        wrongDialog = new WrongDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);
    }



    private void setupUI(){
        textViewQuestion = findViewById(R.id.txtQuestionContainer);
        
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewCountDown = findViewById(R.id.txtViewTimer);


        
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button);
    }




     public void startQuiz() {

        questionTotalCount = monController.getLaGame().getQuestions().size();
        Collections.shuffle(monController.getLaGame().getQuestions());
        for (int i=0; i<monController.getLaGame().getQuestions().size(); i++) {
            Collections.shuffle(monController.getLaGame().getQuestions().get(i).getAnswers());
        }
        showQuestions();   // calling showQuestion() method



         rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {



                 switch (checkedId){

                     case R.id.radio_button1:

                         rb1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                         rb2.setTextColor(Color.BLACK);
                         rb3.setTextColor(Color.BLACK);
                         rb4.setTextColor(Color.BLACK);



                         rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                         rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                     break;
                     case R.id.radio_button2:
                         rb2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                         rb1.setTextColor(Color.BLACK);
                         rb3.setTextColor(Color.BLACK);
                         rb4.setTextColor(Color.BLACK);



                         rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                         rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                         break;

                     case R.id.radio_button3:
                         rb3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                         rb2.setTextColor(Color.BLACK);
                         rb1.setTextColor(Color.BLACK);
                         rb4.setTextColor(Color.BLACK);


                         rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                         rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                         break;

                     case R.id.radio_button4:
                         rb4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                         rb2.setTextColor(Color.BLACK);
                         rb3.setTextColor(Color.BLACK);
                         rb1.setTextColor(Color.BLACK);



                         rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                         rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                         rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                         break;
                 }

             }
         });

         buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!answerd) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {

                        quizOperation();
                    } else {

                        Toast.makeText(QuizActivity.this, "Please Select the Answer ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    public void showQuestions() {


        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        if (questionCounter < questionTotalCount) {
            currentQuestions = monController.getLaGame().getQuestions().get(questionCounter);
            textViewQuestion.setText(currentQuestions.getDescriptionQuestion());
            String t = currentQuestions.getAnswers().get(0).getDescriptionAnswer();
            rb1.setText(currentQuestions.getAnswers().get(0).getDescriptionAnswer());
            rb2.setText(currentQuestions.getAnswers().get(1).getDescriptionAnswer());
            rb3.setText(currentQuestions.getAnswers().get(2).getDescriptionAnswer());
            rb4.setText(currentQuestions.getAnswers().get(3).getDescriptionAnswer());

            questionCounter++;
            answerd = false;
            buttonConfirmNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter + "/" + questionTotalCount);


            timeleftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();




        } else {

            // If Number of Questions Finishes then we need to finish the Quiz and Shows the User Quiz Performance


            Toast.makeText(this, "Quiz Finshed", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonConfirmNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    finalResult();

                }
            }, 2000);
        }
    }

    private void quizOperation() {
        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected);

        checkSolution(answerNr, rbselected);

    }


    private void checkSolution(int answerNr, RadioButton rbselected) {
        if (monController.getLaGame().isCorrectThisAnswer(currentQuestions, answerNr)) {
           changetoCorrectColor(rbselected);

            correctAns++;
            textViewScore.setText("Score: " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
            correctDialog.correctDialog(monController.getLaGame().getMyPlayer().getMyScore(), this);


            FLAG = 1;
            playAudioForAnswers.setAudioforAnswer(FLAG);


        } else {
            changetoIncorrectColor(rbselected);

            wrongAns++;

            String correctAnswer = (String) rb1.getText();
            wrongDialog.wrongDialog(correctAnswer, this);

            FLAG = 2;
            playAudioForAnswers.setAudioforAnswer(FLAG);




        }
/*
        switch (answerNr) {
            case 1:
                if (monController.getLaGame().isCorrectThisAnswer(currentQuestions, answerNr-1)) {
                    rb1.setBackground(ContextCompat.getDrawable(
                            this, R.drawable.correct_option_background));
                    rb1.setTextColor(Color.BLACK);

                    correctAns++;
                    textViewScore.setText("Score: " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
                    correctDialog.correctDialog(monController.getLaGame().getMyPlayer().getMyScore(), this);


                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);


                } else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                }
                break;

            case 2:
                if (monController.getLaGame().isCorrectThisAnswer(currentQuestions, answerNr-1)) {

                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb2.setTextColor(Color.BLACK);

                    correctAns++;
                    textViewScore.setText("Score: " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
                    correctDialog.correctDialog(monController.getLaGame().getMyPlayer().getMyScore(), this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);



                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;


                    String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                }
                break;
            case 3:
                if (monController.getLaGame().isCorrectThisAnswer(currentQuestions, answerNr-1)) {

                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb3.setTextColor(Color.BLACK);


                    correctAns++;
                    textViewScore.setText("Score: " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
                    correctDialog.correctDialog(monController.getLaGame().getMyPlayer().getMyScore(), this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);



                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;


                    String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                }
                break;
            case 4:
                if (monController.getLaGame().isCorrectThisAnswer(currentQuestions, answerNr-1)) {

                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb4.setTextColor(Color.BLACK);


                    correctAns++;
                    textViewScore.setText("Score: " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
                    correctDialog.correctDialog(monController.getLaGame().getMyPlayer().getMyScore(), this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;


                    String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                }
                break;
        }*/
        if (questionCounter == questionTotalCount) {
            buttonConfirmNext.setText("Confirm and Finish");

        }
    }

    void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));

        rbselected.setTextColor(Color.BLACK);
    }

    void changetoCorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
        rbselected.setTextColor(Color.BLACK);
    }



                 //  the timer code

    private void startCountDown(){

        countDownTimer = new CountDownTimer(timeleftinMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinMillis = millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeleftinMillis = 0;
                updateCountDownText();

            }
        }.start();

    }



    private void updateCountDownText(){

     int minutes = (int) (timeleftinMillis/1000) /60;
     int seconds = (int) (timeleftinMillis/1000) % 60;

      //  String timeFormatted = String.format(Locale.getDefault(),"02d:%02d",minutes,seconds);

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
        textViewCountDown.setText(timeFormatted);


     if (timeleftinMillis < 10000){
         
         
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.red));
         
         FLAG = 3;
         playAudioForAnswers.setAudioforAnswer(FLAG);
         
         
     }else {
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.white));
     }


     if (timeleftinMillis == 0 ){


         Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();


         handler.postDelayed(new Runnable() {
             @Override
             public void run() {

                timerDialog.timerDialog();

             }
         },2000);



     }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("BUGBUG","onRestart() in QuizActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG","onStop() in QuizActivity");
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("BUGBUG","onPause() in QuizActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("BUGBUG","onResume() in QuizActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("BUGBUG","onStart() in QuizActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        Log.i("BUGBUG","onDestroy() in QuizActivity");


    }


    private void finalResult(){

        Intent resultData = new Intent(QuizActivity.this, ResultActivity.class);
        resultData.putExtra("monController", monController);
        resultData.putExtra("CorrectQues",correctAns);
        resultData.putExtra("WrongQues",wrongAns);

        startActivity(resultData);

    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){

            Intent intent = new Intent(QuizActivity.this, PlayActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }
}
