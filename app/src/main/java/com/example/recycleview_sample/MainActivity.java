package com.example.recycleview_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    //item元件
    private TextView txt_id,txt_title,txt;
    //Recycleview點擊用
    private View clickview ;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        try {

            //設置RecycleView
            mRecyclerView = findViewById(R.id.recycleview);
            mRecyclerView.setItemViewCacheSize(500);  //設定緩存的item數
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            myListAdapter = new MyListAdapter();
            mRecyclerView.setAdapter(myListAdapter);

            swipeRefreshLayout = findViewById(R.id.swip);
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.swipe_color_1, R.color.swipe_color_2,
                    R.color.swipe_color_3, R.color.swipe_color_4);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.black)); //設定圈圈的顏色
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    arrayList.clear();                       //清除原本的數據
                    getData();                               //更新數據
                    mRecyclerView.setAdapter(myListAdapter);
                    myListAdapter.notifyDataSetChanged();    //通知adapter數據變更
                    swipeRefreshLayout.setRefreshing(false); //取消圈圈
                    Log.v("***","*** 入: " );
                }
            });
        }catch (Exception e){
            Log.v("***","*** error: " + e);
        }

    }

    private void getData() {
        for (int i = 0;i<30;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("Id",String.valueOf(i));
            hashMap.put("Sub1",String.valueOf(new Random().nextInt(80) + 20));
            hashMap.put("Sub2",String.valueOf(new Random().nextInt(80) + 20));
            arrayList.add(hashMap);
        }
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_id = itemView.findViewById(R.id.recycle_id);
                txt_title = itemView.findViewById(R.id.rrecycle_title);
                txt = itemView.findViewById(R.id.rrecycle_txt);
                clickview = itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.v("***","*** 入2: " );
            txt_id.setText(arrayList.get(position).get("Id"));
            txt_title.setText(arrayList.get(position).get("Sub1"));
            txt.setText(arrayList.get(position).get("Sub2"));


            clickview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("***","*** click: " + position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}