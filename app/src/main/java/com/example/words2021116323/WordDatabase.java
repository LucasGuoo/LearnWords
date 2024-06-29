package com.example.words2021116323;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class},version = 1, exportSchema = false) //entities表示这个数据库包含的实体。
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE; //懒汉单例模式

    public synchronized static WordDatabase getWordDatabase(Context context) {
        if(INSTANCE == null) { //创建WordDatab实例
            INSTANCE = Room.databaseBuilder(context,WordDatabase.class,"wordDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public  abstract WordDao getWordDao(); //获取实例
}