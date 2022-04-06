package com.mimimiii.application;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimimiii.battleTools.battle.Card;
import com.mimimiii.battleTools.battle.Deck;
import com.mimimiii.battleTools.database.DatabaseService;
import com.mimimiii.battleTools.database.TablesPrepared;
import com.mimimiii.battleTools.elementTypes.StringToEnumConverter;
import com.mimimiii.battleTools.scoreBoardTools.ScoreBoard;
import com.mimimiii.battleTools.server.*;
import com.mimimiii.battleTools.user.Profile;
import com.mimimiii.battleTools.user.Token;
import com.mimimiii.battleTools.user.User;

import java.sql.SQLException;
import java.util.LinkedList;

public class Procedure implements ServerApp {


    @Override
    public Response handleRequest(Request request) {
        //  System.out.println(request);
        return choosePath(request);

        //new Response(HttpStatus.OK, ContentType.JSON, request.getBody());
    }


    public Response choosePath(Request request) {
        Response response = new Response();
        DatabaseService db = new DatabaseService();
        try {
            db.connect();
        } catch (SQLException e) {
            printException(e);
            return getInternalServerErrorResponse();
        }
        switch (request.getPathname()) {
            case "/users":
                //   System.out.println("/users");
                return createUser(request, db);
            //   return new Response(); // ._();
            //   return DatabaseService.getInstance(request);

// .getPathname

            case "/sessions":
                return authentication(request, db);
        }

        Token token = db.checkToken(request.getAuthorization());
        if (db.checkAdminToken(token.getToken())) {
            switch (request.getPathname()) {
                case "/packages":
                    return createPackage(request, db);
            }
        }
        if (!token.getToken().isEmpty()) {
            switch (request.getPathname()) {
                case "/transactions/packages":
                    return acquirePack(token.getUsername(), request, db);
                case "/cards":
                    return getCollection(token.getUsername(), request, db);
                case "/deck":
                    return accessDeck(token.getUsername(), request, db);
                case "/deck?format=plain":
                    return getPlainDeck(token.getUsername(), request, db);
                case "/score":
                    return getScoreBoard(db);
            }
            if (request.getPathname().length() > 7) {
                if (request.getPathname().substring(0, 7).compareTo("/users/") == 0) {
                    if (token.getUsername().compareTo(request.getPathname().substring(7)) == 0) {
                        return accessProfile(request.getPathname().substring(7), request, db);
                    }
                    return getNotFoundResponse();
                }
            }
        }
        try {
            db.disconnect();
        } catch (SQLException e) {
            printException(e);
            return getInternalServerErrorResponse();
        }
        return getInternalServerErrorResponse();


    }


    /**
     * creates User in db
     *
     * @param request containing user data as json-string
     * @param db      DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response createUser(Request request, DatabaseService db) {
        User user = null;
        Response response = new Response();
        if (request.getMethod().compareTo(Crud_Method.POST) != 0) {
            return getBadRequestResponse();
        }
        try {
            try {
                user = castJsonToUser(request.getContent());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            db.createUser(user);        // bug pq
            response.setStatus(HttpStatus.OK);
            response.setContent("/users");
        } catch (SQLException e) {
            printException(e);
            response.setStatus(HttpStatus.SQL_ERROR);
            response.setContent("huhu");
        }
        return response;
    }


    /**
     * creates a new Package in the DataBase
     *
     * @param request containing package data as json-string
     * @param db      DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response createPackage(Request request, DatabaseService db) {
        Deck s;
        Response response = new Response();
        if (request.getMethod().compareTo(Crud_Method.POST) != 0) {
            return getBadRequestResponse();
        }
        try {
            s = castJsonToStackJson(request.getContent());
            db.createPackageTable(s);
        } catch (JsonProcessingException e) {
            printException(e);
            return getInternalServerErrorResponse();
        } catch (SQLException e) {
            printException(e);
            response.setStatus(HttpStatus.SQL_ERROR);
            response.setContent("ERROR 900 / Package already exist!!");
            return response;
        }
        response.setContent("Packs successfully created!");
        return response;
    }




/*
    private User castJsonToUser(Object content) {
        //change return !
return null;

    }

 */

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


    private Response getInternalServerErrorResponse() {
// change return !
        return null;

    }


    /**
     * retrieves the Scoreboard from the server
     *
     * @param db DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response getScoreBoard(DatabaseService db) {
        Response response = new Response();
        try {
            ScoreBoard sb = db.getScoreBoard();
            response.setContent(serializeObjectToJSON(sb));
            response.setContentType(ContentType.JSON);
            return response;
        } catch (Exception e) {
            printException(e);

        }
        return getInternalServerErrorResponse();
    }


    /**
     * deserializes a json into a user
     *
     * @param json to decode
     * @return returns a User
     * @throws JsonProcessingException cause readTree needs!
     */
    private User castJsonToUser(String json) throws JsonProcessingException {

        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        User user = new User();
        JsonNode node = mapper.readTree(json);
        user.setUsername(node.get("Username").toString().replace("\"", ""));
        user.setPassword(node.get("Password").toString().replace("\"", ""));
        return user;

    }


