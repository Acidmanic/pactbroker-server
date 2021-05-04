/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.utility;

import com.acidmanic.localpactbroker.models.BadgeType;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class Badges {

    public String getBadgeSvg(BadgeType type) {

        byte[] binary = getBadgeBytes(type);

        return new String(binary);
    }

    public byte[] getBadgeBytes(BadgeType type) {

        String name = "unknown.svg";

        if (type == BadgeType.Success) {
            name = "success.svg";
        }
        if (type == BadgeType.Failure) {
            name = "failure.svg";
        }
        Path resourceBase = Paths.get("badges");

        name = resourceBase.resolve(name).normalize().toString();

        return new ResourceHelper().readResource(name);

    }
}
