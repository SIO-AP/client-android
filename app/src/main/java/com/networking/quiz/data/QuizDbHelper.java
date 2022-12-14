package com.networking.quiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import com.networking.quiz.view.QuizContract.*;
import com.networking.quiz.model.Answer;
import com.networking.quiz.model.Question;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GoQuiz.db";
    private static final int DATBASE_VERSION = 2;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATBASE_VERSION);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionTable.TABLE_QUESTION_NAME +
                " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION_DESC + " TEXT, " +
                QuestionTable.COLUMN_QUESTION_DIFFICULTE + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);


        final String SQL_CREATE_ANSWER_TABLE = "CREATE TABLE " + QuestionTable.TABLE_ANSWER_NAME +
                " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_ANSWER_DESC + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_CORRECT + " BOOLEAN, " +
                QuestionTable.COLUMN_ANSWER_QUESTION + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_ANSWER_TABLE);


        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_ANSWER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_QUESTION_NAME);
        onCreate(db);

    }





    private void addQuestions(Question question) {

        ContentValues cv = new ContentValues();

        cv.put(QuestionTable.COLUMN_QUESTION_DESC, question.getDescriptionQuestion());
        cv.put(QuestionTable.COLUMN_QUESTION_DIFFICULTE, question.getDifficulty());
        db.insert(QuestionTable.TABLE_QUESTION_NAME, null, cv);

        for (int i = 0; i < question.getAnswers().size(); i++) {

            cv = new ContentValues();

            cv.put(QuestionTable.COLUMN_ANSWER_DESC, question.getAnswers().get(i).getDescriptionAnswer());
            cv.put(QuestionTable.COLUMN_ANSWER_CORRECT, question.getAnswers().get(i).getIsCorrect());
            cv.put(QuestionTable.COLUMN_ANSWER_QUESTION, question.getAnswers().get(i).getCodeAnswer());

            db.insert(QuestionTable.TABLE_ANSWER_NAME, null, cv);
        }


    }

    public ArrayList<Question> getAllQuestionsWithCategory(int difficulty) {

        ArrayList<Question> questionList = new ArrayList<>();
        ArrayList<Answer> answers;
        db = getReadableDatabase();


        try (Cursor resulQ = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_QUESTION_NAME + " WHERE " + QuestionTable.COLUMN_QUESTION_DIFFICULTE + " = " + difficulty, null)) {

            if (resulQ.getColumnCount() != 0) {
                while (resulQ.moveToNext()) {

                    int id = resulQ.getInt(0);

                    answers = new ArrayList<>();
                    Cursor resultA = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_ANSWER_NAME + " WHERE " + QuestionTable.COLUMN_ANSWER_QUESTION + " = " + id, null);

                    while (resultA.moveToNext()) {
                        answers.add(new Answer(resultA.getString(0), resultA.getString(1), (resultA.getInt(2) > 0)));
                    }

                    questionList.add(new Question(resulQ.getString(1), answers));

                }

            }

        }
        return questionList;
    }


    private void fillQuestionsTable() {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer("1", "Vaisseau pour neptune, resA", false));
        answers.add(new Answer("1", "R??seau priv?? virtuel", true));
        answers.add(new Answer("1", "Visual Protection Network", false));
        answers.add(new Answer("1", "Marque d???ordinateurs", false));
        Question question = new Question("Qu???est ce qu???un VPN ?", answers, 2);
        addQuestions(question);



        answers = new ArrayList<Answer>();
        answers.add(new Answer("2", "Attaque par d??ni de service", true));
        answers.add(new Answer("2", "Data Destructor on system", false));
        answers.add(new Answer("2", "Force secr??te militaire", false));
        answers.add(new Answer("2", "Logiciel Antivirus", false));
        question = new Question("Que signifie DDOS ?", answers, 3);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("3", "azerty", false));
        answers.add(new Answer("3", "azerty1234", false));
        answers.add(new Answer("3", "azerty1234!", false));
        answers.add(new Answer("3", "@z3rty4321!", true));
        question = new Question("Quel mot de passe est le plus s??curis?? ?", answers, 1);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("4", "R??v??ler les secrets", false));
        answers.add(new Answer("4", "Prot??ger le syst??me d???information", true));
        answers.add(new Answer("4", "Augmenter les risques pesant sur le syst??me d???information", false));
        answers.add(new Answer("4", "Rendre difficile la vie des utilisateurs en ajoutant plusieurs contraintes", false));
        question = new Question("Quel est l'enjeu de la cybers??curit?? ?", answers, 1);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("5", "Gendarmerie National", false));
        answers.add(new Answer("5", "Google", false));
        answers.add(new Answer("5", "CNIL", true));
        answers.add(new Answer("5", "Le groupe Anonymous", false));
        question = new Question("Quelle organisation g??re la protection des donn??es ?", answers, 2);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("6", "Logiciel pour prot??ger son ordinateur", true));
        answers.add(new Answer("6", "Une alternative au vaccin", false));
        answers.add(new Answer("6", "Logiciel pour pirater", false));
        answers.add(new Answer("6", "Un ordinateur sur prot??g??", false));
        question = new Question("Qu???est ce qu???un Antivirus ?", answers, 1);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("7", "Une coque en silicone", false));
        answers.add(new Answer("7", "Un pare feu", true));
        answers.add(new Answer("7", "Google Drive", false));
        answers.add(new Answer("7", "Le d??veloppeur de l???entreprise", false));
        question = new Question("Qu???est ce qui permet de faire respecter la politique de s??curit?? du r??seau ?", answers, 2);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("8", "Porte ?? pi??ge", false));
        answers.add(new Answer("8", "Cheval de troie", false));
        answers.add(new Answer("8", "Ver", true));
        answers.add(new Answer("8", "Virus", false));
        question = new Question("Lequel est un programme malveillant ind??pendant qui ne n??cessite aucun autre programme ?",answers,4);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("9", "Bombe logique", true));
        answers.add(new Answer("9", "Porte ?? pi??ge", false));
        answers.add(new Answer("9", "Virus", false));
        answers.add(new Answer("9", "Cheval de troie", false));
        question = new Question("Qu'est-ce qu'un code incorpor?? dans un programme pour ??exploser?? si les conditions sont remplies ?",answers,1);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("10", "Virus de macro", false));
        answers.add(new Answer("10", "Virus parasite", false));
        answers.add(new Answer("10", "Virus polymorphe", false));
        answers.add(new Answer("10", "Virus furtif", true));
        question = new Question("Quel forme de virus est con??ue pour ??viter la d??tection par des logiciels antivirus ?",answers,2);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("11", "POP", false));
        answers.add(new Answer("11", "PGP", true));
        answers.add(new Answer("11", "SNMP", false));
        answers.add(new Answer("11", "HTTP", false));
        question = new Question("Quel protocole est utilis?? pour s??curiser les e-mails ?",answers,3);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("12", "54", false));
        answers.add(new Answer("12", "48", false));
        answers.add(new Answer("12", "52", true));
        answers.add(new Answer("12", "50", false));
        question = new Question("Quel est le nombre de sous-cl??s g??n??r??es dans l???algorithme IDEA ?",answers,4);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("13", "RSA", true));
        answers.add(new Answer("13", "DES", false));
        answers.add(new Answer("13", "IREA", false));
        answers.add(new Answer("13", "RC5", false));
        question = new Question("Lequel est un exemple d???algorithme de cl?? publique ?",answers,4);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("14", "D??cryptage", false));
        answers.add(new Answer("14", "Cryptage", true));
        answers.add(new Answer("14", "Transformation", false));
        answers.add(new Answer("14", "Suppression", false));
        question = new Question("Qu???est ce qui transforme le message en format illisible par les pirates ?",answers,3);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("15", "443", true));
        answers.add(new Answer("15", "404", false));
        answers.add(new Answer("15", "43", false));
        answers.add(new Answer("15", "445", false));
        question = new Question("Quel est le num??ro de port pour HTTPS (HTTP Secure) ?",answers,4);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("16", "Selfie", false));
        answers.add(new Answer("16", "D??cryptage", false));
        answers.add(new Answer("16", "Certificat num??rique", true));
        answers.add(new Answer("16", "Formule de politesse ?? la fin", false));
        question = new Question("Quel est la m??thode utilis??e pour valider l???identit?? de l???exp??diteur d???un message ?",answers,2);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("17", "Un pirate informatique", true));
        answers.add(new Answer("17", "Un biscuit aperitif", false));
        answers.add(new Answer("17", "Un virus surpuissant", false));
        answers.add(new Answer("17", "Une cl?? usb pour ??couter des conversations", false));
        question = new Question("Qu???est ce qu???un hacker ?",answers,1);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("18", "Un stylo connect??", false));
        answers.add(new Answer("18", "Un test de d??bit r??seau", true));
        answers.add(new Answer("18", "Evaluation de la s??curit?? d???un syst??me d???information", false));
        answers.add(new Answer("18", "Un virus pour p??n??trer la base de donn??e", false));
        question = new Question("Qu???est ce qu???un pentest ?",answers,3);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("19", "R??glement g??n??ral sur la protection des donn??es", true));
        answers.add(new Answer("19", "R??gle globale pour le piratage des donn??es", false));
        answers.add(new Answer("19", "Un format de fichier crypt??", false));
        answers.add(new Answer("19", "Regular guaranteed data protection", false));
        question = new Question("Qu???est ce que le RGDP ?",answers,3);
        addQuestions(question);

        answers = new ArrayList<Answer>();
        answers.add(new Answer("20", "13%", false));
        answers.add(new Answer("20", "43%", false));
        answers.add(new Answer("20", "80%", false));
        answers.add(new Answer("20", "27%", true));
        question = new Question("Quelle est la perte moyenne de CA suite ?? une attaque ?",answers,4);
        addQuestions(question);

    }


}


