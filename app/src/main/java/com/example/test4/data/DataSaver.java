package com.example.test4.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<BookItem> data){
        try {
            FileOutputStream dataStream = context.openFileOutput("bookData.dat", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            dataStream.write("114514".getBytes());
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public ArrayList<BookItem> Load(Context context){
        ArrayList<BookItem> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("bookData.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<BookItem>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}


