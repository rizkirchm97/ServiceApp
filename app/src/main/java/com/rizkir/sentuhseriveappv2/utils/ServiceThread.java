package com.rizkir.sentuhseriveappv2.utils;

/**
 * created by RIZKI RACHMANUDIN on 25/10/2022
 */
public class ServiceThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
