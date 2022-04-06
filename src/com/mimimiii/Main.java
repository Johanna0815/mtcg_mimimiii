package com.mimimiii;

import com.mimimiii.application.Procedure;
import com.mimimiii.battleTools.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


        Server server = new Server(10001, new Procedure());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
