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
    private LiveData<List<Task>> Tasks;

    public TaskViewModel(TaskDataRepository mTaskDataRepository, Executor mExecutor) {
        this.mTaskDataRepository = mTaskDataRepository;
        this.mExecutor = mExecutor;
    }

    public void init() {
        if (this.Tasks != null) {
            return;
        }
        Tasks = mTaskDataRepository.getTasks();
    }

    @Nullable
    public LiveData<List<Task>> getTasks() {
        return Tasks;
    }

    public void createTask(final Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.deleteTask(task);
        });
    }
}
