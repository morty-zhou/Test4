package com.example.test4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test4.data.ShopItem;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ShopItem> shopItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    private ActivityResultLauncher<Intent> addDateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
        if(null!=result){
            Intent intent = result.getData();
            if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS){
                Bundle bundle = intent.getExtras();
                String title = bundle.getString("title");
                Double price = bundle.getDouble("price");
                shopItems.add(0, new ShopItem(title, price, R.drawable.ic_drawer));
                mainRecycleViewAdapter.notifyItemRemoved(0);
            }

            Toast.makeText(this, "input activity return", Toast.LENGTH_SHORT).show();
        }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain = findViewById(R.id.recycle_view_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);


        shopItems = new ArrayList<>();
        for(int i = 0; i<10;i++){
            shopItems.add(new ShopItem("item"+i, Math.random()*10, i%2==1?R.drawable.ic_drawer:R.drawable.net));
        }

        mainRecycleViewAdapter = new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "请点击正确位置", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


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
                Intent intent = new Intent(this, InputBookItemActivity.class);
                addDateLauncher.launch(intent);
//                startActivity(intent);
                Toast.makeText(this,"item 添加"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
            case 2:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.string_confirmation)
                        .setMessage("是否确认删除？")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shopItems.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).create();
                alertDialog.show();
                Toast.makeText(this,"item 删除"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this,"item 更改"+item.getOrder()+"已点击!",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }



    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        private ArrayList<ShopItem> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;

            public TextView getTextViewPrice() {
                return textViewPrice;
            }

            private final TextView textViewPrice;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);

                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textViewTitle = (TextView) view.findViewById(R.id.textview_item_caption);
                textViewPrice = (TextView) view.findViewById(R.id.textview_item_price);

                view.setOnCreateContextMenuListener(this);

            }

            public TextView getTextViewTitle() {
                return textViewTitle;
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

        public MainRecycleViewAdapter(ArrayList<ShopItem> dataSet) {
            localDataSet = dataSet;
        }  //在Java中两个对象之间的赋值类似于C++中的引用

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
            return new MainRecycleViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainRecycleViewAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText("书名："+localDataSet.get(position).getTitle());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
            viewHolder.getTextViewPrice().setText("价格："+localDataSet.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }


}

