/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.ui.common.Icons;
import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import javax.imageio.ImageIO;

/**
 *
 * @author philb
 */
public class ThumbnailProvider {

    public final Map<String, BufferedImage> imageMap = new ConcurrentHashMap<>();
    private final BlockingQueue<String> pendingLoadQueue = new LinkedBlockingQueue<>();
    private final Dimension thumbnailSize;
    private Map<String, ThumbnailProviderListener> pendingListeners = new HashMap<>();

    public ThumbnailProvider(Dimension size) {
        this.thumbnailSize = size;
        Thread worker = new Thread(this::processQueue);
        worker.setDaemon(true); // Will not block JVM shutdown
        worker.start();
    }

    public void applyImage(String key, ThumbnailProviderListener listener) {

        BufferedImage image = getImage(key);

        if (image != null) {
            listener.thumbnailLoaded(image);
        } else {
            listener.thumbnailLoaded(ImageUtils.getBufferedImage(Icons.LOADING));
            pendingListeners.put(key, listener);
        }
    }

    public BufferedImage getImage(String key) {

        if (imageMap.containsKey(key)) {
            return imageMap.get(key);
        }

        // This check is not thread safe so we may occasionally process the same
        // value more than once. The processQueue method checks for this
        if (!pendingLoadQueue.add(key)) {
            pendingLoadQueue.offer(key);
        }

        // Return null, not available yet
        return null;
    }

    private void processQueue() {

        while (true) {
            try {
                String key = pendingLoadQueue.take(); // block if empty
                System.out.println("Will try to load " + key);

                if (!imageMap.containsKey(key)) {
                    BufferedImage image = loadImage(key);
                    BufferedImage scaled = scaleImage(image);

                    if (scaled == null) {
                        System.out.println("Created placeholder for key " + key);
                        imageMap.put(key, ImageUtils.getBufferedImage(Icons.LOADING));
                    } else {
                        System.out.println("Created scaled for key " + key);
                        imageMap.put(key, scaled);
                    }

                    if (pendingListeners.containsKey(key)) {
                        ThumbnailProviderListener listener = pendingListeners.get(key);
                        pendingListeners.remove(key);
                        
                        System.out.println("Firing update for key " + key);
                        listener.thumbnailLoaded(imageMap.get(key));
                    }
                } else {
                    System.out.println("Key already present: " + key);
                }

                System.out.println("Pending listeners: " + pendingListeners.size());
            } catch (InterruptedException ix) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private BufferedImage scaleImage(BufferedImage image) {
        return ImageUtils.scaleImageToFit(image, thumbnailSize);
    }

    private BufferedImage loadImage(String key) {
        try {
            return ImageIO.read(new File(key));
        } catch (Throwable tx) {
            System.out.println("Failed to load " + key + ", " + tx.getMessage());
            return null;
        }
    }
}
