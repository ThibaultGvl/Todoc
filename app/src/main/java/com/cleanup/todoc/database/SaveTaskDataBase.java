package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveTaskDataBase extends RoomDatabase {

        private static volatile SaveTaskDataBase INSTANCE;

        public abstract TaskDao taskDao();

        public abstract ProjectDao projectDao();

        public static SaveTaskDataBase getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (SaveTaskDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                SaveTaskDataBase.class, "MyDatabase.db")
                                .addCallback(prepopulateDatabase())
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues Tartampion = new ContentValues();
                Tartampion.put("id", 1);
                Tartampion.put("name", "Tartampion");
                Tartampion.put("color", "0xFFEADAD1");

                db.insert("project", OnConflictStrategy.IGNORE, Tartampion);

                ContentValues Lucidia = new ContentValues();
                Lucidia.put("id", 2);
                Lucidia.put("name", "Lucidia");
                Lucidia.put("color", "0xFFB4CDBA");

                db.insert("project", OnConflictStrategy.IGNORE, Lucidia);

                ContentValues Circus = new ContentValues();
                Circus.put("id", 3);
                Circus.put("name", "Circus");
                Circus.put("color", "0xFFA3CED2");

                db.insert("project", OnConflictStrategy.IGNORE, Circus);
            }
        };
    }
    }
