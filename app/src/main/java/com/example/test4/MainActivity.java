package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<String> mainStringSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain = findViewById(R.id.recycle_view_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);


        mainStringSet = new ArrayList<>();
        for(int i = 1; i<100;i++){
            mainStringSet.add("item" + i);
        }

        MainRecycleViewAdapter mainRecycleViewAdapter = new MainRecycleViewAdapter(mainStringSet);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 1:
                Toast.makeText(this,"item 添加"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this,"item 删除"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this,"item 更改"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        private ArrayList<String> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);

                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textView = (TextView) view.findViewById(R.id.textview_item_caption);

                view.setOnCreateContextMenuListener(this);

            }

            public TextView getTextView() {
                return textView;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,1,getAdapterPosition(),"添加"+getAdapterPosition());
                contextMenu.add(0,2,getAdapterPosition(),"删除"+getAdapterPosition());
                contextMenu.add(0,3,getAdapterPosition(),"更改"+getAdapterPosition());
            }
        }

        public MainRecycleViewAdapter(ArrayList<String> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
            return new MainRecycleViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainRecycleViewAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextView().setText(localDataSet.get(position));
            viewHolder.getImageViewImage().setImageResource(position%2==1?R.drawable.ic_drawer:R.drawable.net);
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }


}

