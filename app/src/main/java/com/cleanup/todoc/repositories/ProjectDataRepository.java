package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.mProjectDao = projectDao;
    }

    public LiveData<Project[]> getProject() {return this.mProjectDao.getProject();}

    public long insertProject(Project project) {return this.mProjectDao.insertProject(project);}

    public int deleteProject(Project project) {return this.mProjectDao.deleteProject(project);}
}
