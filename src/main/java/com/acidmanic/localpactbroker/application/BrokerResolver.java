/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.utility.myoccontainer.Resolver;
import com.acidmanic.utility.myoccontainer.configuration.data.Dependency;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class BrokerResolver extends Resolver {

    private BrokerResolver() {
        install(new BrokerIocRegistry());
    }

    private static BrokerResolver instance = null;

    public static synchronized BrokerResolver makeInstance() {

        if (BrokerResolver.instance == null) {
            BrokerResolver.instance = new BrokerResolver();
        }
        return BrokerResolver.instance;
    }

    public <T> List<T> resolveAllAnnotatedBy(Class annotationType) {

        return resolveAllBy(t -> t.getAnnotation(annotationType) != null);
    }

    public <T> List<T> resolveAllImplemented(Class implementationType) {
        return resolveAllBy(t -> implemented(t, implementationType));
    }

    private boolean implemented(Class t, Class implementationType) {

        Class current = t;

        while (current != null) {
            for (Class iface : current.getInterfaces()) {
                if (implementationType.equals(iface)) {
                    return true;
                }
            }
            current = current.getSuperclass();
        }
        return false;
    }

    private interface Matcher {

        boolean matches(Class type);
    }

    private <T> List<T> resolveAllBy(Matcher matcher) {
        Resolver resolver = BrokerResolver.makeInstance();

        List<Dependency> all = resolver.getRegisteredDependancies().toList();

        ArrayList<T> result = new ArrayList<>();

        all.forEach(d -> {
            Class type = d.getTaggedClass().getType();

            if (matcher.matches(type)) {

                T object = resolver.tryResolve(type);

                if (object != null) {
                    result.add(object);

                }
            }
        });
        return result;
    }
}
