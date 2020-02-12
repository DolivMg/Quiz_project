package com.example.quize.data;
import java.util.ArrayList;
public class ElementQuiz {

    private int id;
    public int type;
    public int topic;
    public String question;
    public ArrayList<Answers> answerList =new ArrayList<Answers>();
    public ElementQuiz(int id,int type,int topic, String question)
    {
        this.id=id;
        this.question=question;
        this.type=type;
        this.topic=topic;
    }

    public int getId() {
        return id;
    }
}
