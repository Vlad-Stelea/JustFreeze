package com.company;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

/**
 * Created by vlads on 12/2/2017.
 * Used to watch whether there is movement on the screen
 */
public class ImageWatcher implements Runnable{
    private final VideoCapture videoCapture;
    private final BackgroundSubtractor backgroundSubtractor;
    private final double backGroundRatioThreshold = .99;//TODO calibrate sensitivity
    private final int threshHoldColor = 120;
    private final Server server;


    /**
     *
     * @param camera the index of the camera to open
     */
    public ImageWatcher(int camera){
        videoCapture = new VideoCapture();
        videoCapture.open(camera);
        backgroundSubtractor = Video.createBackgroundSubtractorMOG2();
        server = new Server();

    }

    @Override
    public void run() {
        Imshow imshow = new Imshow("Mask");
        Mat read = new Mat();
        Mat mask = new Mat();
        while(videoCapture.isOpened()){
            //reads a mat from the videocapture
            videoCapture.read(read);
            Core.flip(read,read,1);
            backgroundSubtractor.apply(read,mask);
                if (getBackgroundRatio(mask) < backGroundRatioThreshold) {
                    server.sendMat(new SerializableMat(read));
                }
        }
    }

    private double getBackgroundRatio(Mat mat){
        int numBlacks = 0;//Keeps track of how many black pixels there are
        for(int i = 0; i < mat.width(); i++){
            for(int j = 0; j < mat.height(); j++){
                double [] pixelValue = mat.get(j,i);
                if(pixelValue[0] < threshHoldColor){
                    numBlacks++;
                }
            }
        }
        return (double)numBlacks/(mat.width()*mat.height());
    }

}
