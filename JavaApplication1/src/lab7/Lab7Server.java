
package lab7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author besweeney
 */
public class Lab7Server extends Thread {
    
        boolean isRunning = true;
        
        Lab7Server(){
            
        }
        
        @Override
        public void run(){
            
            System.out.println("Starting the Server");
            
            try {
                
                ServerSocket serv = new ServerSocket(25567);
                while(isRunning)
                {
                    Socket clSocket = serv.accept();
                    Lab7ClientHandler client = new Lab7ClientHandler(this, clSocket);
                    client.start();
                            
                }
                
            }
            catch (IOException e) 
            { 
                
                System.out.println("THE SERVER IS BROKE");
                
            }
            System.out.println("THE SERVER IS STOPPED");
        }
        
        public static void main(String[] args){
            new Lab7Server().start();
        }
    
}
