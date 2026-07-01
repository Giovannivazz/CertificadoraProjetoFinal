package com.example.gerenciadorprojetos;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// bd
@Database(entities = {Canal.class, Video.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract AppDao appDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {



            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "gerenciador_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}