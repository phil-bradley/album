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
public class ClassBuilder {

    public <T> T createInstance(Class<T> clazz, File file) throws Exception {
        return clazz.getDeclaredConstructor(File.class).newInstance(file);
    }

}
