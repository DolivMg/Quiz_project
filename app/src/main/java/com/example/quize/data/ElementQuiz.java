package com.example.quize.data;
import java.util.ArrayList;
public class ElementQuiz {

    private int type;
    private int topic;
    private String question;
    public ArrayList<String> trueAnswer =new ArrayList<String>();
    public ArrayList<String> otherAnswer =new ArrayList<String>();
    public ElementQuiz(int type,int topic, String question)
    {
        this.question=question;
        this.type=type;
        this.topic=topic;
    }
    /*
    public void setTrueAnswer(String answer)
    {
        trueAnswer.add(answer);
    }

    public void setOtherAnswer(String answer)
    {
        otherAnswer.add(answer);
    }
    */


}
