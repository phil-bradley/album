/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie.philb.album.util;

import java.io.File;

/**
 *
 * @author philb
 */
public interface FileFilter {
    
    public boolean matches(File file);
}
