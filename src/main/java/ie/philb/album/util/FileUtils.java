/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Philip.Bradley
 */
public class FileUtils {

    public static File getHomeDirectory() {
        return new File(System.getProperty("user.home"));
    }

    public static boolean isImage(File file) {

        if (!file.isFile()) {
            return false;
        }

        if (!file.exists()) {
            return false;
        }

        try {
            Path path = file.toPath();
            String fileType = Files.probeContentType(path);

            if (fileType == null) {
                return false;
            }

            return (fileType.toLowerCase().startsWith("image"));
        } catch (IOException ex) {
            return false;
        }

    }

    public static boolean isHidden(File file) {

        if (file == null) {
            return false;
        }

        if (file.isHidden()) {
            return true;
        }

        if (file.getName().startsWith(".")) {
            return true;
        }

        return false;
    }
}
