package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.SaveTaskDataBase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private SaveTaskDataBase mDataBase;
    private final Project mProject = new Project(1L, "Tartampion", 0xFFEADAD1);
    private final Task task1 = new Task(mProject.getId(), "task 1", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDataBase() throws Exception {
        this.mDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SaveTaskDataBase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDataBase() throws Exception {
        mDataBase.close();
    }

    @Test
    public void getProject() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject());
        projects.add(mProject);
        assertFalse(projects.isEmpty());
    }

    @Test
    public void insertProject() throws InterruptedException {
        this.mDataBase.projectDao().insertProject(mProject);
        List<Project> projects = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject());
        assertEquals(1, projects.size());
    }

    @Test
    public void getTasks() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertTask() throws InterruptedException {
        this.mDataBase.taskDao().insertTask(task1);
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertEquals(1, tasks.size());
        this.mDataBase.taskDao().deleteTask(task1);
    }

    @Test
    public void DeleteTask() throws InterruptedException {
        this.mDataBase.taskDao().deleteTask(task1);
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
