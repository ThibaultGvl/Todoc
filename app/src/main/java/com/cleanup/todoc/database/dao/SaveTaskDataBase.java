package com.cleanup.todoc.database.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class SaveTaskDataBase extends RoomDatabase {

        private static volatile SaveTaskDataBase INSTANCE;

        public abstract TaskDao taskDao();

        public static SaveTaskDataBase getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (SaveTaskDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                SaveTaskDataBase.class, "MyDatabase.db")
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }
