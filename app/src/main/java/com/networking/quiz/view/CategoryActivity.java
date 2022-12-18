package com.networking.quiz.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.networking.quiz.data.QuizDbHelper;
import com.networking.quiz.R;
import com.networking.quiz.controller.Controller;
import com.networking.quiz.model.Player;
import com.networking.quiz.model.Question;
import com.networking.quiz.model.QuizGame;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnFacile, btnDifficile, btnMoyen, btnExpert;
    private EditText txtNom;
    private Controller monController;
    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnFacile = findViewById(R.id.btnFacile);
        btnMoyen = findViewById(R.id.btnMoyen);
        btnDifficile = findViewById(R.id.btnDifficile);
        btnExpert = findViewById(R.id.btnExpert);
        txtNom = findViewById(R.id.txtNom);


        btnExpert.setOnClickListener(this);
        btnDifficile.setOnClickListener(this);
        btnMoyen.setOnClickListener(this);
        btnFacile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (!(txtNom.getText().length() == 0)) {
            monController = new Controller();

            player = new Player(String.valueOf(txtNom.getText()), 0);

            switch (view.getId()) {
                case R.id.btnFacile:  // Facile
                    startQuiz(1);
                    break;
                case R.id.btnMoyen:  // Moyen
                    startQuiz(2);
                    break;
                case R.id.btnDifficile:  // Difficile
                    startQuiz(3);
                    break;
                case R.id.btnExpert:  // Expert
                    startQuiz(4);
                    break;
            }
        } else {
            Toast.makeText(this, "Veuillez saisir votre nom !", Toast.LENGTH_SHORT).show();
        }

    }
    ArrayList<Question> quizQuestions;

    public void startQuiz(int difficultyQuiz) {

        fetchDB(difficultyQuiz);

        Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);

        monController.setLaGame(new QuizGame(player, quizQuestions));

        intent.putExtra("monController", monController); // la clé, la valeur
        startActivity(intent);


    }

    public void fetchDB(int difficultyQuiz) {
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        quizQuestions = dbHelper.getAllQuestionsWithCategory(difficultyQuiz);  // calling the method with category
    }
}
