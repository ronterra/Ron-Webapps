/*
 * Created by Osman Balci on 2025.10.16
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Building")

@NamedQueries({
    @NamedQuery(name = "Building.findAll", query = "SELECT b FROM Building b")
    , @NamedQuery(name = "Building.findById", query = "SELECT b FROM Building b WHERE b.id = :id")
    , @NamedQuery(name = "Building.findByName", query = "SELECT b FROM Building b WHERE b.name = :name")
    , @NamedQuery(name = "Building.findByAbbreviation", query = "SELECT b FROM Building b WHERE b.abbreviation = :abbreviation")
    , @NamedQuery(name = "Building.findByLatitude", query = "SELECT b FROM Building b WHERE b.latitude = :latitude")
    , @NamedQuery(name = "Building.findByLongitude", query = "SELECT b FROM Building b WHERE b.longitude = :longitude")
    , @NamedQuery(name = "Building.findByCategory", query = "SELECT b FROM Building b WHERE b.category = :category")
    , @NamedQuery(name = "Building.findByDescription", query = "SELECT b FROM Building b WHERE b.description = :description")
    , @NamedQuery(name = "Building.findByImageFilename", query = "SELECT b FROM Building b WHERE b.imageFilename = :imageFilename")})

public class Building implements Serializable {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Building table in the VTBuildingsDB database.

    CREATE TABLE Building
    (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        abbreviation VARCHAR(12) NOT NULL,
        category VARCHAR(64) NOT NULL,
        description VARCHAR(3000) NOT NULL,
        year_built INT NOT NULL,
        address VARCHAR(300) NOT NULL,
        latitude DECIMAL(13,10) NOT NULL,
        longitude DECIMAL(13,10) NOT NULL,
        image_filename VARCHAR(64) NOT NULL
    );
    ========================================================
     */
    private static final long serialVersionUID = 1L;

    // id INT PRIMARY KEY AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    // name VARCHAR(100) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    // abbreviation VARCHAR(12) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "abbreviation")
    private String abbreviation;

    // category VARCHAR(64) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "category")
    private String category;

    // description VARCHAR(3000) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "description")
    private String description;

    // year_built INT NOT NULL
    @Basic(optional = false)
    @NotNull
    @Column(name = "year_built")
    private Integer yearBuilt;

    // address VARCHAR(300) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "address")
    private String address;

    // latitude DECIMAL(13,10) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private BigDecimal latitude;

    // longitude DECIMAL(13,10) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private BigDecimal longitude;

    // image_filename VARCHAR(64) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "image_filename")
    private String imageFilename ;

    public Building() {
    }

    public Building(Integer id) {
        this.id = id;
    }

    public Building(Integer id, String name, String abbreviation, String category, String description, Integer yearBuilt, String address, BigDecimal latitude, BigDecimal longitude, String imageFilename) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the Building object identified by 'object' is the same as the Building object identified by 'id'
     Parameter object = Building object identified by 'object'
     Returns True if the Building 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Building)) {
            return false;
        }
        Building other = (Building) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
    
}
