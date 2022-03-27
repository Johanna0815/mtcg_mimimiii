package com.mimimiii.application;

import com.mimimiii.battleTools.server.Request;
import com.mimimiii.battleTools.server.Response;
import com.mimimiii.battleTools.server.ServerApp;

public class MTCG implements ServerApp {


    @Override
    public Response handleRequest(Request request) {
       // return null;

        System.out.println(request);
        return null;
    }
}
