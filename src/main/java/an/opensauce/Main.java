package an.opensauce;

import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {

        LoadDependencies();

        EdgeDetect detect = new EdgeDetect();
        detect.run("test");
        detect.video_process("badapple.webm",true);
    }

    static void LoadDependencies(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // this will *TRY* to find opencv.dll by scanning your entire PATH
    }

}