/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.cicdassistant.models.BadgeType;
import com.sun.mail.iap.ByteArray;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author diego
 */
public class GraphicResources {

    public String getBadgeSvg(BadgeType type) {

        byte[] binary = getBadgeBytes(type, "svg");

        return new String(binary);
    }

    public byte[] getBadgeBytes(BadgeType type, String format) {

        String extention = "svg".equalsIgnoreCase(format) ? ".svg" : ".png";

        String name = "unknown";

        if (type == BadgeType.Success) {
            name = "success";
        }
        if (type == BadgeType.Failure) {
            name = "failure";
        }
        Path resourceBase = Paths.get("badges");

        name = resourceBase.resolve(name + extention).normalize().toString();

        return new ResourceHelper().readResource(name);

    }

    public byte[] getIconBytes() {

        String name = "icon.png";

        Path resourceBase = Paths.get("icons").resolve(name);

        String resourceName = resourceBase.normalize().toString();

        return new ResourceHelper().readResource(resourceName);

    }

    public BufferedImage getIconImage() {

        byte[] pngData = getIconBytes();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(pngData);

        BufferedImage pngImage = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);

        try {
            pngImage = ImageIO.read(inputStream);

        } catch (Exception e) {
        }
        return pngImage;
    }

}
