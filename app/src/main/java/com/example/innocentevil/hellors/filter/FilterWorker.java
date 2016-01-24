package com.example.innocentevil.hellors.filter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by innocentevil on 16. 1. 24.
 */
public class FilterWorker {

    private ThreadPoolExecutor mExecutor;
    private ArrayBlockingQueue<Runnable> workQ;

    public static FilterWorker FILTER_WORKER = null;

    public static FilterWorker getInstance(){
        if(FILTER_WORKER == null)
            FILTER_WORKER = new FilterWorker();
        return FILTER_WORKER;
    }

    private FilterWorker(){
        workQ = new ArrayBlockingQueue<Runnable>(10);
        mExecutor = new ThreadPoolExecutor(10,10,3000, TimeUnit.MILLISECONDS,workQ);
    }

    public void execute(Runnable job){
        mExecutor.execute(job);
    }


}
