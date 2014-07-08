package learning_imageset_creator;

import java.awt.*;
import java.io.File;

import ij.*;
import ij.gui.Roi;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import ij.process.MedianCut;

public class Main {
    static String suffix = "png";
    static String source = "E:\\RootWorkspace\\rbm_face_images\\";
    static String sink = "E:\\RootWorkspace\\rbm_face_images_png\\";

    public static void main(String args[]) {
        System.out.println("blurblrblrblr");
        boolean createCompleteImages = false;

        if (createCompleteImages) {
            changeSuffix("training_set");
            changeSuffix("1000_images_trained");
            changeSuffix("1000_images_not_trained");
        } else {
            manipulateImages("1000_images_not_trained", "1000_images_not_trained_incomplete");
            manipulateImages("1000_images_trained", "1000_images_trained_incomplete");
        }
        System.out.println("finished");
    }

    public static void changeSuffix(String dirName) {
        String pathFrom = source + dirName + "\\";
        String pathToBeginning = source + dirName + "\\";
        System.out.println("pathFrom :" + pathFrom);
        System.out.println("pathTo   :" + pathToBeginning);

        File dir = new File(pathFrom);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String pathTo = pathToBeginning + child.getName();
                //System.out.println("from :" + pathFrom + child.getName());
                //System.out.println("to   :" + pathTo);
                ImagePlus img = new ImagePlus(pathFrom + child.getName());
                //img.show();

                // next part seems irrelevant but is used to convert ppm to png
                ImageProcessor ip = img.getProcessor();
                Roi roi = new Roi(0, 0, 0, 0);
                ip.setRoi(roi);
                ip.setValue(0);

                IJ.saveAs(img, suffix, pathTo);
                //img.show();
                //break;
            }
        } else {
        }
    }

    public static void manipulateImages(String fromDir, String toDir) {
        String pathFrom = sink + fromDir + "\\";
        String pathToBeginning =  sink + toDir + "\\";
        System.out.println("pathFrom :" + pathFrom);
        System.out.println("pathTo   :" + pathToBeginning);

        File dir = new File(pathFrom);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String pathTo = pathToBeginning + child.getName();
                //System.out.println("from :" + pathFrom + child.getName());
                //System.out.println("to   :" + pathTo);
                ImagePlus img = new ImagePlus(pathFrom + child.getName());
                ImageProcessor ip = img.getProcessor();

                double r = Math.random();
                Roi roi;
                if (r < .25) {
                    roi = new Roi(0, 0, img.getWidth() / 2, img.getHeight());
                } else if (r > .25 && r < .5) {
                    roi = new Roi(img.getWidth() / 2, 0, img.getWidth() / 2, img.getHeight());
                } else if (r > .5 && r < .75) {
                    roi = new Roi(0, 0, img.getWidth(), img.getHeight() / 2);
                } else {
                    roi = new Roi(0, img.getHeight() / 2, img.getWidth(), img.getHeight());
                }
                ip.setRoi(roi);
                ip.setColor(Color.gray);
                ip.fill();

                IJ.saveAs(img, suffix, pathTo);
                //img.show();
            }
        } else {
        }
    }
}
