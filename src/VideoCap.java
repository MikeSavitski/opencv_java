import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class VideoCap {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    VideoCapture capture;
    Mat2Image mat2Image = new Mat2Image();

    VideoCap() {
        capture = new VideoCapture();
        capture.open(0);
    }

    BufferedImage getOneFrame() {
        capture.read(mat2Image.mat);
        return mat2Image.getImage(mat2Image.mat);
    }

}
