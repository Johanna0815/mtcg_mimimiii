package com.mimimiii.battleTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mimimiii.battleTools.user.User;

// no necesito
public class CreateBattleManager {


    private static CreateBattleManager single_instance = null;

    private User user1;
    private User user2;
    private String response;
    private boolean working = false;
    final Object LOCK = new Object();

    public static CreateBattleManager getInstance() {
        if (single_instance == null) {
            single_instance = new CreateBattleManager();
        }
        return single_instance;
    }


    /*
    public String addUser(User user){
        if (user1 == null){
            user1 = user;
            response = null;
            working = true;
            synchronized (LOCK) {
                while (working) {
                    try { LOCK.wait(); }
                    catch (InterruptedException e) {
                        // treat interrupt as exit request
                        break;
                    }
                }
            }
            return response;
        } else if (user2 == null){
            user2 = user;
            CardManager manager = new CardManager();
            Deck deck1 = manager.getDeckUser(user1);
            Deck deck2 = manager.getDeckUser(user2);
            response = battle(user1,user2,deck1,deck2);
            working = false;
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
            user1 = null;
            user2 = null;
            return response;
        }
        return null;
    }

     */

    public String battle(User user1, User user2, Deck deck1, Deck deck2) {
        if (user1 == null || user2 == null || deck1 == null || deck2 == null) {
            this.user1 = null;
            this.user2 = null;
            this.response = null;
            return null;
        }
        int turns = 0;


        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        String log;

        return null;



                /*
                float damage1 = 30;
                float damage2 = 15;
                if (damage1 > damage2){
                    deck2.removeCard(card2);
                    deck1.putSingleCard(card2);
                    round.put("Won",user1.getName());
                } else if (damage1 < damage2){
                    deck2.putSingleCard(card1);
                    deck1.removeCard(card1);
                    round.put("Won",user2.getName());
                } else {
                    round.put("Won","Draw");
                }
                round.put("DeckSizeAfter_1",deck1.getSize());
                round.put("DeckSizeAfter_2",deck2.getSize());
                arrayNode.add(round);
            }

                 */
            log = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            if (deck1.isEmpty()) {
                user1.battleLoost();
                user2.battleWon();
                return log;
            } else if (deck2.isEmpty()) {
                user1.battleWon();
                user2.battleLoost();
                return log;
            }
            user1.battleRemis();
            user2.battleRemis();
            return log;

            // bug -------------------------------------------------------------------------------

        }


    }