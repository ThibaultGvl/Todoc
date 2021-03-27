package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.mProjectDao = projectDao;
    }

    public LiveData<Project[]> getProject() {return this.mProjectDao.getProject();}

    public Project getProjectById(long id) {return this.mProjectDao.getProjectById(id);}

    public long insertProject(Project project) {return this.mProjectDao.insertProject(project);}

    public int deleteProject(Project project) {return this.mProjectDao.deleteProject(project);}
}