    /**
     * deserializes a json into a stack
     *
     * @param json json to decode
     * @return returns a Stack
     * @throws JsonProcessingException
     */
    private Deck castJsonToStackJson(String json) throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode node = mapper.readTree(json);
        Deck s = new Deck(node.get(0).toString().replace("\"", "").toLowerCase());
        Card b;
        for (int i = 1; i < node.size(); i++) {
            b = castJSONToCard(node.get(i));
            s.putSingleCard(b);
        }
        return s;
    }


    /**
     * deserializes a json-string into a BaseCard
     *
     * @param node node to be deserialized
     * @return a BaseCard
     */
    private Card castJSONToCard(JsonNode node) {
        Card b = new Card();

        b.setId(node.get("Id").toString().replace("\"", ""));
        b.setName(node.get("Name").toString().replace("\"", ""));
        b.setDamage(Integer.parseInt(node.get("Damage").toString()));
        b.setElement(StringToEnumConverter.getElementType(node.get("Element").toString().replace("\"", "")));
        b.setCardType(StringToEnumConverter.getCardType(node.get("Type").toString().replace("\"", "")));

        return b;
    }


    /**
     * trys to retrieve a token from the Database
     *
     * @param request containing login data as json-string
     * @param db      DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response authentication(Request request, DatabaseService db) {
        User user;
        Token token;
        Response response = new Response();
        //getMethod
        if (request.getCrud_Method().compareTo(Crud_Method.POST) != 0) {
            return getBadRequestResponse();
        }
        try {
            user = castJsonToUser(request.getContent());
            token = db.tryAuthentication(user);
            if (token.getToken().compareTo("NO_TOKEN") != 0) {
                response.setContent(serializeObjectToJSON(token));
                response.setContentType(ContentType.JSON);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setContent("No access!!!");
            }
        } catch (JsonProcessingException e) {
            printException(e);
            return getBadRequestResponse();
        } catch (SQLException e) {
            printException(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setContent("ERROR 500 / not authentication right");
        }
        return response;
    }

    public Response getBadRequestResponse() {
        Response response = new Response();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setContent("ERROR 400 / Bad request !!");
        return response;
    }


    /**
     * serializes an object into a json-string
     *
     * @param obj to serialize
     * @return returns a json-string
     */
    private String serializeObjectToJSON(Object obj) throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        return mapper.writeValueAsString(obj);
    }


