/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.imageio.ImageIO;

/**
 *
 * @author philb
 */
public class ImageProvider {

    private final Map<String, BufferedImage> imageMap = new ConcurrentHashMap<>();
    private final BlockingQueue<String> requestQueue = new LinkedBlockingQueue<>();
    private final Set<String> pendingKeys = ConcurrentHashMap.newKeySet();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ImageProvider() {
        executor.submit(this::processQueue);
    }

    public BufferedImage getImage(String key) {

        if (imageMap.containsKey(key)) {
            return imageMap.get(key);
        }

        // If not already pending, queue it for background loading
        if (pendingKeys.add(key)) {
            requestQueue.offer(key);
        }

        // Return null, not available yet
        return null;
    }

    private void processQueue() {

        while (true) {
            try {
                String key = requestQueue.take(); // blocks if empty
                imageMap.put(key, loadImage(key));
                pendingKeys.remove(key);
            } catch (InterruptedException ix) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private BufferedImage loadImage(String key) {
        try {
            return ImageIO.read(new File(key));
        } catch (IOException iox) {
            return null;
        }
    }
}
