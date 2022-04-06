package com.mimimiii.application;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimimiii.battleTools.database.DatabaseService;
import com.mimimiii.battleTools.server.*;
import com.mimimiii.battleTools.user.User;

import java.sql.SQLException;

public class Procedure implements ServerApp {


    @Override
    public Response handleRequest(Request request) {
        // return null;

        //  System.out.println(request);
        return choosePath(request);


        //new Response(HttpStatus.OK, ContentType.JSON, request.getBody());
    }

    public Response choosePath(Request request) {
        Response response = new Response();

        switch (request.getPathname()) {
            case "/users":
                System.out.println("/users");
                return new Response(); // ._();
            //   return DatabaseService.getInstance(request);

// .getPathname


            case "/sessions":

            case "/packages":

            case "/transactions":


            case "/transactions/packages":

            case "/cards":

            case "/deck":

// deck?format=plain

// echo 17) battle
//start /b "kienboec battle" curl -X POST http://localhost:10001/battles --header "Authorization: Basic kienboec-mtcgToken"
//start /b "altenhof battle" curl -X POST http://localhost:10001/battles --header "Authorization: Basic altenhof-mtcgToken"
//ping localhost -n 10 >NUL 2>NUL

            case "/user/kienboec":


            case "/users/altenhof":


            case "/users/someGuy":


            case "/stats":


            case "/score":

            case "/tradings":

                // default:

        }


        return response;
    }

    /**
     * creates User in db
     *
     * @param request containing user data as json-string
     * @param db      DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response createUser(Request request, DatabaseService db) {
        User user;
        Response response = new Response();
        // roto to string // en Request  public String toString() { set !
// public abstract class Enum<E extends Enum<E>>
        if (request.getCrud_Method().compareTo("POST") != 0) {
            return get501Response();
        }
        try {
            user = castJsonToUser(request.getContent());
            db.createUser(user);
            response.setStatus(HttpStatus.OK);
            response.setContent("/users");
        } catch (SQLException e) {
            printException(e);
            response.setStatus(HttpStatus.SQL_ERROR);
            response.setContent("huhu");
        }
        return response;
    }

    private User castJsonToUser(Object content) {


    }

    //
    /// } catch (JsonProcessingException e) {
    //            printException(e);
    //            return get500Response();
    ////
    ///***

    // en test !!
    private void printException(JsonProcessingException e) {
    }
// en bug --------------------------------------encima -----------------



    private Response get500Response() {



    }


    /**
     * deserializes a json into a user
     * @param json to decode
     * @return returns a User
     * @throws JsonProcessingException cause readTree needs!
     */
    private User castJsonToUser(String  json) throws JsonProcessingException {

        JsonFactory factory= new JsonFactory();
        ObjectMapper mapper= new ObjectMapper(factory);
        User user= new User();
        JsonNode node=mapper.readTree(json);
        user.setUsername(node.get("Username").toString().replace("\"", ""));
        user.setPassword(node.get("Password").toString().replace("\"", ""));
        return user;

    }

    private Response get501Response() {
    }


    /**
     * prints an exception
     *
     * @param e exception to be printed
     */
    private void printException(SQLException e) {
        //e.printStackTrace();
        System.out.println(e.getMessage());
    }


}
