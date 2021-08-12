/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.cicdassistant.wiki.authorization.RequestDataProvider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 *
 * @author diego
 */
public class RestEasyRequestProvider implements RequestDataProvider {

    @Override
    public String readCookie(String name) {

        HttpServletRequest request = getServletRequest();

        if (request != null && !StringUtils.isNullOrEmpty(name)) {

            Cookie[] cookies = request.getCookies();

            if (cookies != null) {

                for (Cookie cookie : cookies) {

                    if (name.contentEquals(cookie.getName())) {

                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String readHeader(String name) {

        HttpServletRequest request = getServletRequest();

        if (request != null && !StringUtils.isNullOrEmpty(name)) {

            return request.getHeader(name);
        }
        return null;
    }

    @Override
    public String readQuery(String name) {

        HttpServletRequest request = getServletRequest();

        if (request != null && !StringUtils.isNullOrEmpty(name)) {

            String queryString = request.getQueryString();

            if (!StringUtils.isNullOrEmpty(queryString)) {
                QueryStringMap map = new QueryStringMap(queryString);

                return map.get(name);
            }
        }
        return null;
    }

    private HttpServletRequest getServletRequest() {

        try {
            return ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        } catch (Exception e) {
        }
        return null;
    }

}
