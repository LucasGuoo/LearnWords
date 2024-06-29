package com.example.words2021116323;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.InvalidationTracker;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    WordDao wordDao;
    private LiveData<List<Word>> liveWords;
    private MutableLiveData<String> showMode ;
    //初始化，否则loadShowMode会出错，无法创建ViewModel了
    private SharedPreferences shp;
    private String pattern;

    MutableLiveData<Boolean> dataChanged = new MutableLiveData<>();
    public Word get(String english) {
        return wordDao.get(english);

    }
    //用来监听添加修改操作，通知数据变化

    public MyViewModel(@NonNull Application application) {
        super(application);

        WordDatabase wordDatabase=WordDatabase.getWordDatabase(getApplication());
        wordDao = wordDatabase.getWordDao();
        shp =getApplication().getSharedPreferences("myShp", Context.MODE_PRIVATE);
        getAllWords();
        if (showMode==null){
            showMode=new MutableLiveData<>();
        }

    }
    private void updateLiveWords() {
        liveWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getLiveWords() {
        return liveWords;
    }

    public MutableLiveData<String> getShowMode() {
        return showMode;
    }

    public void setShowMode(String mode) {
        showMode.setValue(mode);
    }
    public String getPattern(){
        return pattern;
    }

    //public LiveData<List<Word>>

    public void insert(Word... words) {
        for(Word word : words) {
            if (wordDao.contains(word.getEnglish()) == null) {
                wordDao.insert(word);
            }
        }
    }

    public void delete(Word... words) {
        wordDao.delete(words);
    }
    public void update(Word... words) {
        wordDao.updata(words);
    }

    public void getAllWords() {
        search("");
    } //这个方法调用search方法并传入空字符串作为参数，以获取数据库中所有的Word对象。

    public void search(String pattern) {
        liveWords = wordDao.getFilteredWords(pattern);
    }


    public void loadShowMode(){
        String str = shp.getString("showMode","showBoth");
        //从 SharedPreferences 中获取名为 "showMode" 的字符串值，如果找不到这个值，则使用默认值 "showBoth"
        showMode.setValue(str);
    }

    public void saveShowMode(){
        SharedPreferences.Editor editor = shp.edit();
        editor.putString("showMode",showMode.getValue());
        editor.apply(); // 提交更改
    }

}
