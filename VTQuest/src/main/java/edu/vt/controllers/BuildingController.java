/*
 * Created by Osman Balci on 2025.10.15
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.globals.Constants;
import edu.vt.pojo.Building;
import edu.vt.globals.Methods;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

@SessionScoped
@Named("buildingController")
public class BuildingController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private final String vtBuildingsAPIbaseURL = "https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api/vtBuildings/";
    private List<Building> listOfVTBuildings;
    private List<String> listOfVTBuildingNames;

    // List of object references of Building objects found in the search
    private List<Building> vtBuildingsFound;

    // Selected building
    private Building selected;

    // Geocoding Query
    private String geocodingQuery;

    // geocode() method below computes the following instance variables
    private String locationLatString;
    private String locationLngString;
    private double locationLatDouble;
    private double locationLngDouble;

    // Used in map.js to show found buildings on map
    private String foundVTBuildingsJsonData;

    // Used in map.js to show directions
    private String startingBuildingName;
    private String destinationBuildingName;
    private double startingBuildingLat;
    private double startingBuildingLng;
    private double destinationBuildingLat;
    private double destinationBuildingLng;
    private String travelMode;
    private String travelModeCapitalized;

    // Type of VTBuildingsAPI search
    private Integer searchType;

    // VTBuildingsAPI Search Query Variables (Q = Query)
    private String nameQ;
    private String descriptionQ;
    private String abbreviationQ;
    private String categoryQ;
    private String addressQ;
    private Integer yearBuiltQ;
    private Integer yearBuiltFromQ;
    private Integer yearBuiltToQ;
    private String eachContainsQ;

    /*
    =====================
    Initialization Method
    =====================

    The PostConstruct annotation is used on a method that needs to be executed 
    after dependency injection is done to perform any initialization. 
    This method is invoked before the class is put into service.
    
    This init() method creates vtBuildings, vtBuildingNames, vtBuildingAbbreviations,
    and vtBuildingCategories by using the VTBuildingsAPI RESTful web services.
     */
    @PostConstruct
    public void init() {

        selected = null;
        listOfVTBuildings = new ArrayList<>();

        String apiUrl = vtBuildingsAPIbaseURL + "getAll";

        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
          :    separates Key from the Value

        ******************************************************************
        *   Create the vtBuildings list containing VT building objects   *
        ******************************************************************
         */
        try {
            // Using the method given below, obtain the JSON data file as a String from the API URL
            String jsonData = Methods.readUrlContent(apiUrl);

            /*
            The API URL https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api/vtBuildings/getAll
            returns the following JSON file as one array containing 122 VT building JSON objects.

            [
                {"abbreviation":"AGNEW","address":"460 West Campus Drive","category":"Academic",
                "description":"Agnew Hall is ...","id":1,"imageFilename":"Agnew",
                "latitude":37.2247600000,"longitude":-80.4241500000,"name":"Agnew Hall","yearBuilt":1940},

                {"abbreviation":"LARNA","address":"500 Plantation Road","category":"Support",
                "description":"The facility includes ...","id":2,"imageFilename":"AlphinStuart",
                "latitude":37.2192900000,"longitude":-80.4399100000,"name":"Alphin-Stuart Livestock Teaching Arena","yearBuilt":2004},
                :
                :
                {"abbreviation":"WMS","address":"890 Drillfield Drive","category":"Academic",
                "description":"Williams Hall houses ...","id":121,"imageFilename":"Williams",
                "latitude":37.2278400000,"longitude":-80.4243500000,"name":"Williams Hall","yearBuilt":1953},

                {"abbreviation":"WRIGHT","address":"765 West Campus Drive","category":"Academic",
                "description":"The Wright House contains ...","id":122,"imageFilename":"WrightHouse",
                "latitude":37.2268300000,"longitude":-80.4261700000,"name":"Wright House","yearBuilt":1923}
            ]
             */

            // Convert the JSON data into a JSON array
            JSONArray jsonArrayOfBuildings = new JSONArray(jsonData);

            // Iterate over the JSON array containing VT building JSON objects
            jsonArrayOfBuildings.forEach(building -> {

                // Typecast the building as a JSON object
                JSONObject buildingJsonObject = (JSONObject) building;

                /*
                Building(Long id, String name, String abbreviation, String category, String description,
                Integer yearBuilt, String address, BigDecimal latitude, BigDecimal longitude, String imageFilename)
                 */

                //-------------------
                // Building id Number
                //-------------------
                Long id = buildingJsonObject.optLong("id", 0);
                // 0 value implies "Unavailable"

                //--------------
                // Building Name
                //--------------
                String name = buildingJsonObject.optString("name", "Unavailable");

                //---------------------------
                // Building Name Abbreviation
                //---------------------------
                String abbreviation = buildingJsonObject.optString("abbreviation", "Unavailable");

                //------------------
                // Building Category
                //------------------
                String category = buildingJsonObject.optString("category", "Unavailable");

                //---------------------
                // Building Description
                //---------------------
                String description = buildingJsonObject.optString("description", "Unavailable");

                //--------------------
                // Building Year Built
                //--------------------
                Integer yearBuilt = buildingJsonObject.optInt("yearBuilt", 0);
                // 0 value implies "Unavailable"

                //-----------------
                // Building Address
                //-----------------
                String address = buildingJsonObject.optString("address", "Unavailable");

                //------------------
                // Building Latitude
                //------------------
                double latitudeDouble = buildingJsonObject.optDouble("latitude", 0);
                // 0 value implies "Unavailable"
                BigDecimal latitude = BigDecimal.valueOf(latitudeDouble);

                //-------------------
                // Building Longitude
                //-------------------
                double longitudeDouble = buildingJsonObject.optDouble("longitude", 0);
                // 0 value implies "Unavailable"
                BigDecimal longitude = BigDecimal.valueOf(longitudeDouble);

                //------------------------
                // Building Image Filename
                //------------------------
                String imageFilename = buildingJsonObject.optString("imageFilename", "Unavailable");

                // Create a new building object dressed up with the values obtained from the JSON object
                Building bulding = new Building(id, name, abbreviation, category, description, yearBuilt, address, latitude, longitude, imageFilename);

                // Add the newly created building object to the list of VT buildings
                listOfVTBuildings.add(bulding);
            });

            // Sort the List of Building objects with respect to Name property in alphabetical order
            listOfVTBuildings.sort(Comparator.comparing(Building::getName));

        } catch (Exception ex) {
            Methods.showMessage("Fatal Error", "Something went wrong while accessing VTBuildingsAPI RESTful web services!",
                    "See: " + ex.getMessage());
        }

        /*
         ********************************************
         *   Create the List of VT Building Names   *
         ********************************************
         */
        listOfVTBuildingNames = new ArrayList<>();
        try {
            // API URL to return all VT building names
            String buildingNamesApiUrl = vtBuildingsAPIbaseURL + "names";

            // Using the method given below, obtain the JSON data file as a String from the API URL
            String jsonData = Methods.readUrlContent(buildingNamesApiUrl);

            /*
            The API URL https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api/vtBuildings/names
            returns the following JSON file as one array containing JSON objects with 'name':value pairs
            
            [
                {"name":"Agnew Hall"},
                {"name":"Alphin-Stuart Livestock Teaching Arena"},
                {"name":"Ambler Johnston Hall"},
                    :
                    :
                {"name":"William E. Lavery Animal Health Research Center"},
                {"name":"Williams Hall"},
                {"name":"Wright House"}
            ]
             */
            // Convert the JSON data into a JSON array
            JSONArray jsonArrayOfBuildingNames = new JSONArray(jsonData);

            // Iterate over the JSON array containing JSON objects with 'name':value pairs
            jsonArrayOfBuildingNames.forEach(buildingName -> {

                // Typecast the buildingName as JSON object
                JSONObject buildingNameJsonObject = (JSONObject) buildingName;

                //--------------
                // Building Name
                //--------------
                String name = buildingNameJsonObject.optString("name", "");
                if (!name.isEmpty()) {
                    listOfVTBuildingNames.add(name);
                }
            });

            // Sort the List of Building names (Strings) in alphabetical order
            Collections.sort(listOfVTBuildingNames);

        } catch (Exception ex) {
            Methods.showMessage("Fatal Error", "VTBuildingsAPI RESTful web service building names is unreachable!",
                    "See: " + ex.getMessage());
        }
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public List<Building> getListOfVTBuildings() {
        return listOfVTBuildings;
    }

    public void setListOfVTBuildings(List<Building> listOfVTBuildings) {
        this.listOfVTBuildings = listOfVTBuildings;
    }

    public List<String> getListOfVTBuildingNames() {
        return listOfVTBuildingNames;
    }

    public void setListOfVTBuildingNames(List<String> listOfVTBuildingNames) {
        this.listOfVTBuildingNames = listOfVTBuildingNames;
    }

    public Building getSelected() {
        return selected;
    }

    public void setSelected(Building selected) {
        this.selected = selected;
    }

    public String getGeocodingQuery() {
        return geocodingQuery;
    }

    public void setGeocodingQuery(String geocodingQuery) {
        this.geocodingQuery = geocodingQuery;
    }

    public String getLocationLatString() {
        return locationLatString;
    }

    public void setLocationLatString(String locationLatString) {
        this.locationLatString = locationLatString;
    }

    public String getLocationLngString() {
        return locationLngString;
    }

    public void setLocationLngString(String locationLngString) {
        this.locationLngString = locationLngString;
    }

    public double getLocationLatDouble() {
        return locationLatDouble;
    }

    public void setLocationLatDouble(double locationLatDouble) {
        this.locationLatDouble = locationLatDouble;
    }

    public double getLocationLngDouble() {
        return locationLngDouble;
    }

    public void setLocationLngDouble(double locationLngDouble) {
        this.locationLngDouble = locationLngDouble;
    }

    public String getFoundVTBuildingsJsonData() {
        return foundVTBuildingsJsonData;
    }

    public void setFoundVTBuildingsJsonData(String foundVTBuildingsJsonData) {
        this.foundVTBuildingsJsonData = foundVTBuildingsJsonData;
    }
    /*
     This method is used below at line 793.
     To jump to a line number in code on Mac: Command+L or on Windows: Control+G
     :::
     String jsonData = Methods.readUrlContent(apiUrl);
     //-----------------------------------------------------------------------------
     // foundVTBuildingsJsonData is used in map.js to display their locations on map
     //-----------------------------------------------------------------------------
     setFoundVTBuildingsJsonData(jsonData);
     :::
     */

    public String getStartingBuildingName() {
        return startingBuildingName;
    }

    public void setStartingBuildingName(String startingBuildingName) {
        this.startingBuildingName = startingBuildingName;
    }

    public String getDestinationBuildingName() {
        return destinationBuildingName;
    }

    public void setDestinationBuildingName(String destinationBuildingName) {
        this.destinationBuildingName = destinationBuildingName;
    }

    public double getStartingBuildingLat() {
        return startingBuildingLat;
    }

    public void setStartingBuildingLat(double startingBuildingLat) {
        this.startingBuildingLat = startingBuildingLat;
    }

    public double getStartingBuildingLng() {
        return startingBuildingLng;
    }

    public void setStartingBuildingLng(double startingBuildingLng) {
        this.startingBuildingLng = startingBuildingLng;
    }

    public double getDestinationBuildingLat() {
        return destinationBuildingLat;
    }

    public void setDestinationBuildingLat(double destinationBuildingLat) {
        this.destinationBuildingLat = destinationBuildingLat;
    }

    public double getDestinationBuildingLng() {
        return destinationBuildingLng;
    }

    public void setDestinationBuildingLng(double destinationBuildingLng) {
        this.destinationBuildingLng = destinationBuildingLng;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getTravelModeCapitalized() {
        return travelModeCapitalized;
    }

    public void setTravelModeCapitalized(String travelModeCapitalized) {
        this.travelModeCapitalized = travelModeCapitalized;
    }

    public String getNameQ() {
        return nameQ;
    }

    public void setNameQ(String nameQ) {
        this.nameQ = nameQ;
    }

    public String getDescriptionQ() {
        return descriptionQ;
    }

    public void setDescriptionQ(String descriptionQ) {
        this.descriptionQ = descriptionQ;
    }

    public String getAbbreviationQ() {
        return abbreviationQ;
    }

    public void setAbbreviationQ(String abbreviationQ) {
        this.abbreviationQ = abbreviationQ;
    }

    public String getCategoryQ() {
        return categoryQ;
    }

    public void setCategoryQ(String categoryQ) {
        this.categoryQ = categoryQ;
    }

    public String getAddressQ() {
        return addressQ;
    }

    public void setAddressQ(String addressQ) {
        this.addressQ = addressQ;
    }

    public Integer getYearBuiltQ() {
        return yearBuiltQ;
    }

    public void setYearBuiltQ(Integer yearBuiltQ) {
        this.yearBuiltQ = yearBuiltQ;
    }

    public Integer getYearBuiltFromQ() {
        return yearBuiltFromQ;
    }

    public void setYearBuiltFromQ(Integer yearBuiltFromQ) {
        this.yearBuiltFromQ = yearBuiltFromQ;
    }

    public Integer getYearBuiltToQ() {
        return yearBuiltToQ;
    }

    public void setYearBuiltToQ(Integer yearBuiltToQ) {
        this.yearBuiltToQ = yearBuiltToQ;
    }

    public String getEachContainsQ() {
        return eachContainsQ;
    }

    public void setEachContainsQ(String eachContainsQ) {
        this.eachContainsQ = eachContainsQ;
    }

    /*
    ================
    Instance Methods
    ================
     */

    // Unselect Selected Building Object
    public void unselect() {
        selected = null;
    }

    // Clear geocoding query
    public void clearGeocodingQuery() {
        geocodingQuery = "";
    }

    /*
     *****************************
     *   Return Google API Key   *
     *****************************
     */
    public String googleApiKey() {
        return Constants.GOOGLE_API_KEY;
    }

    /*
     ****************************************
     *   Return VT Map Center Coordinates   *
     ****************************************
     */
    public BigDecimal vtMapCenterLatitude() {
        return Constants.VT_MAP_CENTER_LATITUDE;
    }

    public BigDecimal vtMapCenterLongitude() {
        return Constants.VT_MAP_CENTER_LONGITUDE;
    }

    /*
     ================================================================
     INPUT: query
        Query can be a full address, “city, country”, a landmark name
        or “latitude, longitude” to show its location on Google map

     OUTPUT: Computed values of the following instance variables:
        private String locationLatString;
        private String locationLngString;
        private double locationLatDouble;
        private double locationLngDouble;
     ================================================================
     */
    public void geocode(String query) {

        String cleanedQuery = query.replaceAll(" ", "+");

        String geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                cleanedQuery + "&key=" + Constants.GOOGLE_API_KEY;

        Methods.preserveMessages();

        try {
            // Obtain the JSON file for geocodingUrl from Google Geocoding API
            String searchResultsJsonData = Methods.readUrlContent(geocodingUrl);

            /*
             {
             ✅"results" :
               [
                  {
                     "address_components" : [...],
                     "formatted_address" : "2001 Carroll Dr, Blacksburg, VA 24060, USA",
                   ✅"geometry" : {
                        "bounds" : {...},
                      ✅"location" : {
                         ✅"lat" : 37.25518630000001,
                         ✅"lng" : -80.4183295
                        },
                        "location_type" : "ROOFTOP",
                        "viewport" : {...}
                     },
                     "place_id" : "ChIJMfS-09a_TYgR_cWtDLRormY",
                     "types" : [ "premise" ]
                  }
               ],
               "status" : "OK"
             }
             */

            // Create a new JSON object from the returned file
            JSONObject searchResultsJsonObject = new JSONObject(searchResultsJsonData);
            JSONArray resultsJsonArray = searchResultsJsonObject.getJSONArray("results");
            JSONObject aJsonObject = resultsJsonArray.getJSONObject(0);
            JSONObject geometryJsonObject = aJsonObject.getJSONObject("geometry");
            JSONObject locationJsonObject = geometryJsonObject.getJSONObject("location");

            locationLatDouble = locationJsonObject.optDouble("lat", 0.0);
            locationLngDouble = locationJsonObject.optDouble("lng", 0.0);

            locationLatString = String.valueOf(locationLatDouble);
            locationLngString = String.valueOf(locationLngDouble);

        } catch (Exception ex) {
            Methods.showMessage("Information", "Unable to Geocode!",
                    "Google Geocoding API was unable to geocode the given query!");
        }
    }

    /*
    ******************************
    Show Building Information Page
    ******************************
     */
    public String displayBuildingInformation() {

        if (selected == null) {
            return "";
        }
        return "/vtBuilding/Information?faces-redirect=true";
    }

    /*
    ***************************************
    Set travel mode and determine start and 
    destination geolocations for directions
    ***************************************
     */
    public String createStartAndDestinationPoints(int mode) {

        Methods.preserveMessages();

        // Set the travel mode according to the mode selected in Directions.xhtml
        switch (mode) {
            case 0:
                travelMode = "WALKING";
                travelModeCapitalized = "Walking";
                break;
            case 1:
                travelMode = "DRIVING";
                travelModeCapitalized = "Driving";
                break;
            case 2:
                travelMode = "BICYCLING";
                travelModeCapitalized = "Bycycling";
                break;
            default:
                travelMode = "TRANSIT";
                travelModeCapitalized = "Transit";
                break;
        }

        // Remove building selection if any
        selected = null;

        /*
        ------------------------------------------------------------------------
        Starting building geolocation determination for directions to START with 
        ------------------------------------------------------------------------
         */
        try {
            // Replace all occurrences of space with %20 since URL cannot have spaces
            String cleanedBuildingName = startingBuildingName.replaceAll(" ", "%20");

            // Building name "Lane Stadium / Worsham Field" contains a slash unacceptable in a URL
            if (cleanedBuildingName.contains("/")) {
                // Use the substring before the slash as the name to search
                int slashIndex = cleanedBuildingName.indexOf("/");
                cleanedBuildingName = cleanedBuildingName.substring(0, slashIndex);
            }

            String apiUrl = vtBuildingsAPIbaseURL + "nameContains/" + cleanedBuildingName;
            String jsonData = Methods.readUrlContent(apiUrl);
            /*
            https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api/vtBuildings/nameContains/McBryde%20Hall
            returns
            [
            {"abbreviation":"MCB","address":"225 Stanger St.","category":"Academic","description":"McBryde Hall ...",
            "id":61,"imageFilename":"McBryde","latitude":37.2306200000,"longitude":-80.4217800000,"name":"McBryde Hall","yearBuilt":1972}
            ]
             */

            // Convert the JSON data into a JSON array
            JSONArray jsonArrayOfBuildings = new JSONArray(jsonData);

            // Obtain the first and only JSON object for the found building
            JSONObject buildingJsonObject = jsonArrayOfBuildings.getJSONObject(0);

            startingBuildingLat = buildingJsonObject.optDouble("latitude", 0);
            startingBuildingLng = buildingJsonObject.optDouble("longitude", 0);

        } catch (Exception ex) {
            Methods.showMessage("Warning", "Directions Unavailable!",
                    "Google API is unable to compute the requested directions!");
        }

        /*
        -------------------------------------------------------------------------
        Destination building geolocation determination for directions to END with 
        -------------------------------------------------------------------------
         */
        try {
            // Replace all occurrences of space with %20 since URL cannot have spaces
            String cleanedBuildingName = destinationBuildingName.replaceAll(" ", "%20");

            // Building name "Lane Stadium / Worsham Field" contains a slash unacceptable in a URL
            if (cleanedBuildingName.contains("/")) {
                // Use the substring before the slash as the name to search
                int slashIndex = cleanedBuildingName.indexOf("/");
                cleanedBuildingName = cleanedBuildingName.substring(0, slashIndex);
            }

            String apiUrl = vtBuildingsAPIbaseURL + "nameContains/" + cleanedBuildingName;
            String jsonData = Methods.readUrlContent(apiUrl);

            // Convert the JSON data into a JSON array
            JSONArray jsonArrayOfBuildings = new JSONArray(jsonData);

            // Obtain the first and only JSON object for the found building
            JSONObject buildingJsonObject = jsonArrayOfBuildings.getJSONObject(0);

            destinationBuildingLat = buildingJsonObject.optDouble("latitude", 0);
            destinationBuildingLng = buildingJsonObject.optDouble("longitude", 0);

        } catch (Exception ex) {
            Methods.showMessage("Warning", "Directions Unavailable!",
                    "Google API is unable to compute the requested directions!");
        }

        return "/vtBuilding/DirectionsOnMap?faces-redirect=true";
    }

    /*
     ********************************************
     *   Display the SearchResults.xhtml Page   *
     ********************************************
     */
    public String search(Integer type) {
        // Set search type given as input parameter
        searchType = type;

        // Unselect previously selected company if any before showing the search results
        selected = null;

        // Invalidate list of VT buildings found to trigger re-query.
        vtBuildingsFound = null;

        return "/vtSearch/SearchResults?faces-redirect=true";
    }

    /*
     ****************************************************************************************************
     *   Return the list of object references of all those buildings that satisfy the search criteria   *
     ****************************************************************************************************
     */
    public List<Building> getVtBuildingsFound() {
        /*
        =============================================================================================
        You must construct and return the search results List "searchItems" ONLY IF the List is null.
        Any List provided to p:dataTable to display must be returned ONLY IF the List is null
        ===> in order for the column-sort to work. <===
        =============================================================================================
         */
        if (vtBuildingsFound == null) {

            vtBuildingsFound = new ArrayList<>();
            String apiUrl = "";

            switch (searchType) {
                case 1: // Search Type 1: Find VT buildings whose names contain nameQ
                    apiUrl = vtBuildingsAPIbaseURL + "nameContains/" + nameQ;
                    break;
                case 2: // Search Type 2: Find VT buildings whose descriptions contain descriptionQ
                    apiUrl = vtBuildingsAPIbaseURL + "descriptionContains/" + descriptionQ;
                    break;
                case 3: // Search Type 3: Find VT buildings whose abbreviations abbreviationQ
                    apiUrl = vtBuildingsAPIbaseURL + "abbreviationContains/" + abbreviationQ;
                    break;
                case 4: // Search Type 4: Find VT buildings whose categories contain categoryQ
                    apiUrl = vtBuildingsAPIbaseURL + "categoryContains/" + categoryQ;
                    break;
                case 5: // Search Type 5: Find VT buildings whose addresses contain addressQ
                    apiUrl = vtBuildingsAPIbaseURL + "addressContains/" + addressQ;
                    break;
                case 6: // Search Type 6: Find VT buildings built in year yearBuiltQ
                    apiUrl = vtBuildingsAPIbaseURL + "yearBuilt/" + yearBuiltQ;
                    break;
                case 7: // Search Type 7: Find VT buildings built between yearBuiltFromQ and yearBuiltToQ inclusive
                    apiUrl = vtBuildingsAPIbaseURL + yearBuiltFromQ + "/" + yearBuiltToQ;
                    break;
                case 8: // Search Type 8: Find VT buildings built between yearBuiltFromQ and yearBuiltToQ inclusive
                        // AND whose categories contain categoryQ
                    apiUrl = vtBuildingsAPIbaseURL + yearBuiltFromQ + "/" + yearBuiltToQ + "/" + categoryQ;
                    break;
                case 9: // Search Type 9: Find VT buildings whose name OR description OR category OR address contains eachContainsQ
                    apiUrl = vtBuildingsAPIbaseURL + "eachContains/" + eachContainsQ;
                    break;
                default:
                    Methods.showMessage("Fatal Error", "Search Type is Out of Range!",
                            "");
            }

            try {
                // Obtain the JSON data file as a String from the API URL
                String jsonData = Methods.readUrlContent(apiUrl);

                //---------------------------------------------------------------------------------
                // Found VT buildings JSON data is used in map.js to display their locations on map
                //---------------------------------------------------------------------------------
                setFoundVTBuildingsJsonData(jsonData);

                // Convert the JSON data into a JSON array
                JSONArray jsonArrayOfBuildings = new JSONArray(jsonData);

                // Iterate over the JSON array containing VT building JSON objects
                jsonArrayOfBuildings.forEach(building -> {

                    // Typecast the building as a JSON object
                    JSONObject buildingJsonObject = (JSONObject) building;

                    //-------------------
                    // Building id Number
                    //-------------------
                    Long id = buildingJsonObject.optLong("id", 0);
                    // 0 value implies "Unavailable"

                    //--------------
                    // Building Name
                    //--------------
                    String name = buildingJsonObject.optString("name", "Unavailable");

                    //---------------------------
                    // Building Name Abbreviation
                    //---------------------------
                    String abbreviation = buildingJsonObject.optString("abbreviation", "Unavailable");

                    //------------------
                    // Building Category
                    //------------------
                    String category = buildingJsonObject.optString("category", "Unavailable");

                    //---------------------
                    // Building Description
                    //---------------------
                    String description = buildingJsonObject.optString("description", "Unavailable");

                    //--------------------
                    // Building Year Built
                    //--------------------
                    Integer yearBuilt = buildingJsonObject.optInt("yearBuilt", 0);
                    // 0 value implies "Unavailable"

                    //-----------------
                    // Building Address
                    //-----------------
                    String address = buildingJsonObject.optString("address", "Unavailable");

                    //------------------
                    // Building Latitude
                    //------------------
                    double latitudeDouble = buildingJsonObject.optDouble("latitude", 0);
                    // 0 value implies "Unavailable"
                    BigDecimal latitude = BigDecimal.valueOf(latitudeDouble);

                    //-------------------
                    // Building Longitude
                    //-------------------
                    double longitudeDouble = buildingJsonObject.optDouble("longitude", 0);
                    // 0 value implies "Unavailable"
                    BigDecimal longitude = BigDecimal.valueOf(longitudeDouble);

                    //------------------------
                    // Building Image Filename
                    //------------------------
                    String imageFilename = buildingJsonObject.optString("imageFilename", "Unavailable");

                    // Create a new building object dressed up with the values obtained from the JSON object
                    Building bulding = new Building(id, name, abbreviation, category, description, yearBuilt, address, latitude, longitude, imageFilename);

                    // Add the newly created building object to the list of VT buildings found
                    vtBuildingsFound.add(bulding);
                });

                // Sort the List of Building objects with respect to Name property in alphabetical order
                vtBuildingsFound.sort(Comparator.comparing(Building::getName));

            } catch (Exception ex) {
                Methods.showMessage("Fatal Error", "Something went wrong while accessing VTBuildingsAPI RESTful web services!",
                        "See: " + ex.getMessage());
            }
        }

        return vtBuildingsFound;
    }

}
