package com.mimimiii.battleTools.database;

import com.mimimiii.battleTools.battle.Card;
import com.mimimiii.battleTools.battle.Deck;
import com.mimimiii.battleTools.elementTypes.CardType;
import com.mimimiii.battleTools.elementTypes.StringToEnumConverter;
import com.mimimiii.battleTools.scoreBoardTools.ScoreBoard;
import com.mimimiii.battleTools.scoreBoardTools.ScoreRelated;
import com.mimimiii.battleTools.user.Profile;
import com.mimimiii.battleTools.user.Token;
import com.mimimiii.battleTools.user.TokenRelated;
import com.mimimiii.battleTools.user.User;

import java.sql.*;

public class DatabaseService {
    private Connection connection;

    // mtcg_mimimiii
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "swe1user";
    private static final String PASSWORD = "swe1pw";

    private static DatabaseService instance;

    // private --- again afterwards ?
    public DatabaseService() {
    }

    public void connect() throws SQLException{
        this.connection= DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }


    public void disconnect() throws SQLException{
        this.connection.close();
    }


    //    Method to obtain an object.
    public static DatabaseService getInstance() {
        if (DatabaseService.instance == null) {
            DatabaseService.instance = new DatabaseService();
        }
        return DatabaseService.instance;
    }


    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void createUser(User user) throws SQLException {
        PreparedStatement ps;
        String stackName = TablesPrepared.getUserStackTableName(user.getUsername());
        String deckName = TablesPrepared.getDeckTableName(user.getUsername());
        ps = connection.prepareStatement("INSERT INTO " + TablesPrepared.getUserListTableName() + " (username, password, collection, token) VALUES (?,?,?,?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, stackName);
        ps.setString(4, TokenRelated.getUserToken(user.getUsername()));
        ps.executeUpdate();
        createUserStackTable(stackName, "-s");
        createUserStackTable(deckName, "-d");
    }




    public void createUserStackTable(String stackName, String options) throws SQLException{
        PreparedStatement ps=connection.prepareStatement("");
        switch (options){
            case "-s":
                ps=connection.prepareStatement("CREATE TABLE "+ stackName +" (uid varchar(255), amount int default 1)");
                break;
            case "-d":
                ps=connection.prepareStatement("CREATE TABLE "+ stackName +" (uid varchar(255));");
        }
        ps.executeUpdate();
        ps=connection.prepareStatement("CREATE UNIQUE INDEX " + stackName + "_uid_uindex ON " + stackName + " (uid);");
        ps.executeUpdate();
    }

    public void createPackageTable(Deck s) throws SQLException{
        PreparedStatement ps;
        ps=connection.prepareStatement("CREATE TABLE "+ TablesPrepared.getPackageTableName(s.getOwner()) +" (uid varchar(255))");
        ps.executeUpdate();
        ps=connection.prepareStatement("CREATE UNIQUE INDEX " + TablesPrepared.getPackageTableName(s.getOwner()) + "_uid_uindex ON " + TablesPrepared.getPackageTableName(s.getOwner()) + " (uid);");
        ps.executeUpdate();

        ps=connection.prepareStatement("INSERT INTO " + TablesPrepared.getPackageListTableName() + "(name) VALUES (?);");
        ps.setString(1, TablesPrepared.getPackageTableName(s.getOwner()));
        ps.executeUpdate();

        addCardsToCardListTable(s);

        for(Card b : s.getDeckList()){
            addCardToTable(b.getId(), s.getOwner());
        }
    }

    public boolean addCardToUserTable(String uid, String username) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        Card card= getCard(uid);
        if(card==null){
            return false;
        }
        String table= TablesPrepared.getUserStackTableName(username);
        ps= connection.prepareStatement("SELECT amount FROM " + table +" WHERE uid =?");
        ps.setString(1, card.getId());
        rs= ps.executeQuery();

        if(rs.next()) {
            int amount= rs.getInt(1);
            amount+=1;

            ps=connection.prepareStatement("UPDATE " + table + " SET amount=?  WHERE uid=?");
            ps.setInt(1, amount);
            ps.setString(2, card.getId());
            ps.executeUpdate();

        }
        else{
            ps=connection.prepareStatement("INSERT INTO " + table + " (uid) VALUES (?)");
            ps.setString(1, card.getId());
            ps.executeUpdate();
        }

        rs.close();
        return true;
    }

