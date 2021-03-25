package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.SaveTaskDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        SaveTaskDataBase mDataBase = SaveTaskDataBase.getInstance(context);
        return new TaskDataRepository(mDataBase.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        SaveTaskDataBase mDataBase = SaveTaskDataBase.getInstance(context);
        return new ProjectDataRepository(mDataBase.projectDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository taskDataRepository = provideTaskDataSource(context);
        ProjectDataRepository projectDataRepository = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(taskDataRepository, projectDataRepository, executor);
    }
}
