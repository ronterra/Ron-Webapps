/*
 * Created by Osman Balci on 2025.10.16
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.api;

import jakarta.ws.rs.core.Application;
import java.util.Set;

/*
 This defines the first part of the request URL as
 https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api
 */
@jakarta.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(BuildingFacadeREST.class);
    }
    
}
