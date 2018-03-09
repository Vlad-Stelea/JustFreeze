package com.company;


import org.opencv.core.Mat;

import java.io.Serializable;

/**
 * Created by vlads on 12/2/2017.
 */
public class SerializableMat implements Serializable {

    int rows;
    int cols;
    int type;
    byte[] data;

    public SerializableMat(){

    }
    public SerializableMat(Mat mat){
        if (mat.isContinuous()) {

            int elemSize = (int) mat.elemSize();
            rows = mat.rows();
            cols = mat.cols();
            type = mat.type();

            data = new byte[cols * rows * elemSize];
            mat.get(0, 0, data);
        }
    }
    public Mat toMat()
    {
        Mat mat = new Mat(rows, cols, type);
        mat.put(0, 0, data);
        return mat;
    }
}
