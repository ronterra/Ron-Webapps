/*
 * Created by Osman Balci on 2025.10.15
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
                "Description for Photo 1", "An aerial view of the main campus"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo2.jpg", "/resources/images/photos/photo2s.jpg",
                "Description for Photo 2", "Lane Stadium / Worsham Field seats 65,632"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo3.jpg", "/resources/images/photos/photo3s.jpg",
                "Description for Photo 3", "Torgersen Hall offers computer labs, classrooms and study areas"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo4.jpg", "/resources/images/photos/photo4s.jpg",
                "Description for Photo 4", "Pylons and War Memorial Chapel"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo5.jpg", "/resources/images/photos/photo5s.jpg",
                "Description for Photo 5", "Moss Arts Center includes a 1,260-seat performance hall"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo6.jpg", "/resources/images/photos/photo6s.jpg",
                "Description for Photo 6", "Squires Student Center is a hub of student activity"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo7.jpg", "/resources/images/photos/photo7s.jpg",
                "Description for Photo 7", "Cassell Coliseum is the arena for basketball and other sports"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo8.jpg", "/resources/images/photos/photo8s.jpg",
                "Description for Photo 8", "Pearson Hall East and West contains 234 and 230 dorm rooms"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo9.jpg", "/resources/images/photos/photo9s.jpg",
                "Description for Photo 9", "Burruss Hall is the main administration building on campus"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo10.jpg", "/resources/images/photos/photo10s.jpg",
                "Description for Photo 10", "Alumni Mall and Drillfield"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo11.jpg", "/resources/images/photos/photo11s.jpg",
                "Description for Photo 11", "Football game at Lane Stadium / Worsham Field"));
        listOfPhotos.add(new Photo("/resources/images/photos/photo12.jpg", "/resources/images/photos/photo12s.jpg",
                "Description for Photo 12", "Virginia Tech / Montgomery Executive Airport"));
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
