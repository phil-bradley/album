/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class FileUtilsTest {

    @BeforeAll
    static void setUp() {
        // Clear album.home
        System.clearProperty("album.home");
    }

    @Test
    void getHomeDirectoryReturnUserHome() {

        // If the home dir looks like /usr/local/home/fred then the folder name will be fred
        String userHomeSetting = System.getProperty("user.home");
        String[] tokens = userHomeSetting.split(Pattern.quote(File.separator));
        String folderName = tokens[tokens.length - 1];

        File homeDirectory = FileUtils.getHomeDirectory();

        assertEquals(folderName, homeDirectory.getName());
    }

    @Test
    void givenAlbumHomeSet_andExists_expectAlbumHome() throws IOException {

        // Create a folder that exists
        File albumDir = Files.createTempDirectory("album-test").toFile();

        System.setProperty("album.home", albumDir.getAbsolutePath());

        File homeDirectory = FileUtils.getHomeDirectory();
        assertEquals(albumDir.getAbsolutePath(), homeDirectory.getAbsolutePath());
    }

    @Test
    void givenAlbumHomeSet_andNotExists_expectUserHome() throws IOException {

        String userHomeSetting = System.getProperty("user.home");
        String[] tokens = userHomeSetting.split(Pattern.quote(File.separator));
        String userHomeFolderName = tokens[tokens.length - 1];

        System.setProperty("album.home", "album-test-1234");

        File homeDirectory = FileUtils.getHomeDirectory();
        assertEquals(userHomeFolderName, homeDirectory.getName());
    }

    @Test
    void givenImageFile_expectIsImageIsTrue() throws Exception {

        String resourcePath = getClass().getResource("/test_275x183.jpg").getPath();
        File file = new File(resourcePath);

        assertTrue(FileUtils.isImage(file));
    }

    @Test
    void givenNonImageFile_expectIsImageIsFalse() throws Exception {
        File tmpFile = Files.createTempFile("album-test", ".txt").toFile();
        assertFalse(FileUtils.isImage(tmpFile));
    }

    @Test
    void givenFolder_expectIsImageIsFalse() throws Exception {
        File dir = Files.createTempDirectory("album-test").toFile();
        assertFalse(FileUtils.isImage(dir));
    }

    @Test
    void givenFileNotExists_expectIsImageIsFalse() throws Exception {
        File file = new File("dfgdfkjfg");
        assertFalse(FileUtils.isImage(file));
    }

    @Test
    void givenFileExists_expectNotHidden() throws Exception {
        File file = Files.createTempFile("album-test", ".txt").toFile();
        assertFalse(FileUtils.isHidden(file));
    }

    @Test
    void givenFileExists_andStartsWithDot_expectNotHidden() throws Exception {
        File file = Files.createTempFile(".album-test", ".txt").toFile();
        assertTrue(FileUtils.isHidden(file));
    }

    @Test
    void givenFileIsNull_expectNotHidden() throws Exception {
        assertFalse(FileUtils.isHidden(null));
    }
}
