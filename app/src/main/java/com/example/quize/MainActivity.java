package com.example.quize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



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
                /**choose topic*/
                switch(radioGroupChooseTopic.getCheckedRadioButtonId())
                {
                    case  R.id.radButTopic1:
                        topic=1;
                        break;
                    case  R.id.radButTopic2:
                        topic=2;
                        break;
                    case  R.id.radButTopic3:
                        topic=3;
                        break;
                    case  R.id.radButTopic4:
                        topic=4;
                        break;

                }


                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra(KEY, topic);
                startActivity(intent);

                break;
            case R.id.buttonGoToStatistic:
                break;
        }
    }
}
