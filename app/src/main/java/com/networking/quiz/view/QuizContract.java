package com.networking.quiz.view;

import android.provider.BaseColumns;

public final class QuizContract {


    public QuizContract(){
    }

     public static class QuestionTable implements BaseColumns {

         public static final String TABLE_QUESTION_NAME = "question";
         public static final String COLUMN_QUESTION_DESC = "desc_question";
         public static final String COLUMN_QUESTION_DIFFICULTE = "difficulte";

         public static final String TABLE_ANSWER_NAME = "answer";
         public static final String COLUMN_ANSWER_DESC = "desc_question";
         public static final String COLUMN_ANSWER_CORRECT = "is_correct";
         public static final String COLUMN_ANSWER_QUESTION = "id_question";




     }


}
