/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.cicdassistant.models.BadgeType;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class Badges {

    public String getBadgeSvg(BadgeType type) {

        byte[] binary = getBadgeBytes(type,"svg");

        return new String(binary);
    }

    public byte[] getBadgeBytes(BadgeType type,String format) {

        String extention = "svg".equalsIgnoreCase(format)?".svg":".png";
        
        String name = "unknown";

        if (type == BadgeType.Success) {
            name = "success";
        }
        if (type == BadgeType.Failure) {
            name = "failure";
        }
        Path resourceBase = Paths.get("badges");

        name = resourceBase.resolve(name+extention).normalize().toString();

        return new ResourceHelper().readResource(name);

    }
}
