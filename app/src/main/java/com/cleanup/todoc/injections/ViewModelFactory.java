package com.cleanup.todoc.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory{

        private final TaskDataRepository mTaskDataRepository;
        private final ProjectDataRepository mProjectDataRepository;
        private final Executor mExecutor;

        public ViewModelFactory(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
            mTaskDataRepository = taskDataRepository;
            mProjectDataRepository = projectDataRepository;
            mExecutor = executor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TaskViewModel.class)) {
                return (T) new TaskViewModel(mTaskDataRepository, projectDataRepository, mExecutor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
