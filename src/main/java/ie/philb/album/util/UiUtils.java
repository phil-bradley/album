/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Component;
import javax.swing.SwingUtilities;

/**
 *
 * @author philb
 */
public class UiUtils {

    public static <T extends Component> T getChildOfType(Component root, int x, int y, Class<T> type) {

        Component deepest = SwingUtilities.getDeepestComponentAt(root, x, y);
        while (deepest != null) {
            if (type.isInstance(deepest)) {
                return type.cast(deepest);
            }
            deepest = deepest.getParent();
        }

        return null;
    }
}
