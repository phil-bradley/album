/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class FileUtils {

    public static final String HOME_LOCATION_PROPERTY = "user.home";

    public static File getHomeDirectory() {

        String albumHomePropertyValue = System.getProperty("album.home", "");

        if (!albumHomePropertyValue.trim().isBlank()) {
            File albumHome = new File(albumHomePropertyValue);
            if (albumHome.exists() && albumHome.isDirectory()) {
                return albumHome;
            }
        }

        return new File(System.getProperty(HOME_LOCATION_PROPERTY));
    }

    public static boolean isImage(File file) {

        if (!file.exists()) {
            return false;
        }

        if (!file.isFile()) {
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

        return (file.getName().startsWith(".") || file.isHidden());
    }

    public static List<Path> getPathToRoot(Path file) {

        List<Path> paths = new ArrayList<>();

        Path current = file;

        while (current != null) {
            paths.add(current);
            current = current.getParent();
        }

        Collections.reverse(paths);
        return paths;
    }

    public static List<File> getChildren(File parent) {
        // Use dummy filter, matches everything
        return getChildren(parent, (File file) -> true);
    }

    public static List<File> getChildren(File parent, FileFilter filter) {

        if (parent == null) {
            return null;
        }

        File[] children = parent.listFiles();

        if (children == null) {
            return Collections.emptyList();
        }

        // Directories first
        List<File> directories = new ArrayList<>();

        for (File file : children) {
            if (file.isDirectory() && !FileUtils.isHidden(file)) {
                directories.add(file);
            }
        }

        Collections.sort(directories, (File f, File f1) -> f.getName().toLowerCase().compareTo(f1.getName().toLowerCase()));

        List<File> files = new ArrayList<>();

        for (File file : children) {
            if (filter.matches(file)) {
                files.add(file);
            }
        }

        Collections.sort(files, (File f, File f1) -> f.getName().toLowerCase().compareTo(f1.getName().toLowerCase()));

        List<File> entries = new ArrayList<>(directories.size() + files.size());
        entries.addAll(directories);
        entries.addAll(files);

        return entries;
    }
}
