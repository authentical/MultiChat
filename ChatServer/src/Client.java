import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/* I think I can see now that the MessageProcessor List should contain
Client objects.... but maybe not because where am I going to store chat history.
I could just put a MessageProcessor List in Main... and just
put new Clients in there.
Or maybe I should keep the MessageProcessor and just add a List of Clients in
Main anyway...
*/


public class Client extends Thread{

    private Socket socket;
    Random rand = new Random();
    private String username = String.valueOf(rand.nextInt());
    private MessageProcessor messageProcessor;  // Shared


    // CONSTRUCTOR ///////////////////////////////////////////////////////////
    public Client(Socket socket, MessageProcessor messageProcessor){
        this.socket = socket;
        // Client receives shared messageProcessor
        this.messageProcessor = messageProcessor;
    }


    // RUN() ////////////////////////////////////////////////////////////////
    @Override
    public void run(){
        try{

            // Each client has it's own input
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // Each client has it's own output
            // All received input will be echoed ONCE to PrintWriter
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);


            // Add the Client to the MessageProcessor user List
            messageProcessor.addUsername(username);

            while(true){

                // Read message from socket and check for "exit"
                String messageIn = input.readLine();
                if(messageIn.equals("exit")){
                    break;
                } else if(messageIn == null){
                    messageIn = "";
                }

                // Add message to MessageProcessor List
                messageProcessor.addNewMessage(username, messageIn);

                // Write new messages to socket
                output.println(messageProcessor.getNewMessages(username));
            }


        }catch(IOException e ){
            System.out.println("Client disconnected");
        }finally{
            try{
                socket.close(); // Close socket on exit
            }catch(IOException e){e.printStackTrace();}
        }

    }

}






























