package com.example.quize.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "quiz";
    private SQLiteDatabase myDataBase;
    private final Context mContext;
    private ArrayList<ElementQuiz> quizList = new ArrayList<ElementQuiz>();
    private  int chooseTopic;

    public DataBaseHelper(Context context,int chooseTopic) {
        super(context, DB_NAME, null, 1);
        DB_PATH =context.getDatabasePath(DB_NAME).getPath();
        Log.d("vasa", context.getDatabasePath(DB_NAME).exists()+"  tut");
        this.chooseTopic=chooseTopic;
        /*if (android.os.Build.VERSION.SDK_INT >= 19)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";*/
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){
            //ничего не делать - база уже есть
        }else{
            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
//------------------------------------------
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    //---------------------------------------
    private void copyDataBase() throws IOException{
        //Открываем локальную БД как входящий поток
        InputStream myInput = mContext.getAssets().open(DB_NAME);

        //Путь ко вновь созданной БД
        String outFileName = DB_PATH + DB_NAME;

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public SQLiteDatabase openDataBase() throws SQLException {
        //открываем БД
        String myPath = DB_PATH;//+DB_NAME; //context.getDatabasePath(DB_NAME).getPath() ;
        Log.d("vasa", myPath);

        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    private void loadElements() {
        /**My db*/
        SQLiteDatabase db = null;
        /**Element db*/
        ElementQuiz elem;
        /**Open db*/
        try {
            db = openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        /**SELECT a._id, a.question , a.topic , a.type  FROM question a ; */
        String query = "SELECT "
                + QuizContract.QuestionsEntry._ID +
                ","
                + QuizContract.QuestionsEntry.COLUMN_QUESTION +
                ", "
                + QuizContract.QuestionsEntry.COLUMN_TOPIC +
                ", "
                + QuizContract.QuestionsEntry.COLUMN_TYPE +
                " FROM "
                + QuizContract.QuestionsEntry.TABLE_NAME +
                " WHERE "
                +QuizContract.QuestionsEntry.COLUMN_TOPIC+
                " = "+
                (chooseTopic) +
                " ;";

        if( db.isOpen())
            Log.d("vasa","open" );else Log.d("vasa","close" );
        Log.d("vasa",db.getMaximumSize()+"" );

        /**it begin to read*/
        Cursor cursor = db.rawQuery(query , null);
        try {
            /**read*/
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor
                        .getColumnIndex(QuizContract.QuestionsEntry._ID));
                String question = cursor.getString(cursor
                        .getColumnIndex(QuizContract.QuestionsEntry.COLUMN_QUESTION));
                int type = cursor.getInt(cursor
                        .getColumnIndex(QuizContract.QuestionsEntry.COLUMN_TYPE));
                int topic = cursor.getInt(cursor
                        .getColumnIndex(QuizContract.QuestionsEntry.COLUMN_TOPIC));
                /**Add element to quiz*/
                elem = new ElementQuiz(id, type, topic, question);
                quizList.add(elem);
            }
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        //----------------------------
        /***/
        for(int i=0;i<quizList.size();i++ ) {

            query= "SELECT a."
                    + QuizContract.AnswersEntry.COLUMN_ANSWER
                    +" , "
                    + QuizContract.AnswersEntry.COLUMN_IS_VALID
                    + " FROM "
                    + QuizContract.AnswersEntry.TABLE_NAME
                    + " a INNER JOIN "
                    + QuizContract.QuestionsEntry.TABLE_NAME
                    + "b ON a."+ QuizContract.AnswersEntry.Q_ID
                    + " = "
                    + "b."+QuizContract.QuestionsEntry._ID+" ;";

            cursor = db.rawQuery(query, null);
            try {
                /**read*/
                while (cursor.moveToNext()) {
                    Boolean isVald = cursor.getInt(cursor
                            .getColumnIndex(QuizContract.AnswersEntry.COLUMN_IS_VALID)) == 1 ? true : false;

                    String answer =cursor.getString(cursor
                            .getColumnIndex(QuizContract.AnswersEntry.COLUMN_IS_VALID));

                    quizList.get(i).answerList.add( new Answers(isVald, answer ));
                }
            } finally {
                // Всегда закрываем курсор после чтения
                /**Close cursor*/
                cursor.close();
            }
        }
    }
    public ArrayList<ElementQuiz> getQuizList()
    {
        return quizList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        loadElements();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
