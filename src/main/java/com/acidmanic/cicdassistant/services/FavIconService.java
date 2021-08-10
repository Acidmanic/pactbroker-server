/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.GraphicResources;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import net.sf.image4j.codec.ico.ICOEncoder;

/**
 *
 * @author diego
 */
public class FavIconService {

    public byte[] getFavIconBytes() {

        byte[] pngData = new GraphicResources().getIconBytes();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(pngData);

        BufferedImage pngImage = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);

        try {
            pngImage = ImageIO.read(inputStream);
        } catch (Exception e) {
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ICOEncoder.write(pngImage, outputStream);
            
        } catch (Exception e) {
        }

        byte[] iconData = outputStream.toByteArray();

        return iconData;

    }
}
