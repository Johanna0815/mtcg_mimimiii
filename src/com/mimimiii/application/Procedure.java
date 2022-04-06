package com.mimimiii.application;

import com.mimimiii.battleTools.database.DatabaseService;
import com.mimimiii.battleTools.server.*;

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


}
