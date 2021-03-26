package com.cleanup.todoc.injections;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.ViewModel;

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
        public <T extends android.arch.lifecycle.ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ViewModel.class)) {
                return (T) new ViewModel(mTaskDataRepository, mProjectDataRepository, mExecutor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
