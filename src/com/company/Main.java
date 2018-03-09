package com.company;

import org.opencv.core.Core;


public class Main {
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) {
	// write your code here
        Thread imageWatcherThread = new Thread(new ImageWatcher(0));
        imageWatcherThread.start();
    }
}
