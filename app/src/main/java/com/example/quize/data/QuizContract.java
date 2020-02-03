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
    public static final class TopicTableEntry implements BaseColumns {
        public final static String TABLE_NAME = "topicTable";

        public final static String _ID = BaseColumns._ID;
        public final static String Q_ID = "topic";
    }
    public static final class AnswersEntry implements BaseColumns {
        public final static String TABLE_NAME = "answers";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ANSWER = "answer";
        public final static String COLUMN_IS_VALID = "isValid";
        public final static String Q_ID = "q_id";
    }
}
