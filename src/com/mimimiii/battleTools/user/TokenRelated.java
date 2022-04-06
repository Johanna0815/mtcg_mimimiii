package com.mimimiii.battleTools.user;

public class TokenRelated {
    public static String getAdminToken(){
        // adminToken sol
        return "admin-mtcgToken";
    }

    //CURL adaptar !  -mtcgToken
    public static String getUserToken(String username){
        return (username+"-mtcgToken");
    }
}
