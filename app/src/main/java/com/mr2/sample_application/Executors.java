package com.mr2.sample_application;

import java.util.concurrent.Callable;

public class Executors {

    public static void ioThread(Runnable runnable){
        java.util.concurrent.Executors.newSingleThreadExecutor().execute(runnable);
    }

//    public static void uiThread(Runnable runnable){
//    }
    public interface Result{
        void onFinished();
    }

    public static void ioThreadForResult(Runnable runnable, Result result){
        Callable<Result> c = new Callable<Result>() {
            int res;
            @Override
            public Result call() throws Exception {

                return null;
            }
        };
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    c.call();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
    }
}
