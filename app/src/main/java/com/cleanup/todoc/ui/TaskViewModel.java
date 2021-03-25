package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository mTaskDataRepository;
    private final ProjectDataRepository mProjectDataRepository;
    private final Executor mExecutor;

    @Nullable
    private LiveData<List<Task>> Tasks;

    private LiveData<List<Project>> Projects;

    public TaskViewModel(TaskDataRepository mTaskDataRepository, ProjectDataRepository mProjectDataRepository, Executor mExecutor) {
        this.mTaskDataRepository = mTaskDataRepository;
        this.mProjectDataRepository = mProjectDataRepository;
        this.mExecutor = mExecutor;
    }

    public void init() {
        if (this.Tasks != null) {
            return;
        }
        Tasks = mTaskDataRepository.getTasks();
    }

    public LiveData<List<Project>> getProject() {return Projects;}

    public void createProject(final Project project) {
        mExecutor.execute(() -> {
            mProjectDataRepository.insertProject(project);
        });
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