    public boolean addListToDeck(String username, Deck l) throws SQLException{
        PreparedStatement ps;
        String stackName=TablesPrepared.getDeckTableName(username);

        ps= connection.prepareStatement("DELETE FROM " + stackName);
        ps.executeUpdate();
        try{
            ps= connection.prepareStatement("INSERT INTO " + stackName + "(uid) VALUES (?);");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }

        try{
            for(int i=0; i<4; i++) {
                ps.setString(1, String.valueOf(l.getDeckList().remove()));
                ps.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }




    public boolean addCardToTable(String id, String name) throws SQLException{
        PreparedStatement ps;
        // bug!
        Card card= getCard(id);
        if(card==null){
            return false;
        }

        ps= connection.prepareStatement("INSERT INTO " + TablesPrepared.getPackageTableName(name) + "(uid) VALUES (?);");
        ps.setString(1, card.getId());
        ps.executeUpdate();
        return true;
    }
    public boolean checkForValue(String field, String table,  String value) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        ps=connection.prepareStatement("SELECT " + field + " FROM " + table + " WHERE " + field + "=?");
        ps.setString(1, TablesPrepared.getPackageTableName(value.toLowerCase()));
        rs=ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public boolean addCardsToCardListTable(Deck d){
        PreparedStatement ps;
        boolean wasIssue=false;
        for(Card b: d.getDeckList()){
            try{
                ps=connection.prepareStatement("INSERT INTO "+ TablesPrepared.getCardListTableName() + " (uid, name, power, element, type) VALUES (?,?,?,?,?)");
                ps.setString(1, b.getId());
                ps.setString(2,b.getName());
                //´cast!!!
                ps.setFloat(3,b.getDamage());
                ps.setString(4, b.getElement().toString());
                ps.setString(5, b.getCardType().toString());
                ps.executeUpdate();
            }catch (SQLException e){
                wasIssue=true;
            }
        }
        return wasIssue;
    }

    public Token tryAuthentication(User user) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        Token token=new Token();
        ps= connection.prepareStatement("SELECT token FROM users WHERE username =? AND password =?");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        rs=ps.executeQuery();
        if(rs.next()){
            token.setToken(rs.getString("token"));
            token.setUsername(user.getUsername());
            rs.close();
            return token;
        }
        rs.close();
        token.setToken("NO_TOKEN");
        return token;
    }
/*
    public Deck getDeckFromTable(String name, String options) throws SQLException{
        String tableName="";
        Deck d= new Deck(name);
        switch(options){
            case "-u":
                tableName=TablesPrepared.getUserStackTableName(name);
                break;
            case "-p":
                tableName=TablesPrepared.getPackageTableName(name);
                break;
            case "-d":
                tableName=TablesPrepared.getDeckTableName(name);
        }
        CardType b;
        PreparedStatement ps;
        ResultSet uids;
        try{
            ps=connection.prepareStatement("SELECT uid FROM " + tableName);
            uids=ps.executeQuery();
            while(uids.next()){
                // bug cardType -- getCaRDtype
                if((b= CardType.DRAGON.id.getString(1)))!=null)
                CardType.WIZZARD,




                        CardType.WATERSPELL,
                        CardType.KNIGHT,
                        CardType.FIREELF,
                        CardType.GOBLIN,
                        CardType.KRAKEN,
                        CardType.Ork.{
                    d.putSingleCard(b);
                }
           }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  d;
    }


 */
    public ScoreBoard getScoreBoard() throws SQLException{
        ScoreBoard sb= new ScoreBoard();
        PreparedStatement ps;
        ResultSet rs;
        ps= connection.prepareStatement("SELECT username, elo FROM "+ TablesPrepared.getUserListTableName() + " ORDER BY name;");
        rs=ps.executeQuery();
        while(rs.next()){
            String username=rs.getString("username");
            int elo=rs.getInt("elo");
            sb.addScore(new ScoreRelated(username, elo));
        }
        rs.close();
        return sb;
    }

    public Card getCard (String uid) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        Card b= new Card();
        ps=connection.prepareStatement("SELECT * FROM " + TablesPrepared.getCardListTableName() + " WHERE uid=?");
        ps.setString(1,uid);
        rs=ps.executeQuery();
        if(rs.next()){
            b.setId(rs.getString(1));
            b.setName(rs.getString(2));
            b.setDamage(rs.getInt(3));
            b.setElement(StringToEnumConverter.getElementType(rs.getString(4)));
            b.setCardType(StringToEnumConverter.getCardType(rs.getString(5)));
            return b;
        }
        return null;
    }
    public boolean changeProfile(String username, Profile profile){
        try{
            PreparedStatement ps= connection.prepareStatement("UPDATE " + TablesPrepared.getUserListTableName() + " SET name=?, bio=?, image=? WHERE username=?;");
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getBio());
            ps.setString(3, profile.getImage());
            ps.setString(4, username);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Profile getProfile(String username){
        Profile profile= new Profile();
        try{
            PreparedStatement ps= connection.prepareStatement("SELECT * FROM " + TablesPrepared.getUserListTableName() + " WHERE  username=?;");
            ps.setString(1, username);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                profile.setUsername(rs.getString("username"));
                profile.setName(rs.getString("name"));
                profile.setBio(rs.getString("bio"));
                profile.setImage(rs.getString("image"));
                profile.setWon(rs.getInt("wins"));
                profile.setLost(rs.getInt("losses"));
                profile.setCoins(rs.getInt("coins"));
                profile.setElo(rs.getInt("elo"));
                rs.close();
                return profile;
            }
            rs.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return new Profile();
    }


    public Token checkToken(String givenToken){
        PreparedStatement ps;
        ResultSet rs;
        Token token= new Token();
        try {
            ps = connection.prepareStatement("SELECT username, token FROM " + TablesPrepared.getUserListTableName() + " WHERE token=?;");
            ps.setString(1, givenToken);
            rs = ps.executeQuery();
            if (rs.next()) {
                token.setUsername(rs.getString("username"));
                token.setToken(rs.getString("token"));
            }
            rs.close();
            return token;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return token;
        }
    }
    public boolean checkAdminToken(String givenToken){
        if(givenToken.compareTo(TokenRelated.getAdminToken())==0){
            return true;
        }
        return false;
    }

    public void changeElo(String username, int eloChange) throws SQLException{
        PreparedStatement ps;
        int updatedElo;

        updatedElo=getElo(username)+eloChange;

        ps= connection.prepareStatement("UPDATE users SET elo=? WHERE username=?");
        ps.setInt(1, updatedElo);
        ps.setString(2, username);
        ps.executeUpdate();

    }

    public void changeCoins(String username, int coinChange) throws SQLException{
        PreparedStatement ps;
        int updatedCoins;

        updatedCoins= getCoins(username) + coinChange;

        ps= connection.prepareStatement("UPDATE users SET coins=? WHERE username=?");
        ps.setInt(1, updatedCoins);
        ps.setString(2, username);
        ps.executeUpdate();

    }

    public int getElo(String username) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        int elo;

        ps=connection.prepareStatement("SELECT elo FROM users WHERE username=?");
        ps.setString(1, username);
        rs=ps.executeQuery();
        rs.next();
        elo= rs.getInt(1);
        rs.close();
        return elo;
    }

    public int getCoins(String username) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        int coins;

        ps=connection.prepareStatement("SELECT coins FROM users WHERE username=?");
        ps.setString(1, username);
        rs=ps.executeQuery();
        rs.next();
        coins= rs.getInt(1);
        rs.close();
        return coins;
    }

}



