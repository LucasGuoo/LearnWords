package com.example.words2021116323;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


    @Entity(tableName = "words")
    public class Word {
        @PrimaryKey
        @NonNull
        private String english;

        @NonNull
        private String chinese;

        public Word(String english, String chinese) {
            this.english = english;
            this.chinese = chinese;
        }

        public void setEnglish(String english) {
            this.english = english;
        }

        public void setChinese(String chinese) {
            this.chinese = chinese;
        }

        public String getEnglish() {
            return english;
        }

        public String getChinese() {
            return chinese;
        }
    }


