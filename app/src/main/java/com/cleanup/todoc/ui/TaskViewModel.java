package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository mTaskDataRepository;
    private final Executor mExecutor;

    @Nullable
    private LiveData<List<Task>> currentTask;

    public TaskViewModel(TaskDataRepository mTaskDataRepository, Executor mExecutor) {
        this.mTaskDataRepository = mTaskDataRepository;
        this.mExecutor = mExecutor;
    }

    public void init(long id) {
        if (this.currentTask != null) {
            return;
        }
        currentTask = mTaskDataRepository.getTasks(id);
    }

    @Nullable
    public LiveData<List<Task>> getTasks(long id) {
        return mTaskDataRepository.getTasks(id);
    }

    public void createTask(final Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() ->{
            mTaskDataRepository.createTask(task);
        });
    }

    public void updateTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.updateTask(task);
        });
    }
}
