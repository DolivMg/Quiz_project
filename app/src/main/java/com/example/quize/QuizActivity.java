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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{
    private DataBaseHelper dataBase;
    private ArrayList<ElementQuiz> quizList = new ArrayList<ElementQuiz>();

    private Button buttonNext;

    private final String KEY = "Topic";

    private int chooseTopic;
    private ElementQuiz chooseElement;
    private int chooseType;

    private int trueAnswersQuiz=0;
    private int trueFalseQuiz=0;
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
        quizList=dataBase.getQuizList();

        buttonNext = (Button) findViewById(R.id.buttonNext);

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
                        RadioGroup answerGrup4  = (RadioGroup)  findViewById(R.id.answerCheckBox);
                        answerGrup4.setVisibility(View.VISIBLE);
                        break;
                }

                break;
        }
    }

    private ElementQuiz choosQuestion()
    {
        Random random = new Random();
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
                        // RadioButton r1 = (RadioButton)  findViewById(R.id.answerTrueFalseGroup);
                        setAnswerTrueFalse();
                        break;
                    case 2:
                        setAnswerString();
                        break;
                    case 3:
                        setAnswerRadioGrup(chooseElement.answerList);
                        break;
                    case 4:
                        RadioGroup answerGrup4  = (RadioGroup)  findViewById(R.id.answerCheckBox);
                        answerGrup4.setVisibility(View.VISIBLE);
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

    private void setAnswerRadioGrup(ArrayList<Answers> answers)
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
private void getAnswerTrueFalse()
{
    RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerTrueFalseGroup);
    boolean check = (1 == -1*answerGrup.getCheckedRadioButtonId()+answerGrup.getCheckedRadioButtonId()+2);

    if (chooseElement.answerList.get(0).isValid == check)
        trueAnswersQuiz++;
        else trueFalseQuiz++;

}

    private void getAnswerString()
    {
        EditText answerGrup  = (EditText)  findViewById(R.id.answerString);
        boolean check =  ( answerGrup.getText().equals(chooseElement.answerList.get(0).answer));
        if (chooseElement.answerList.get(0).isValid == check)
            trueAnswersQuiz++;
        else trueFalseQuiz++;
    }
    private void getAnswerRadioGrup()
    {
        RadioGroup answerGrup  = (RadioGroup)  findViewById(R.id.answerRadioGroup);
        boolean check = (1 == -1*answerGrup.getCheckedRadioButtonId()+answerGrup.getCheckedRadioButtonId()+4);

        if (true == check)
            trueAnswersQuiz++;
        else trueFalseQuiz++;
    }


}//SELECT a._id, a.question , a.topic , a.type , b.answer , c.answer FROM question a INNER JOIN otherAnswer b ,trueAnswer c ON a._id = b.q_id AND a._id = c.q_id;