// bug ------------------------ adapt !

    /**
     * decodes a json-string into a DeckList and changes its owner to the given username
     *
     * @param json     json-string to deserialize
     * @param username new owner of the DeckList
     * @return returns a DeckList
     * @throws JsonProcessingException
     */
    private Deck decodeJSONToDeckList(String json, String username) throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        Deck l = new Deck(username);
        JsonNode node = mapper.readTree(json);
        for (int i = 0; i < node.size(); i++) {
            // en bug !! -------------------------------------------------
            l.addID(node.get(i).toString().replace("\"", ""));
        }
        return l;
    }


    public Response getNotFoundResponse() {
        Response response = new Response();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setContent("ERROR 404 / Not Found !!");
        return response;
    }

    /**
     * deserializes a json-string into a Profile
     *
     * @param json json-string to decode
     * @return a Profile
     * @throws JsonProcessingException
     */
    private Profile decodeJSONToProfile(String json) throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode node = mapper.readTree(json);
        Profile profile = new Profile();
        profile.setName(node.get("Name").toString().replace("\"", ""));
        profile.setBio(node.get("Bio").toString().replace("\"", ""));
        profile.setImage(node.get("Image").toString().replace("\"", ""));
        return profile;
    }


    /**
     * aquires a pck for the given client
     *
     * @param username client which tries to acquire pack
     * @param request  containing the name of the pack
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response acquirePack(String username, Request request, DatabaseService db) {
        if (request.getMethod().compareTo(Crud_Method.POST) != 0) {
            return getInternalServerErrorResponse();
        }
        String packName = request.getContent();
        Response response = new Response();
        response.setContent("Pack successfully acquired");
        try {
            if (db.getCoins(username) < 5) {
                response.setContent("not enough coins available");
                return response;
            }
        } catch (SQLException e) {
            printException(e);
            return getInternalServerErrorResponse();
        }

        try {
            if (db.checkForValue("name", TablesPrepared.getPackageListTableName(), packName.replace("\"", ""))) {
                Deck s = db.getDeckFromTable(packName.replace("\"", ""), "-p");
                db.changeCoins(username, -5);
                for (Card b : s.getDeckList()) {
                    db.addCardToUserTable(b.getId(), username);
                }
                return response;
            }
            response.setContent("!ERROR 500 / Package not found");
        } catch (SQLException e) {
            printException(e);
            return getInternalServerErrorResponse();
        }
        return response;
    }

    public Response getCollection(String username, Request request, DatabaseService db) {
        if (request.getMethod().compareTo(Crud_Method.GET) != 0) {
            return getInternalServerErrorResponse();
        }
        Response response = new Response();
        try {
            Deck s = db.getDeckFromTable(username, "-u");
            response.setContent(serializeObjectToJSON(s));
            response.setContentType(ContentType.JSON);
            return response;
        } catch (Exception e) {
            printException(e);
            return getInternalServerErrorResponse();
        }
    }


    /**
     * retrieves deck of given user from the Database
     *
     * @param username user whose deck is to be retrieved
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response getDeck(String username, DatabaseService db) {
        Response response = new Response();
        try {
            Deck s = db.getDeckFromTable(username, "-d");
            if (s.deckList().size() == 0) {
                response.setContent("<!DOCTYPE html><html><body><h1>Deck of " + s.getOwner() + " is Empty</h1></body></html>");
                return response;
            }
            response.setContent(serializeObjectToJSON(s));
            return response;
        } catch (Exception e) {
            printException(e);
            return getInternalServerErrorResponse();
        }
    }


    /**
     * changes deck on the Server
     *
     * @param l  DeckList which contains the new deck list
     * @param db DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response configureDeck(Deck l, DatabaseService db) {
        Response response = new Response();
        if (l.getDeckList().size() != 4) {
            return getBadRequestResponse();
        }
        try {
            db.addListToDeck(l.getOwner(), l);
        } catch (SQLException e) {
            printException(e);
            return getBadRequestResponse();
        }
        response.setContent("<!DOCTYPE html><html><body><h1>Deck of " + l.getOwner() + " successfully changed</h1></body></html>");
        return response;
    }


    /**
     * retrieves a simpler version of the Deck from the server
     *
     * @param username user whose DeckList is to be rtrieved
     * @param request  a request containing a Method and a Login-Token
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response getPlainDeck(String username, Request request, DatabaseService db) {
        if (request.getMethod().compareTo(Crud_Method.GET) != 0) {
            return getInternalServerErrorResponse();
        }
        Response response = new Response();
        try {
            Deck s = db.getDeckFromTable(username, "-d");
            LinkedList<String> l = new LinkedList<>();
            for (Card b : s.getDeckList()) {
                l.add(b.getName());
            }
            String json = serializeObjectToJSON(l);
            response.setContent(json);
            response.setContentType(ContentType.JSON);
        } catch (Exception e) {
            printException(e);
            return getInternalServerErrorResponse();
        }

        return response;
    }


    /**
     * retrieves a full Profile of the user form the DataBase
     *
     * @param username user which profile is to be retrieved
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response getProfile(String username, DatabaseService db) {
        Response response = new Response();
        Profile profile = db.getProfile(username);
        if (profile == null) {
            return getBadRequestResponse();
        }
        try {
            response.setContent(serializeObjectToJSON(profile));
            response.setContentType(ContentType.JSON);
            return response;
        } catch (JsonProcessingException e) {
            printException(e);
        }
        return getInternalServerErrorResponse();
    }


    /**
     * changes the profile of a user
     *
     * @param username user whose profile gets changed
     * @param profile  Profile containing the changes to conduct
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response setProfile(String username, Profile profile, DatabaseService db) {
        Response response = new Response();
        if (db.changeProfile(username, profile)) {
            response.setContent("Profile successfully changed");
            return response;
        }
        return getBadRequestResponse();
    }

    /**
     * tries to access a users profile
     *
     * @param username the user of which the profile is to be accessed
     * @param request  request containing method of access and Login-Token
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response accessProfile(String username, Request request, DatabaseService db) {
        switch (request.getMethod()) {
            case PUT:

                try {
                    return setProfile(username, decodeJSONToProfile(request.getContent()), db);
                } catch (JsonProcessingException e) {
                    printException(e);
                    return getBadRequestResponse();
                }

            case GET:
                return getProfile(username, db);
        }

        return getInternalServerErrorResponse();
    }


    /**
     * tries to access a deck of a given user
     *
     * @param username user whose collection
     * @param request  a request containng Method and a Login-Token
     * @param db       DataBaseConnector, which handles all Database interactions
     * @return a Response to be sent to the client
     */
    public Response accessDeck(String username, Request request, DatabaseService db) {
        switch (request.getMethod()) {
            case GET:
                return getDeck(username, db);
            case PUT:
                try {
                    Deck l = decodeJSONToDeckList(request.getContent(), username);
                    return configureDeck(l, db);
                } catch (JsonProcessingException e) {
                    return getInternalServerErrorResponse();
                }
        }
        return getInternalServerErrorResponse();
    }


    /**
     * prints an exception
     *
     * @param e exception to be printed
     */
    public void printException(Exception e) {
        //e.printStackTrace();
        System.out.println(e.getMessage());
    }



}
