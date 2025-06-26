/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

/**
 *
 * @author philb
 */
public class StringUtils {

    public static boolean hasValue(String s) {
        return !isBlank(s);
    }
    
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        return s.trim().length() == 0;
    }
}
