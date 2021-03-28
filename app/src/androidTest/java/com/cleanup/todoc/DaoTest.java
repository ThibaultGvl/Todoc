package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.DataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    private DataBase mDataBase;
    private final Project mProject = new Project(4L, "aaa", 0x1DADAEFF);
    private final Task task1 = new Task(Objects.requireNonNull(Project.getProjectById(1L)).getId(), "task 1", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDataBase() throws Exception {
        this.mDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), DataBase.class).allowMainThreadQueries().addCallback(DataBase.prepopulateDatabase()).build();
    }

    @After
    public void closeDataBase() throws Exception {
        mDataBase.close();
    }

    @Test
    public void getProject() throws InterruptedException {
        Project[] projects = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject());
        assertEquals(3, projects.length);
    }

    @Test
    public void insertProject() throws InterruptedException {
        this.mDataBase.projectDao().insertProject(mProject);
        Project[] projects = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject());
        assertEquals(4, projects.length);
    }

    @Test
    public void deleteProject() throws InterruptedException {
        this.mDataBase.projectDao().insertProject(mProject);
        this.mDataBase.projectDao().deleteProject(mProject);
        Project[] projects = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject());
        assertEquals(3, projects.length);
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
    }

    @Test
    public void deleteTask() throws InterruptedException {
        this.mDataBase.taskDao().deleteTask(task1);
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
