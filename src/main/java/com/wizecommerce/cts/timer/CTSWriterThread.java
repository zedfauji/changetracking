package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.zeus.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 17/01/14
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class CTSWriterThread extends Thread {
    public void run(){
        System.out.println("Name:" + Thread.currentThread().getName());
        System.out.println("Daemon:" + Thread.currentThread().isDaemon());
        Writer w1 = new Writer();

    }
}
