package com.example.words2021116323;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    MyViewModel myViewModel;

    MyAdapter myAdapter;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordFragment newInstance(String param1, String param2) {
        WordFragment fragment = new WordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_word, container, false);

         searchView = view.findViewById(R.id.searchView);
         recyclerView = view.findViewById(R.id.recyclerView);
         floatingActionButton = view.findViewById(R.id.floatingActionButton);

         //1.获取ViewModel实例
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        //2.设置RecyclerView的Adapter和LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new MyAdapter(myViewModel);
        recyclerView.setAdapter(myAdapter);
        //读取Shp
        myViewModel.loadShowMode();
        //数据观测,观察ViewModel中的LiveData并更新UI
        myViewModel.getLiveWords().observe(requireActivity(),words -> {
            myAdapter.setWords(words);
            myAdapter.notifyDataSetChanged();
        });
        myViewModel.getShowMode().observe(requireActivity(),s -> {
            myAdapter.setShowMode(s);
            myAdapter.notifyDataSetChanged();
        });

        //增加按钮的跳转监听
         floatingActionButton.setOnClickListener(v -> {
             NavController navController = Navigation.findNavController(v);
             Bundle bundle = new Bundle();
             bundle.putBoolean("add",true);
             navController.navigate(R.id.action_wordFragment_to_addFragment,bundle);
         });

        searchView.setQuery(myViewModel.getPattern(),false);
        //4.处理SearchView过滤等操作
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myViewModel.getLiveWords().removeObservers(requireActivity());
                String str =newText.trim();
                myViewModel.search(str);// 可以选择在这里也更新列表，但通常只在提交时更新
                myViewModel.getLiveWords().observe(getViewLifecycleOwner(),words -> {
                    myAdapter.setWords(words);
                    myAdapter.notifyDataSetChanged();
                });
                return false;
            }
        });

        //创建optionmenu
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                if(menu.hasVisibleItems()) return;
                menuInflater.inflate(R.menu.option_menu,menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int itemId =menuItem.getItemId();
                if(itemId==R.id.showChinese){
                    myViewModel.setShowMode("showChinese");
                }else if(itemId==R.id.showEnglish){
                    myViewModel.setShowMode("showEnglish");
                }else if(itemId==R.id.showBoth){
                    myViewModel.setShowMode("showBoth");
                }
                return false;
            }
        });

       return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        myViewModel.saveShowMode();
    }
}