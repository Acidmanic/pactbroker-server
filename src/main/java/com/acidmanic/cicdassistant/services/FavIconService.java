/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.GraphicResources;
import com.acidmanic.cicdassistant.utility.ResourceHelper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import net.sf.image4j.codec.ico.ICOEncoder;

/**
 *
 * @author diego
 */
public class FavIconService {

    private boolean cached;
    private byte[] imageData;

    public FavIconService(boolean cached) {
        this.cached = cached;
    }

    public FavIconService() {
        this(false);
    }

    public synchronized byte[] getFavIconBytes() {

        if (!cached || imageData == null || imageData.length == 0) {

            this.imageData = produceFavIconBytes();
        }

        return this.imageData;
    }

    private byte[] produceFavIconBytes() {

        BufferedImage pngImage = getAvailableFavIconImage();

        byte[] iconData = imageToIconBytes(pngImage);

        return iconData;
    }

    private BufferedImage getAvailableFavIconImage() {

        File baseDirectory = new ResourceHelper().getExecutionDirectory().toFile();

        File files[] = baseDirectory.listFiles();

        for (File file : files) {

            if (file.isFile()) {

                if (file.getName().toLowerCase().startsWith("favicon.")) {

                    try {

                        BufferedImage readImage = ImageIO.read(file);

                        return readImage;

                    } catch (Exception e) {
                    }
                }
            }
        }
        return new GraphicResources().getIconImage();
    }

    private byte[] imageToIconBytes(BufferedImage image) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ICOEncoder.write(image, outputStream);

        } catch (Exception e) {
        }

        byte[] iconData = outputStream.toByteArray();

        return iconData;
    }
}
