import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class Mat2Image {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    Mat mat = new Mat();
    BufferedImage bufferedImage;

    public Mat2Image() {

    }

    public Mat2Image(Mat mat) {
        getSpace(mat);
    }

    public void getSpace(Mat mat) {
        int type = 0;

        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if(mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        this.mat = mat;
        int w = mat.cols();
        int h = mat.rows();

        if (bufferedImage == null || bufferedImage.getWidth() != w || bufferedImage.getHeight() != h || bufferedImage.getType() != type) {
            bufferedImage = new BufferedImage(w, h, type);
        }
    }

    public BufferedImage getImage(Mat mat) {
        getSpace(mat);

        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("haarcascade_frontalface_alt.xml");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(mat, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(mat, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            Imgproc.putText(mat, "FACE", new Point(rect.x, rect.y - 2), 1, 1, new Scalar(0, 255, 0));
        }

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return bufferedImage;
    }

}
