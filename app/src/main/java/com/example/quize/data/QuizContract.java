package com.example.quize.data;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract(){

    }

    public static final class QuestionsEntry implements BaseColumns {
        public final static String TABLE_NAME = "questions";


        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_QUESTION = "question";
        public final static String COLUMN_TYPE = "type";
        public final static String COLUMN_TOPIC = "topic";
    }
    public static final class TrueAnswersEntry implements BaseColumns {
        public final static String TABLE_NAME = "trueAnswers";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_QUESTION = "answer";
        public final static String Q_ID = "q_id";
    }
    public static final class OtherAnswersEntry implements BaseColumns {
        public final static String TABLE_NAME = "otherAnswers";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_QUESTION = "answer";
        public final static String Q_ID = "q_id";
    }
}
