package com.mimimiii;

import com.mimimiii.application.MTCG;
import com.mimimiii.battleTools.BattleLogic;
import com.mimimiii.battleTools.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


        Server server = new Server(10002, new MTCG());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
