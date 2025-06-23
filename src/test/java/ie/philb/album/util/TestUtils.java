/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.io.File;

/**
 *
 * @author philb
 */
public class TestUtils {

    public static File getTestImageFile() {
        String resourcePath = TestUtils.class.getResource("/test_275x183.jpg").getPath();
        return new File(resourcePath);
    }
}
