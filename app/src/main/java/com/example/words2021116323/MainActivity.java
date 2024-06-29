package com.example.words2021116323;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

    }
    @Override
    public boolean onSupportNavigateUp() {
        int id=navController.getCurrentDestination().getId();
        if (id==R.id.homeFragment){
            //确认是否退出
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("确认退出吗？")
                    .setPositiveButton("确认",(dialog, which) -> finish())
                    .setNegativeButton("取消",(dialog, which) -> dialog.dismiss()).create();
            alertDialog.show();
        }else if (id==R.id.wordFragment){
            //跳转到homefragment
            navController.navigate(R.id.homeFragment);
        }else if (id==R.id.addFragment){
            //跳转到wordfragment
            navController.navigate(R.id.wordFragment);
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

}