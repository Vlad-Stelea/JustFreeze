package com.company;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by vlads on 12/2/2017.
 */
public class Server implements Runnable{
    ServerSocket serverSocket;
    private int port = 50004;
    private final ArrayList<ObjectOutputStream> socketOutputStreams;

    public Server (){
        System.out.println("Starting server");
        socketOutputStreams = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server done initializing");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("server failed initializing");
        }
        if(serverSocket.isBound()) {
            new Thread(this).start();
        }
    }
    //Keeps track of adding people to the server
    @Override
    public void run() {
        while (!serverSocket.isClosed()){
            try {
                ObjectOutputStream out = new ObjectOutputStream(serverSocket.accept().getOutputStream());
                out.flush();
                socketOutputStreams.add(out);
            } catch (IOException e) {
                e.printStackTrace();//TODO handle better
            }
        }
    }

    /**
     * sends a mat to the devices
     * @param m the mat to be sent
     */
    public void sendMat(SerializableMat m){
        for(ObjectOutputStream object: socketOutputStreams){
            try {
                object.writeObject(m);
                object.reset();
                object.flush();
            } catch (IOException e) {
                e.printStackTrace();//TODO handle
            }
        }
    }
}
