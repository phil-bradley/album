/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.metadata.ImageMetaData;
import ie.philb.album.metadata.ImageMetaDataReader;
import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import javax.imageio.ImageIO;

/**
 *
 * @author philb
 */
public class ThumbnailProvider {

    public final Map<String, Thumbnail> thumbnailMap = new ConcurrentHashMap<>();

//    public final Map<String, BufferedImage> imageMap = new ConcurrentHashMap<>();
//    public final Map<String, ImageMetaData> metadataMap = new HashMap<>();
    private final BlockingQueue<String> pendingLoadQueue = new LinkedBlockingQueue<>();
    private final Dimension thumbnailSize;
    private final Map<String, Consumer<Thumbnail>> pendingConsumers = new HashMap<>();
    private final Thumbnail placeHolderThumbnail = new Thumbnail("Loading", ImageUtils.getPlaceholderImage());

    public ThumbnailProvider(Dimension size) {
        this.thumbnailSize = size;
        Thread worker = new Thread(this::processQueue);
        worker.setDaemon(true); // Will not block JVM shutdown
        worker.start();
    }

    public void applyImage(String key, Consumer<Thumbnail> consumer) {

        Thumbnail thumbnail = getThumbnail(key);

        if (thumbnail != null) {
            consumer.accept(thumbnail);
        } else {
            consumer.accept(placeHolderThumbnail);
            pendingConsumers.put(key, consumer);
        }
    }

    public boolean hasThumbnail(String key) {
        return thumbnailMap.containsKey(key);
    }

    public Thumbnail getThumbnail(String key) {

        if (thumbnailMap.containsKey(key)) {
            return thumbnailMap.get(key);
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

                if (!thumbnailMap.containsKey(key)) {
                    BufferedImage image = loadImage(key);
                    BufferedImage scaled = scaleImage(image);

                    if (scaled == null) {
                        thumbnailMap.put(key, new Thumbnail("Loading", ImageUtils.getPlaceholderSmallImage()));
                    } else {
                        ImageMetaData metaData = tryReadMetaData(key);
                        Thumbnail thumbnail = new Thumbnail(key, scaled, metaData);
                        thumbnailMap.put(key, thumbnail);
                    }

                    if (pendingConsumers.containsKey(key)) {
                        Consumer<Thumbnail> consumer = pendingConsumers.get(key);
                        pendingConsumers.remove(key);
                        consumer.accept(thumbnailMap.get(key));
                    }
                } else {
                    // Key already present
                }
            } catch (InterruptedException ix) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private ImageMetaData tryReadMetaData(String fileName) {
        File imageFile = new File(fileName);

        if (!imageFile.exists()) {
            return null;
        }

        try {
            return new ImageMetaDataReader(imageFile).getMetaData();
        } catch (Exception ex) {
            return null;
        }
    }

    private BufferedImage scaleImage(BufferedImage image) {
        return ImageUtils.scaleImageToFit(image, thumbnailSize);
    }

    private BufferedImage loadImage(String key) {
        try {
            return ImageIO.read(new File(key));
        } catch (Throwable tx) {
            return null;
        }
    }

}
