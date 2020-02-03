package com.example.quize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //private final int ID = 2131230889;
    private final String KEY = "Topic";
    private int topic;
    private Button buttonGoToQuiz;
    private Button buttonGoToStatistic;
    private RadioGroup radioGroupChooseTopic ;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonGoToQuiz = (Button) findViewById(R.id.buttonGoToGame);
        buttonGoToStatistic = (Button) findViewById(R.id.buttonGoToStatistic);
        radioGroupChooseTopic = (RadioGroup) findViewById(R.id.chooseTopicGroup);

        /***/
        buttonGoToQuiz.setOnClickListener(this);
        buttonGoToStatistic.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGoToGame:
                topic = radioGroupChooseTopic.getCheckedRadioButtonId();

                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra(KEY, topic);
                startActivity(intent);

                break;
            case R.id.buttonGoToStatistic:
                break;
        }
    }
}
