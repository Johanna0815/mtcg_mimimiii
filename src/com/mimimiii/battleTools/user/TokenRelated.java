package com.mimimiii.battleTools.user;

public class TokenRelated {
    public static String getAdminToken(){
        return "adminToken";
    }

    //CURL adaptar !  -mtcgToken
    public static String getUserToken(String username){
        return (username+"-Token");
    }
}
