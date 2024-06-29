package com.example.words2021116323;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    MyViewModel myViewModel;
    List<Word> words;
    String showMode ;


    //设置myViewmodel
    public MyAdapter (MyViewModel myViewModel) {

        // 注意：通常你会注入一个具体的ViewModel实例，而不是AndroidViewModel基类
        this.myViewModel = myViewModel; // 这里可能需要转型为具体的ViewModel类型
        if(words==null){
            words=new ArrayList<>();
        }
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
    public void setShowMode(String showMode) {
        this.showMode = showMode;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载你的布局并创建ViewHolder实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = words.get(position);
         // 根据显示模式设置控件是否可见
        if (showMode.equals("showEnglish")) {
            holder.textViewEnglish.setVisibility(View.VISIBLE);
            holder.textViewChinese.setVisibility(View.GONE);//控件是不可见的，并且它不占据布局空间。
        } else if (showMode.equals("showChinese")) {
            holder.textViewEnglish.setVisibility(View.GONE);
            holder.textViewChinese.setVisibility(View.VISIBLE);
        } else {
            holder.textViewEnglish.setVisibility(View.VISIBLE);
            holder.textViewChinese.setVisibility(View.VISIBLE);
        }


        holder.textViewNo.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getEnglish());
        holder.textViewChinese.setText(word.getChinese());

        Context context = holder.itemView.getContext();
        // 长按事件处理（如果需要，可以在ViewHolder中设置）
        holder.itemView.setOnLongClickListener(v -> {
            // 显示PopupMenu
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu()); // 加载菜单布局

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.modify) {
                    // 获取NavController并跳转页面（传递参数）
                    NavController navController = Navigation.findNavController(holder.itemView);
                    // 假设你有一个Word对象，你可以把它作为参数传递
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("add",false);
                    bundle.putString("english",word.getEnglish());
                    navController.navigate(R.id.addFragment, bundle); //导航到编辑单词的Fragment
                    return true;
                } else if (id == R.id.delete) {
                    // 弹出对话框确认删除
                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("确认删除")
                            .setMessage("您确定要删除这个单词吗？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                // 在这里实现删除逻辑
                                // 例如：从数据源中移除单词，并通知适配器更新数据
                                myViewModel.delete(word);
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    return true;
                }
                return false;
            });
            popupMenu.setGravity(Gravity.END);
            popupMenu.show();
            return true; // 长按事件已处理
        });

        // 图片点击事件处理
        holder.check.setOnClickListener(v -> {
            // 打开指定网页
            // 构造有道翻译搜索页面的URL，并带上查询参数
            // 注意：以下URL是基于有道翻译网页版的一个假设URL，实际URL可能不同，请根据实际情况调整
            String url = null;
            try {
                url = "http://dict.youdao.com/w/" + URLEncoder.encode(holder.textViewEnglish.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            // 创建Intent打开浏览器并加载URL
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(holder.itemView.getContext().getPackageManager()) != null) {
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }




    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNo,textViewEnglish,textViewChinese;
        ImageView check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNo = itemView.findViewById(R.id.textViewNo);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            check = itemView.findViewById(R.id.check);


        }
    }

    }