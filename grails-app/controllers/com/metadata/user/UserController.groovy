/**
 *
 * @author abhinavpandey
 */
package com.metadata.user
import grails.converters.JSON
import groovy.sql.Sql
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import RateITUsers.Users
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class UserController {
    def dataSource
    
    def index() {

    }
    
    def userRegisterAndLogin(){
        try{
            def server = startServer()
            System.out.println("Server started.");
            while(true){
                Socket connection = server.accept();
                ObjectInputStream serverIn = new ObjectInputStream(connection.getInputStream());
                def messageIn = []
                messageIn = serverIn.readObject();
                System.out.println("Message received from client: " + messageIn);
                def returnMessage = []

                def msgType = messageIn[0]
                if (msgType == "1"){
                    def chkUsrExist = Users.findByEmail(messageIn[1])
            
                    if(!chkUsrExist){
                        def userObj = new Users()
                        userObj.email = messageIn[1]
                        userObj.name = messageIn[2]
                        userObj.password = messageIn[3]
                        userObj.createdOn = new Date()
                        def apiAccessToken = UUID.randomUUID().toString()+new Date().getTime()
                        def tokenAfterOpr = apiAccessToken.split("-").join()
                        userObj.apiAccessToken = tokenAfterOpr
                        if(userObj.save(flush:true,failOnError: true)){
                            session['user'] = messageIn[1]
                            returnMessage.add("User Successfully Registered")
                        }else{
                            valUser.errors.allErrors.each{
                                println it 
                            }
                        }
                    }
                    else{
                        returnMessage.add("User Already Exist")
                    }
                }
                if (msgType == "2"){
                    def getUser = Users.findByEmailAndPassword(messageIn[1],messageIn[2])
                    print "getUser "+getUser
                    if(getUser){
                        session['user'] = messageIn[1]
                        returnMessage.add("Login Successfull")
                    }
                    else{
                        returnMessage.add("Authentication Failed")
                    }
                }
                //Sending the response back to the client.
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is "+returnMessage);
                bw.flush();
                bw.close();
            }
        }        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
//                print "Closing Connection and Clean Up..."
//                connection.close();
            }
            catch(Exception e){}
        }
    }
    
    def startServer() {
        String host = "localhost";
        int port = 8082;
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(host));
        return server
    }
}

