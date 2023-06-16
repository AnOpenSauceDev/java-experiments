package an.opensauce;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class EdgeDetect {

    void run(String path){
        Mat image;
        System.err.println("path: " + System.getProperty("user.dir"));
        if(path == null || path == "test"){
            image = Imgcodecs.imread("edgedetect-test.jpg");
        }else {
            image = Imgcodecs.imread(path);
        }

        Mat grayscale = new Mat();
        Imgproc.cvtColor(image,grayscale,Imgproc.COLOR_BGR2GRAY); // uses the B(lue)G(reen)R(ed) color format, caused by imread();
        Mat blur = new Mat();
        Imgproc.blur(grayscale,blur, new Size(2,2));

        Mat x = new Mat(),y = new Mat(),xy = new Mat();
        Imgproc.Sobel(blur,x, CvType.CV_64F, 1, 0, 5);
        Imgproc.Sobel(blur,y, CvType.CV_64F, 0, 1, 5);
        Imgproc.Sobel(blur,xy, CvType.CV_64F, 1, 1, 5);


        Imgcodecs.imwrite("C:\\Users\\User\\test\\output.png",xy);





    }

}
