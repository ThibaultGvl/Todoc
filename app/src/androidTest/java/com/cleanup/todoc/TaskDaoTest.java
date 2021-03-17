package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.dao.SaveTaskDataBase;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private SaveTaskDataBase mDataBase;
    private static final Task Repassage = new Task(1, 1, "Repassage", new Date().getTime());
    private static final Task Nettoyage = new Task(2, 2, "Nettoyage", new Date().getTime());

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
    public void getTasks() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertTask() throws InterruptedException {
        this.mDataBase.taskDao().insertTask(Repassage);
        this.mDataBase.taskDao().insertTask(Nettoyage);

        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertEquals(2, tasks.size());
    }

    @Test
    public void DeleteTask() throws InterruptedException {
        this.mDataBase.taskDao().insertTask(Repassage);
        this.mDataBase.taskDao().deleteTask(Repassage);

        List<Task> items = LiveDataTestUtil.getValue(this.mDataBase.taskDao().getTasks());
        assertTrue(items.isEmpty());
    }
}
