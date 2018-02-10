import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {

        // The MessageProcessor is shared by all Clients
        MessageProcessor messageProcessor = new MessageProcessor();


        try(ServerSocket serverSocket = new ServerSocket(5000)){

            while(true){
                new Client(serverSocket.accept(), messageProcessor).start();
            }
        }catch(IOException e ){e.printStackTrace();}
    }
}
