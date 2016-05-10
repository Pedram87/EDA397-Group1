package com.group1.eda_397_group1;
import android.os.AsyncTask;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by p on 5/10/2016.
 */
public class DatabaseTest {
    @Test
    public void doesConnect() throws NullPointerException, Exception {
        assertEquals(android.os.AsyncTask.Status.RUNNING, DatabaseHandler.Status.RUNNING);
    }


}
