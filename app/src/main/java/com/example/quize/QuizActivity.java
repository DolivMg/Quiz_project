package com.example.quize;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.quize.data.Answers;
import com.example.quize.data.DataBaseHelper;
import com.example.quize.data.ElementQuiz;
import com.example.quize.data.QuizContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Database;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{
    private DataBaseHelper dataBase;
    private ArrayList<ElementQuiz> quizList; //= new ArrayList<ElementQuiz>();

    private Button buttonNext;

    private final String KEY = "Topic";

    private int chooseTopic;
    private ElementQuiz chooseElement;
    private int chooseType;
    private int max;

    private int trueAnswersQuiz=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        /**load infomation about Topic game */
        Bundle arguments = getIntent().getExtras();
        chooseTopic = arguments.getInt(KEY);//get(KEY);
        /***/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBase = new DataBaseHelper(this,chooseTopic);
        try {
            dataBase.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        dataBase.loadElements();
        quizList=dataBase.getQuizList();

        buttonNext = (Button) findViewById(R.id.buttonNext);

        onDisplayQuestion();
        max=quizList.size();
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNext:
                switch(chooseElement.type)
                {
                    case 1:
                        getAnswerTrueFalse();
                        break;
                    case 2:
                        getAnswerString();
                        break;
                    case 3:
                        getAnswerRadioGrup();
                        break;
                    case 4:
                        getAnswerCheckGrup();
                        break;
                }
        }
        Log.d("vasa", trueAnswersQuiz+"");
        if (checkFinalGame())
        {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("Answers", trueAnswersQuiz);
            intent.putExtra("MaxQuestions", max);
            startActivity(intent);
        }
            else
        onDisplayQuestion();
    }

    private boolean checkFinalGame()
    {
        quizList.remove(chooseElement);
        return (quizList.size()==0);
    }

    private ElementQuiz choosQuestion()
    {
        Random random = new Random();
        Log.d("vasa", quizList.size()+"size");
        int chooseElement = random.nextInt(quizList.size());
        return quizList.get(chooseElement);
    }

    private void onDisplayQuestion()
    {
        chooseElement = choosQuestion();

        TextView textQuestion  = (TextView)  findViewById(R.id.textViewQuestions);
        textQuestion.setText(chooseElement.question);

                switch(chooseElement.type)
                {
                    case 1:
                        setAnswerTrueFalse();
                        break;
                    case 2:
                        setAnswerString();
                        break;
                    case 3:
                        setAnswerRadioGrup();
                        break;
                    case 4:
                        setAnswerCheckGrup();
                        break;
                }
    }

    private void setAnswerTrueFalse()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerTrueFalseGroup);
        answerGrup.setVisibility(View.VISIBLE);
    }

    private void setAnswerString()
    {
        EditText answerGrup  = (EditText)  findViewById(R.id.answerString);
        answerGrup.setVisibility(View.VISIBLE);
    }

    private void setAnswerRadioGrup()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerRadioGroup);
        answerGrup.setVisibility(View.VISIBLE);

        RadioButton r1 = (RadioButton) findViewById(R.id.radioButtonAnsw1);
        RadioButton r2 = (RadioButton) findViewById(R.id.radioButtonAnsw2);
        RadioButton r3 = (RadioButton) findViewById(R.id.radioButtonAnsw3);
        RadioButton r4 = (RadioButton) findViewById(R.id.radioButtonAnsw4);

        r1.setText(chooseElement.answerList.get(0).answer);
        r2.setText(chooseElement.answerList.get(1).answer);
        r3.setText(chooseElement.answerList.get(2).answer);
        r4.setText(chooseElement.answerList.get(3).answer);
    }

    private void setAnswerCheckGrup()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerCheckBox);
        answerGrup.setVisibility(View.VISIBLE);

        CheckBox r1 = (CheckBox) findViewById(R.id.checkButton1);
        CheckBox r2 = (CheckBox) findViewById(R.id.checkButton2);
        CheckBox r3 = (CheckBox) findViewById(R.id.checkButton3);
        CheckBox r4 = (CheckBox) findViewById(R.id.checkButton4);

        r1.setText(chooseElement.answerList.get(0).answer);
        r2.setText(chooseElement.answerList.get(1).answer);
        r3.setText(chooseElement.answerList.get(2).answer);
        r4.setText(chooseElement.answerList.get(3).answer);
    }



private void getAnswerTrueFalse()
{
    RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerTrueFalseGroup);

    boolean check =false;
    switch(answerGrup.getCheckedRadioButtonId())
    {
        case  R.id.radioButtonFalse:
            check =false;
            break;
        case  R.id.radioButtonTrue:
            check =true;
            break;
    }

    if (chooseElement.answerList.get(0).isValid == check)
        trueAnswersQuiz++;
    answerGrup.setVisibility(View.GONE);
}

    private void getAnswerString()
    {
        EditText answerGrup  = (EditText)  findViewById(R.id.answerString);
        boolean check =  ( answerGrup.getText().equals(chooseElement.answerList.get(0).answer));
        if (chooseElement.answerList.get(0).isValid == check)
            trueAnswersQuiz++;
        answerGrup.setVisibility(View.GONE);
    }

    private void getAnswerRadioGrup()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerRadioGroup);
        int check =0;

        RadioButton r1 = (RadioButton) findViewById(R.id.radioButtonAnsw1);
        RadioButton r2 = (RadioButton) findViewById(R.id.radioButtonAnsw2);
        RadioButton r3 = (RadioButton) findViewById(R.id.radioButtonAnsw3);
        RadioButton r4 = (RadioButton) findViewById(R.id.radioButtonAnsw4);

        switch(answerGrup.getCheckedRadioButtonId())
        {
            case  R.id.radioButtonAnsw1:
                check =0;
                break;
            case  R.id.radioButtonAnsw2:
                check =1;
                break;
            case  R.id.radioButtonAnsw3:
                check =2;
                break;
            case  R.id.radioButtonAnsw4:
                check =3;
                break;
        }
        answerGrup.setVisibility(View.GONE);
        if (chooseElement.answerList.get(check).isValid == true)
            trueAnswersQuiz++;
    }

    private void getAnswerCheckGrup()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerCheckBox);
        int check =0;

        CheckBox r1 = (CheckBox) findViewById(R.id.checkButton1);
        CheckBox r2 = (CheckBox) findViewById(R.id.checkButton2);
        CheckBox r3 = (CheckBox) findViewById(R.id.checkButton3);
        CheckBox r4 = (CheckBox) findViewById(R.id.checkButton4);

        answerGrup.setVisibility(View.GONE);
        if (chooseElement.answerList.get(0).isValid == r1.callOnClick()==true)
            trueAnswersQuiz++;
        if (chooseElement.answerList.get(1).isValid == r2.callOnClick()==true)
            trueAnswersQuiz++;
        if (chooseElement.answerList.get(2).isValid == r3.callOnClick()==true)
            trueAnswersQuiz++;
        if (chooseElement.answerList.get(3).isValid == r4.callOnClick()==true)
            trueAnswersQuiz++;
    }
}//SELECT a._id, a.question , a.topic , a.type , b.answer , c.answer FROM question a INNER JOIN otherAnswer b ,trueAnswer c ON a._id = b.q_id AND a._id = c.q_id;



