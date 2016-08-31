package com.udacity.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Amardeep Kumar on 8/27/2016.
 */
public class AsyncTaskTest extends ApplicationTestCase<Application> implements JokeFetchListener {

    private String mJsonString;
    private Exception mError;
    private CountDownLatch signal;

    public AsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJokeTask() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask(this);
        task.execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJsonString));
    }

    @Override
    public void onJokeFetched(String joke) {
        mJsonString = joke;
        signal.countDown();
    }
}