package com.example.words2021116323;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    TextView English,Chinese,addText;
    Button ensure;
    ImageView imageView3;
    private MyViewModel myViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        English = view.findViewById(R.id.English);
        Chinese = view.findViewById(R.id.Chinese);
        addText = view.findViewById(R.id.addView);
        ensure = view.findViewById(R.id.ensure);
        imageView3 = view.findViewById(R.id.imageView3);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        //获取传递进来的参数
        Bundle bundle =getArguments();
        boolean isAdd = bundle.getBoolean("add",true);
        Word word;
        imageView3.setVisibility(View.GONE);
        if (isAdd){
            word=null;
            addText.setText("添加单词");
            //焦点变化时进行事件监听
            English.setOnFocusChangeListener((v,hasFocus) -> {
                if (!hasFocus){
                    imageView3.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(English.getText())){
                        imageView3.setImageResource(R.drawable.baseline_error_24);
                        ensure.setEnabled(false);
                        Toast.makeText(requireActivity(),"输入不能为空!",Toast.LENGTH_SHORT).show();
                    }else {
                        String english=English.getText().toString().trim();
                        Word word1 = myViewModel.get(english);
                        if (word1 != null){
                            imageView3.setImageResource(R.drawable.baseline_error_24);
                            ensure.setEnabled(false);
                            Toast.makeText(requireActivity(),"该单词已存在，不能重复输入！",Toast.LENGTH_SHORT).show();
                        }else {
                            imageView3.setImageResource(R.drawable.baseline_check_24);
                            ensure.setEnabled(true);
                        }
                    }
                }
            });

        }else {
            addText.setText("修改单词");
            String english =bundle.getString("english");
            word = myViewModel.get(english);
            English.setText(english);
            Chinese.setText(word.getChinese());
            English.setEnabled(false);
        }

        //事件监听
        ensure.setOnClickListener(v -> {
            if(TextUtils.isEmpty(English.getText())
                    || TextUtils.isEmpty(English.getText())){
                Toast.makeText(requireActivity(),"输入不能为空！",Toast.LENGTH_SHORT).show();
            }else {
                String english =English.getText().toString().trim();
                String chinese =Chinese.getText().toString().trim();
                if (isAdd){
                    Word word2 = new Word(english,chinese);
                    myViewModel.insert(word2);
                    Toast.makeText(requireActivity(),"添加成功！",Toast.LENGTH_SHORT).show();
                }else {
                    word.setEnglish(english);
                    word.setChinese(chinese);
                    myViewModel.update(word);
                    Toast.makeText(requireActivity(),"修改成功！",Toast.LENGTH_SHORT).show();
                    English.setEnabled(true);
                }
                //页面导航
                NavController navController= Navigation.findNavController(v);
                navController.navigate(R.id.action_addFragment_to_wordFragment);
            }
        });
        return view;
    }



}