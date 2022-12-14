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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test4.data.BookItem;
import com.example.test4.data.DataSaver;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView menu2;

    int icon[] = new int[]{R.drawable.book0,R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book4
            ,R.drawable.book5,R.drawable.book6,R.drawable.book7,R.drawable.book8,R.drawable.book9};

    private ArrayList<BookItem> bookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    private ActivityResultLauncher<Intent> addDateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent = result.getData();
                    if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String author = bundle.getString("author");
                        String shop = bundle.getString("shop");
                        Double price = bundle.getDouble("price");
//                      int position = bundle.getInt("position");
                        Random random = new Random();
                        int i  = random.nextInt(10);
                        bookItems.add(0, new BookItem(title, author, price, shop, icon[i]));
                        new DataSaver().Save(this, bookItems);
                        mainRecycleViewAdapter.notifyItemRemoved(0);
                    }

                    Toast.makeText(this, "input activity return", Toast.LENGTH_SHORT).show();
                }
            });

    private ActivityResultLauncher<Intent> updateDateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent = result.getData();
                    if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String author = bundle.getString("author");
                        String shop = bundle.getString("shop");
                        Double price = bundle.getDouble("price");
                        int position = bundle.getInt("position");
                        bookItems.get(position).setTitle(title);
                        bookItems.get(position).setAuthor(author);
                        bookItems.get(position).setShop(shop);
                        bookItems.get(position).setPrice(price);
                        new DataSaver().Save(this, bookItems);
//                        shopItems.add(position, new BookItem(title, price, R.drawable.ic_drawer));
                        mainRecycleViewAdapter.notifyItemChanged(position);
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

        DataSaver dataSaver = new DataSaver();
        bookItems = dataSaver.Load(this);

//        bookItems = new ArrayList<>();
        if(bookItems.size()==0){
            Random random = new Random();
            int i  = random.nextInt(10);
            bookItems.add(0, new BookItem("firstBook", "author", 10.0, "???????????????????????????", icon[i]));
        }

        mainRecycleViewAdapter = new MainRecycleViewAdapter(bookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

        menu2 = findViewById(R.id.menu2);
        registerForContextMenu(menu2);

        //??????ID???????????????????????????
        ImageButton btnImage = findViewById(R.id.imb_add);

        //?????????????????????????????????
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputBookItemActivity.class);
                intent.putExtra("position",view.getAutofillValue());
                addDateLauncher.launch(intent);
            }
        });

        //?????????????????????????????????
        btnImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openContextMenu(menu2);
                return true;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu2, menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 1:
                Intent intent = new Intent(this, InputBookItemActivity.class);
                intent.putExtra("position",item.getOrder());
                addDateLauncher.launch(intent);
//                startActivity(intent);
                Toast.makeText(this,"item ??????"+item.getOrder()+"?????????!",Toast.LENGTH_LONG).show();
                break;
            case 2:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.string_confirmation)
                        .setMessage("?????????????????????")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookItems.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this, bookItems);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).create();
                alertDialog.show();
                Toast.makeText(this,"item ??????"+item.getOrder()+"?????????!",Toast.LENGTH_LONG).show();
                break;
            case 3:
                Intent intentUpdate = new Intent(this, InputBookItemActivity.class);
                intentUpdate.putExtra("position", item.getOrder());
                intentUpdate.putExtra("title", bookItems.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("author", bookItems.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("shop", bookItems.get(item.getOrder()).getShop());
                intentUpdate.putExtra("price", bookItems.get(item.getOrder()).getPrice());
                updateDateLauncher.launch(intentUpdate);

                Toast.makeText(this,"item ??????"+item.getOrder()+"?????????!",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        private ArrayList<BookItem> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewAuthor;
            private final TextView textViewShop;

            public TextView getTextViewPrice() {
                return textViewPrice;
            }

            private final TextView textViewPrice;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);

                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textViewTitle = (TextView) view.findViewById(R.id.textview_item_caption);
                textViewAuthor = (TextView) view.findViewById(R.id.textview_item_author);
                textViewShop = (TextView) view.findViewById(R.id.textview_item_shop);
                textViewPrice = (TextView) view.findViewById(R.id.textview_item_price);

                view.setOnCreateContextMenuListener(this);

            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public TextView getTextViewAuthor() {
                return textViewAuthor;
            }
            public TextView getTextViewShop() {
                return textViewShop;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,1,getAdapterPosition(),"??????"+getAdapterPosition());
                contextMenu.add(0,2,getAdapterPosition(),"??????"+getAdapterPosition());
                contextMenu.add(0,3,getAdapterPosition(),"??????"+getAdapterPosition());
            }
        }

        public MainRecycleViewAdapter(ArrayList<BookItem> dataSet) {
            localDataSet = dataSet;
        }  //???Java???????????????????????????????????????C++????????????

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
            return new MainRecycleViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainRecycleViewAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(""+localDataSet.get(position).getTitle());
            viewHolder.getTextViewAuthor().setText("?????????"+localDataSet.get(position).getAuthor());
            viewHolder.getTextViewShop().setText("????????????"+localDataSet.get(position).getShop());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
            viewHolder.getTextViewPrice().setText("?????????"+localDataSet.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add :
                Intent intent = new Intent(this, InputBookItemActivity.class);
                intent.putExtra("position",item.getOrder());
                addDateLauncher.launch(intent);
                break;
            case R.id.shelf:
                startActivity(new Intent(MainActivity.this,FindActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
