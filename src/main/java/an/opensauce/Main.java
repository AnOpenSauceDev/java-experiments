package an.opensauce;

import org.opencv.core.Core;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner Input;

        System.out.println("--- Welcome! ---");
        Input = new Scanner(System.in);
        System.out.println("Are you using: ");
        System.out.println("[1] an image.");
        System.out.println("[2] a video file.");
        System.out.println("Please enter a number...");
        int choice = Input.nextInt();

        System.out.println("please enter the filename of what you are converting... (Example: video.webm, image.png) spaces aren't valid.");
        String path = Input.next();
        String canny = null;
        if(choice == 2) {
            System.out.println("use canny? (produces usually better (sometimes worse) results) [Y/n]");
            canny = Input.next();
        }
        LoadDependencies(); // load important libraries and stuff (DLL's, not.jar's, that stuff goes in gradle.)
        EdgeDetect detect = new EdgeDetect();
        if(choice == 1){
        detect.run(path);
        }
        if(choice == 2){
            if(canny.matches("(?i)yes|y|ye|yees|yess|yse")) { // checks for many variants of messed-up yes-es
                detect.video_process(path, true);
            }else {
                detect.video_process(path, false);
            }
        }

        System.out.println("Conversion complete! Run again? [Y/n]");
        String response = Input.next();
        if(response.matches("(?i)yes|y|ye|yees|yess|yse")){ // checks for many variants of messed-up yes-es
            Main.main(null);
        }else {
            System.out.println("goodbye!");
        }

    }

    static void LoadDependencies(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // this will *TRY* to find opencv.dll by scanning your entire PATH.
    }

}