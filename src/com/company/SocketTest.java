package com.company;

import org.opencv.core.Core;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by vlads on 12/2/2017.
 */
public class SocketTest {
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String args[]){
        int port= 5004;
        String host = "loclhost";

        if(args.length>0){
            System.out.println(args.length);
            System.out.println(args[1].substring(0,3));
            for(int i =0; i<args.length; i++){
                String arg = args[i];
                if(arg.substring(0,3).equalsIgnoreCase("-IP")){
                    host = arg.substring(3);
                }else if(arg.substring(0,5).equalsIgnoreCase("-port")){
                    port = Integer.parseInt(arg.substring(5));
                }
            }
        }

        Socket s = null;
        System.out.println("starting client");
        try {
            s = new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("starting input stream");
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done with inputstream");
        Imshow imshow = new Imshow("RECIEVER");
        System.out.println("done creating gui");
        while(true){
            try {
                Toolkit.getDefaultToolkit().beep();
                imshow.showImage(((SerializableMat)in.readObject()).toMat());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
