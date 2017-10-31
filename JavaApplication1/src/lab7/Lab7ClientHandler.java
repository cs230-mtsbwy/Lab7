/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author besweeney
 */
public class Lab7ClientHandler extends Thread{
    
    Lab7Server lab7server;
    BufferedReader readIn;
    PrintWriter writeOut;
    
    
    public Lab7ClientHandler(Lab7Server servIn, Socket socketIn){
        
        lab7server = servIn;
        
        try{
            readIn = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
            writeOut = new PrintWriter(socketIn.getOutputStream());
            
        }catch(IOException e){
            System.out.println("Exception in ClientHandler");
        }
        
    }
    
    public void run(){
        
        try{
            String dataIn;
            while ((dataIn = readIn.readLine()) != null) {
                System.err.println(dataIn);
                processData(dataIn);
                
            }
        } catch (IOException e) {
            System.out.println("Exception in ClientHandler:  Client likely Disconnected");
        }
        
    }
    
    public void processData(String dataIn) throws IOException{
    
      
            try {
                if (dataIn.startsWith("/save "))
                {
                    String data = dataIn.substring(6);
                    PrintWriter writeToDatabase = new PrintWriter("Database");
                    String test[];
                    test = data.split("\\$");
                    for (int i = 0; i < test.length; i++)
                    {
                       writeToDatabase.println(test[i]); 
                    }
                    
                    writeToDatabase.flush();
                }
                else if (dataIn.startsWith("/request"))
                {
                    File database = new File("Database");
                    BufferedReader input = new BufferedReader(new FileReader(database));
                    String tempString = "/display";
                    String tempLine;
                    
                    while((tempLine = input.readLine()) != null)
                    {
                        tempString = tempString + tempLine + "$";
                    }
                    
                    writeOut.println(tempString);
                    writeOut.flush();
                }
                
            } catch (FileNotFoundException ex) {
                System.out.println("NoFile");
            }
            
        
        

    }


    
}
