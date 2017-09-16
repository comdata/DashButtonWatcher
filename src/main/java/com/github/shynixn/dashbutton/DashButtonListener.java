package com.github.shynixn.dashbutton;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2017 Shynixn
 * <p>
 * Do not remove this header!
 * <p>
 * Version 1.0
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2017
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class DashButtonListener implements Runnable, AutoCloseable {

    private final Set<Runnable> listeners = new HashSet<>();
    private volatile boolean isRunning;
    private String ip;

    /**
     * Initializes a new listener
     */
    private DashButtonListener() {
    }

    /**
     * Registers a listener which gets called when the button isPressed
     *
     * @param runnable runnable
     */
    public void register(Runnable runnable) {
        synchronized (this.listeners) {
            if (runnable == null)
                throw new IllegalArgumentException("Runnable cannot be null!");
            this.listeners.add(runnable);
        }
    }

    /**
     * Returns the ip address
     *
     * @return ip
     */
    public String getIpAddress() {
        return this.ip;
    }

    /**
     * Unregisters a listener which was registered before
     *
     * @param runnable runnable
     */
    public void unregister(Runnable runnable) {
        synchronized (this.listeners) {
            if (runnable != null && this.listeners.contains(runnable)) {
                this.listeners.remove(runnable);
            }
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (this.isRunning) {
            try {
                final InetAddress address = InetAddress.getByName(this.ip);
                if (address.isReachable(5000)) {
                    synchronized (this.listeners) {
                        for (final Runnable runnable : this.listeners) {
                            runnable.run();
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Registers a new dashButtonListener from Ip Address
     *
     * @param ip ip
     * @return listener
     */
    public static DashButtonListener fromIpAddress(String ip) {
        final DashButtonListener dashButtonListener = new DashButtonListener();
        dashButtonListener.ip = ip;
        dashButtonListener.isRunning = true;
        final Thread thread = new Thread(dashButtonListener);
        thread.setDaemon(true);
        thread.start();
        return dashButtonListener;
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        this.isRunning = false;
    }
}
