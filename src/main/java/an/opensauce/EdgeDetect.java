package an.opensauce;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.tesseract.OSResults;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EdgeDetect {

    void run(String path){
        Mat image;

        System.out.println("path: " + System.getProperty("user.dir"));

        if(path == null || path == "test"){
            image = Imgcodecs.imread("/resources/demos/touhou/edgedetect-test.jpg");
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

        if(System.getProperty("os.name").contains("Win")) {
            Imgcodecs.imwrite("test\\_image_output.png", xy);
        }else { // assume Linux/Macos/BSD filesystem
            Imgcodecs.imwrite("./test/_image_output.png", xy);
        }

        Mat canny_output = new Mat();
        Imgproc.Canny(blur,canny_output,100,200,3,false);
        if(System.getProperty("os.name").contains("Win")) {
            Imgcodecs.imwrite("test\\_image_output-canny.png",canny_output);
        }else { // assume Linux/Macos/BSD filesystem
            Imgcodecs.imwrite("./test/_image_output-canny.png",canny_output);
        }

        System.out.println("Done!");

    }





    void video_process(String path, boolean Canny){
        boolean Is_Linux = !System.getProperty("os.name").contains("win");
        System.out.println("Starting process");
        long start = System.nanoTime();
        FFmpegFrameGrabber capture =  new FFmpegFrameGrabber(path);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {


            capture.start();
            int frameDuration = capture.getLengthInFrames();
            for(int i = 0; i <= frameDuration; i++){


                Frame frame = capture.grabImage();
                long convTime = System.nanoTime();
                Mat mat = BufferedImage2Mat(converter.convert(frame));

                Mat grayscale = new Mat();
                Imgproc.cvtColor(mat,grayscale,Imgproc.COLOR_BGR2GRAY); // uses the B(lue)G(reen)R(ed) color format, caused by imread();
                Mat blur = new Mat();
                Imgproc.blur(grayscale,blur, new Size(2,2));

                if(!Canny) { // sobel, slower
                    Mat x = new Mat(), y = new Mat(), xy = new Mat();
                    Imgproc.Sobel(blur, xy, CvType.CV_64F, 1, 1, 5);
                    if(!Is_Linux) {
                        Imgcodecs.imwrite("test\\file_" + i + ".png", xy);
                    }else {
                        Imgcodecs.imwrite("./test/file_" + i + ".png", xy);
                    }
                }


                if(Canny) { // canny edge-detection, about 2x as fast vs the full sobel algorithm, also sometimes good-looking
                    Mat canny_output = new Mat();
                    Imgproc.Canny(blur, canny_output, 100, 200, 3, false);
                    if(!Is_Linux) {
                        Imgcodecs.imwrite("test\\file_" + i + ".png", canny_output);
                    }else {
                        Imgcodecs.imwrite("./test/file_" + i + ".png", canny_output);
                    }
                }



            }
            capture.close();
            capture.release();
            long finaltime = System.nanoTime() - start;

            System.out.println("that took: " + finaltime + " nanoseconds to fully process (AKA: " + finaltime / 1000000 + "ms)" );

        }catch (Exception e){
            e.printStackTrace();

        }

    }


    // yoink'ed from StackOverflow: https://stackoverflow.com/questions/14958643/converting-bufferedimage-to-mat-in-opencv
    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
    }

}
