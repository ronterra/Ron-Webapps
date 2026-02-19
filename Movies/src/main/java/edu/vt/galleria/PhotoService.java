/*
 * Created by Osman Balci on 2025.9.22
 * Copyright © 2025 Osman Balci. All rights reserved.
 */

package edu.vt.galleria;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@Named(value = "photoService")
@ApplicationScoped
public class PhotoService {
    /*
    ============================
    Instance Variable (Property)
    ============================
     */
    private List<Photo> listOfPhotos;

    /*
    The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization. The initialization
    method init() is the first method invoked before this class is put into service.
     */
    @PostConstruct
    public void init() {
        listOfPhotos = new ArrayList<>();

        listOfPhotos.add(new Photo("/resources/images/photos/photo1.jpg", "/resources/images/photos/photo1s.jpg",
                "Description for Photo 1", "Star Wars: The Rise of Skywalker"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo2.jpg", "/resources/images/photos/photo2s.jpg",
                "Description for Photo 2", "James Bond: No Time to Die"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo3.jpg", "/resources/images/photos/photo3s.jpg",
                "Description for Photo 3", "X-Men: Apocalypse"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo4.jpg", "/resources/images/photos/photo4s.jpg",
                "Description for Photo 4", "The Hunger Games: Catching Fire"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo5.jpg", "/resources/images/photos/photo5s.jpg",
                "Description for Photo 5", "Captain America: Civil War"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo6.jpg", "/resources/images/photos/photo6s.jpg",
                "Description for Photo 6", "Star Wars: The Force Awakens"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo7.jpg", "/resources/images/photos/photo7s.jpg",
                "Description for Photo 7", "Live Die Repeat: Edge of Tomorrow"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo8.jpg", "/resources/images/photos/photo8s.jpg",
                "Description for Photo 8", "Black Panther"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo9.jpg", "/resources/images/photos/photo9s.jpg",
                "Description for Photo 9", "Avengers: Endgame"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo10.jpg", "/resources/images/photos/photo10s.jpg",
                "Description for Photo 10", "Pirates of the Caribbean: Dead Man's Chest"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo11.jpg", "/resources/images/photos/photo11s.jpg",
                "Description for Photo 11", "Rogue One: A Star Wars Story"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo12.jpg", "/resources/images/photos/photo12s.jpg",
                "Description for Photo 12", "Batman v Superman: Dawn of Justice"));
    }

    /*
    =============
    Getter Method
    =============
     */
    public List<Photo> getListOfPhotos() {
        return listOfPhotos;
    }
}
