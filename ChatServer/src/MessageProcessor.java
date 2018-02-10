// By Chris Spencer 2018

import java.util.ArrayList;
import java.util.List;

/*
    MessageProcessor has a List of User objects containing:
    1. Username
    2. A List of Messages sent by the user
    3. A Map of username/messages the user has not yet received (including their own)

    It routes new messages from each user
    1. By way of each Client invoking getUnsent
    2. Into server log
 */


public class MessageProcessor {

    // A list of User objects
    // A User is a username, messages sent by the user, an array of messages the user
    // has not yet received.
    protected List<User> users = new ArrayList<>();
    // A list of "username + message"
    private List<String> serverLog = new ArrayList<>();


    public class User {

        private String username;
        private List<String> messagesIn;
        private List<String> unsent;    // This should be a map so it can store username/message

        public User(String username) {
            this.username = username;
            this.messagesIn = new ArrayList<>();
            this.unsent = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getMessagesIn() {
            return messagesIn;
        }

        public void addMessagesIn(String newMessage) {
            messagesIn.add(newMessage);
        }

        public List<String> getUnsent() {
            return unsent;
        }

        public void addUnsent(String newMessage) {
            unsent.add(newMessage);
        }
    }



    public void addUsername(String username){

        User newuser = new User(username);
        newuser.setUsername(username);
        users.add(newuser);
    }

    public void addNewMessage(String username, String newMessage){

        for(User user : users) {
            if(user.getUsername().equals(username)){
                user.addMessagesIn(newMessage);
            }
            user.addUnsent(newMessage);
        }
        serverLog.add(username + " " + newMessage);
    }

    public String getNewMessages(String username){

        String newMessages="";

        for(User user : users){
            if(user.username.equals(username)){
                for(String unsentMessage : user.getUnsent()){
                    newMessages += username + ": " + unsentMessage + "\n";
                }
                user.getUnsent().clear();
            }
        }

        return newMessages;
    }
}
