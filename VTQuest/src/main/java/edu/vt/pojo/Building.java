/*
 * Created by Osman Balci on 2025.10.15
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.pojo;
import java.math.BigDecimal;

public class Building {
    /*
    ======================================================
    Instance Variables representing VT Building Attributes
    ======================================================
    JSON Data returned from VTBuildingsAPI:
    {
        "abbreviation":"AGNEW",
        "address":"460 West Campus Drive",
        "category":"Academic",
        "description":"Agnew Hall is ...",
        "id":1,
        "imageFilename":"Agnew",
        "latitude":37.2247600000,
        "longitude":-80.4241500000,
        "name":"Agnew Hall",
        "yearBuilt":1940
     },
     */
    private Long id;
    private String name;
    private String abbreviation;
    private String category;
    private String description;
    private Integer yearBuilt;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String imageFilename;

    /*
    ======================================================
    Class constructors for instantiating a Building entity
    object to represent a particular VT building.
    ======================================================
     */
    public Building() {
    }

    public Building(Long id) {
        this.id = id;
    }

    public Building(Long id, String name, String abbreviation, String category, String description, Integer yearBuilt, String address, BigDecimal latitude, BigDecimal longitude, String imageFilename) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.category = category;
        this.description = description;
        this.yearBuilt = yearBuilt;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageFilename = imageFilename;
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
