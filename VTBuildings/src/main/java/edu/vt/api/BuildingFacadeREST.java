/*
 * Created by Osman Balci on 2025.10.16
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.api;

import edu.vt.entity.Building;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless

/*
 This adds vtBuildings to the first part of the request URL as
 https://venus.cs.vt.edu/VTBuildingsAPI-Balci/api/vtBuildings/
 */
@Path("vtBuildings")

public class BuildingFacadeREST extends AbstractFacade<Building> {
    /*
    ---------------------------------------------------------------------------------------------
    The EntityManager is an API that enables database CRUD (Create Read Update Delete) operations
    and complex database searches. An EntityManager instance is created to manage entities
    that are defined by a persistence unit. The @PersistenceContext annotation below associates
    the entityManager instance with the persistence unitName identified below.
    ---------------------------------------------------------------------------------------------
     */
    @PersistenceContext(unitName = "VTBuildingsAPI-TerrazasPU")
    private EntityManager entityManager;

    // Obtain the object reference of the EntityManager instance in charge of
    // managing the entities in the persistence context identified above.
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /* 
    This constructor method invokes its parent AbstractFacade's constructor method,
    which in turn initializes its entity class type T and entityClass instance variable.
     */
    public BuildingFacadeREST() {
        super(Building.class);
    }

    /*
    =======================================================================
    Our JAX-RS Web Services will be providing data only in the JSON format.
    Therefore, MediaType.APPLICATION_XML is deleted from the methods below. 
    =======================================================================
    
    // We do not allow CREATE operation
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Building entity) {
        super.create(entity);
    }

    // We do not allow EDIT operation
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Building entity) {
        super.edit(entity);
    }

    // We do not allow DELETE operation
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    */

    /*
    ****************************************************************************
    *   Jakarta Persistence Query Formulation for Searching a MySQL Database   *
    ****************************************************************************
    By default, MySQL does not distinguish between upper and lower case letters in searches.
    Therefore, searches based on the queries below are all case insensitive by default.

    The LIKE Expression
        SELECT c FROM Company c WHERE c.name LIKE :'gen%'       All companies whose names begin with "gen"
        SELECT c FROM Company c WHERE c.name LIKE :'%tion'      All companies whose names end with "tion"
        SELECT c FROM Company c WHERE c.name LIKE :'%com%'      All companies whose names contain "com"

    The LIKE expression uses wildcard character % to search for strings that match the wildcard pattern.
    */

    /*
    ---------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/50
    Returns a JSON file containing the VT building whose Primary Key (id) is 50
    ---------------------------------------------------------------------------
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Building find(@PathParam("id") Integer id) {
        // Super class AbstractFacade provides the find method
        return super.find(id);
    }

    /*
    -----------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/getAll
    Returns a JSON file containing all of the VT buildings
    -----------------------------------------------------------------
     */
    @GET
    @Path("getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findAllBuildings() {
        // Super class AbstractFacade provides the findAll method
        return super.findAll();
    }

    /*
    -------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/count
    Returns a JSON file containing the total number of VT buildings in the database
    -------------------------------------------------------------------------------
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String numberOfBuildings() {
        // Super class AbstractFacade provides the count method
        return String.valueOf(super.count());
    }

    /*
    ----------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/names
    Returns a JSON file containing the names of all VT buildings
    ----------------------------------------------------------------
     */
    @GET
    @Path("names")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAllBuildingNames() {

        // Get all building names from the database in the buildingNames list
        List<String> buildingNames = getEntityManager().createQuery("SELECT b.name FROM Building b").getResultList();

        // Create an empty JSON array
        JSONArray arrayOfJsonObjects = new JSONArray();

        // Loop over each building name returned from the database in the buildingNames list
        for (String nameFromDB : buildingNames) {

            // Instantiate a new JSON object
            JSONObject jsonObject = new JSONObject();

            // Set "name" as the KEY and the name returned from the database as the VALUE
            // e.g., set {"name":"McBryde Hall"} as the JSON object
            jsonObject.put("name", nameFromDB);

            // Add the newly created and dressed up JSON object into the arrayOfJsonObjects
            arrayOfJsonObjects.put(jsonObject);
        }

        // Return the String representation of the arrayOfJsonObjects
        return arrayOfJsonObjects.toString();
    }

    /*
    -------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/abbreviations
    Returns a JSON file containing the abbreviations of all VT building names
    -------------------------------------------------------------------------
     */
    @GET
    @Path("abbreviations")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAllBuildingAbbreviations() {

        // Get all building abbreviations from the database in the buildingAbbreviations list
        List<String> buildingAbbreviations = getEntityManager().createQuery("SELECT b.abbreviation FROM Building b").getResultList();

        // Create an empty JSON array
        JSONArray arrayOfJsonObjects = new JSONArray();

        // Loop over each building abbreviation returned from the database in the buildingAbbreviations list
        for (String abbreviationFromDB : buildingAbbreviations) {

            // Instantiate a new JSON object
            JSONObject jsonObject = new JSONObject();

            // Set "abbreviation" as the KEY and the abbreviation returned from the database as the VALUE
            // e.g., set {"abbreviation":"MCB"} as the JSON object
            jsonObject.put("abbreviation", abbreviationFromDB);

            // Add the newly created and dressed up JSON object into the arrayOfJsonObjects
            arrayOfJsonObjects.put(jsonObject);
        }

        // Return the String representation of the arrayOfJsonObjects
        return arrayOfJsonObjects.toString();
    }

