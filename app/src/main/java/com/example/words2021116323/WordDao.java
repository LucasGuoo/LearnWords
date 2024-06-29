package com.example.words2021116323;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface WordDao {
    @Insert
    void insert(Word... words);
    @Delete
    void delete(Word... words); //获取对应数据之后再用该方法删除
    @Update
    void updata(Word... words);

    @Query("select * from words where english = :pattern")
    Word contain(String pattern);

    @Query("select * from words where english = :english")
    Word contains(String english);
    @Query("select * from words")
    LiveData<List<Word>> getAllWords();

    @Query("select * from words where english like '%' ||  :pattern || '%'")
    LiveData<List<Word>> getFilteredWords(String pattern);

    @Query("SELECT * FROM words WHERE english = :english")
    Word get(String english);
}