    /*
    ---------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/categories
    Returns a JSON file containing the categories of all VT buildings
    ---------------------------------------------------------------------
     */
    @GET
    @Path("categories")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAllCategories() {

        // Get all building categories from the database in the buildingCategories list,
        // which will contain repeated category names.
        List<String> buildingCategories = getEntityManager().createQuery("SELECT b.category FROM Building b").getResultList();

        // Instantiate a new HashSet object, which does not allow repeated values
        Set<String> hashSet = new HashSet<>();

        // Transfer the buildingCategories list into the hashSet list by removing duplicate names
        hashSet.addAll(buildingCategories);

        // Empty the buildingCategories list
        buildingCategories.clear();

        // Transfer the hashSet list with no repeated category names back into the buildingCategories list
        buildingCategories.addAll(hashSet);

        // Create an empty JSON array
        JSONArray arrayOfJsonObjects = new JSONArray();

        // Loop over each category name returned from the database in the buildingCategories list
        for (String categoryFromDB : buildingCategories) {

            // Instantiate a new JSON object
            JSONObject jsonObject = new JSONObject();

            // Set "category" as the KEY and the category returned from the database as the VALUE
            // e.g., set {"category":"Academic"} as the JSON object
            jsonObject.put("category", categoryFromDB);

            // Add the newly created and dressed up JSON object into the arrayOfJsonObjects
            arrayOfJsonObjects.put(jsonObject);
        }

        // Return the String representation of the arrayOfJsonObjects
        return arrayOfJsonObjects.toString();
    }

    /*
    -----------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/nameContains/Mc
    Returns a JSON file listing all VT buildings whose name contains the query Mc
    -----------------------------------------------------------------------------
     */
    @GET
    @Path("/nameContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByNameContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.name LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    -----------------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/descriptionContains/science
    Returns a JSON file listing all VT buildings whose description contains the query science
    -----------------------------------------------------------------------------------------
     */
    @GET
    @Path("/descriptionContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByDescriptionContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.description LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    -------------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/abbreviationContains/MC
    Returns a JSON file listing all VT buildings whose abbreviation contains the query MC
    -------------------------------------------------------------------------------------
     */
    @GET
    @Path("/abbreviationContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByAbbreviationContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.abbreviation LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    -----------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/categoryContains/Acad
    Returns a JSON file listing all VT buildings whose category contains the query Acad
    -----------------------------------------------------------------------------------
     */
    @GET
    @Path("/categoryContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByCategoryContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.category LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    ----------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/addressContains/Stan
    Returns a JSON file listing all VT buildings whose address contains the query Stan
    ----------------------------------------------------------------------------------
     */
    @GET
    @Path("/addressContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByAddressContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.address LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    -------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/yearBuilt/1995
    Returns a JSON file listing all VT buildings built in the query year 1995
    -------------------------------------------------------------------------
     */
    @GET
    @Path("/yearBuilt/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByYearBuilt(@PathParam("query") Integer query) {

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.yearBuilt = :query")
                .setParameter("query", query)
                .getResultList();
    }

    /*
    ----------------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/1990/2000
    Returns a JSON file listing all VT buildings built between 1990 and 2000 inclusive
    ----------------------------------------------------------------------------------
     */
    @GET
    @Path("{yearFrom}/{yearTo}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByYearBuiltRange(@PathParam("yearFrom") Integer yearFrom, @PathParam("yearTo") Integer yearTo) {

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.yearBuilt >= :yearFrom AND b.yearBuilt <= :yearTo")
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .getResultList();
    }

    /*
    -------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/2010/2020/acad
    Returns a JSON file listing all VT buildings built between
    2010 and 2020 inclusive and category contains the query acad
    -------------------------------------------------------------------------
     */
    @GET
    @Path("{yearFrom}/{yearTo}/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByYearBuiltRangeAndCategory(
            @PathParam("yearFrom") Integer yearFrom,
            @PathParam("yearTo") Integer yearTo,
            @PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.yearBuilt >= :yearFrom AND b.yearBuilt <= :yearTo AND b.category LIKE :query")
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .setParameter("query", query)
                .getResultList();
    }

    /*
    ----------------------------------------------------------------------------
    Request URL serverURL/VTBuildingsAPI-Balci/api/vtBuildings/eachContains/lane
    Returns a JSON file listing all VT buildings whose
    name OR description OR category OR address contains the query lane
    ----------------------------------------------------------------------------
     */
    @GET
    @Path("/eachContains/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Building> findByEachContaining(@PathParam("query") String query) {

        query = "%" + query + "%";

        return getEntityManager().createQuery(
                "SELECT b FROM Building b WHERE b.name LIKE :query OR b.description LIKE :query OR b.category LIKE :query OR b.address LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }

}
