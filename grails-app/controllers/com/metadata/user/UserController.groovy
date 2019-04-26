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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future

import RateITUsers.Users
import com.subsystem.ConversationFactory
import com.subsystem.Dispatcher
import com.subsystem.Envelope
import com.subsystem.UDPComm
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.message.Message;
import com.subsystem.ConversationDictionary;

class UserController {
    def dataSource
    
    //Dispatcher t2;
    
    def index() {

    }
    
    def userRegisterAndLogin(){
        //        print "Receiving......"
        //        UDPComm t1 = new UDPComm();
        //        //t2 = new Dispatcher();
        ////        while(true){
        ////            print "Listeninin...."
        ////            t1.receive()
        ////        }
        //        Thread udpCommThread =  new Thread(t1);
        //        //Thread dispatcherThread = new Thread(t2);
        //        Future<Envelope> task = udpCommThread.start();
        //        Envelope env = task.get();
        //        print("After Thread completion "+env.getMessage().getConversationId())
        //println "Stsrting dispatcher thread"
        //dispatcherThread.start();
        //        t.start()
        UDPComm c1 = new UDPComm();
        
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Envelope> future = service.submit(c1);
        Envelope env = future.get();
        //print("Envelope "+env.getMessage());
        print("user name "+env.getMessage().getName());
        print("email "+env.getMessage().getEmailId());
        print("password "+env.getMessage().getPassword());
        print("area of interest "+env.getMessage().getAreaOfInterest());
        List<String> userDetails = new ArrayList();
        userDetails.add(env.getMessage().getName());
        userDetails.add(env.getMessage().getEmailId());
        userDetails.add(env.getMessage().getPassword());
        userDetails.add(env.getMessage().getAreaOfInterest());
        def flag = saveUser(userDetails);
        if(flag==true){
            try{
                print("flag is true")
                ConversationFactory cf = new ConversationFactory()
                // Sending Request to RequestResolver server
                print("Conversation Factory created")
                def msgType = "ACKMessage"
                
                print("msgType is Assigned "+msgType.getClass().getName())
                def conv = ConversationDictionary.getConversation(env.getMessage().getConversationId());
                print("current conv received from Conversation Dictionary "+conv)
                def convId = conv.getConvId()
                
                def env2 = createEnvelope(cf,msgType)
                //System.out.println("No. of bytes after creating and encoding the object controller"+env2.getMessage().conversationId);
                sendEnvelope(env2)
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
            
        
        
        
        
    }
    
    //    public UDPComm getUDPComm(){
    //        return t1;
    //    }

    def saveUser(userDetails){
        Users usrObj = new Users();
        usrObj.name = userDetails.get(0);
        usrObj.email = userDetails.get(1);
        usrObj.password = userDetails.get(2);
        //                byte[] byteConent = userDetails.get(3).getBytes();
        //                usrObj.userPref.setBytes(1, byteContent);
        usrObj.createdOn = new Date()
        def apiAccessToken = UUID.randomUUID().toString()+new Date().getTime()
        def tokenAfterOpr = apiAccessToken.split("-").join()
        usrObj.apiAccessToken = tokenAfterOpr
        usrObj.userPref = userDetails.get(3);
        if(usrObj.save(flush:true,failOnError: true)){
            print("user details saved");
            return true
        }else{
            valUser.errors.allErrors.each{
                return false;
            }
        }
    }
    def createConversation(cf){
        print  "Creating Conversation"+cf
        return cf.CreateConversation()
    }
    def sendEnvelope(Env){
        //print "Sending Envelope"+subsystemObj
        //        print subsystemObj.Initialize()
        //        print subsystemObj.setUdpComm(new UDPComm())
        //        print "sendingggg"+subsystemObj.getUdpComm()
        print("Inside send envelope method of UserController")
        UDPComm UDPCommobj = new UDPComm()
        UDPCommobj.send(Env)
    }
    def createEnvelope(cf,msgType){
        
        def msgList = []
        

        if (msgType.equals("CreateProd")){
            msgList.add("CreateProd")
            msgList.add(params.userId)
            msgList.add(params.name)
            msgList.add(params.prodId)
            msgList.add(params.price)
        }
        if (msgType.equals("FollowProd")){
            msgList.add("FollowProd")
            msgList.add(params.userId)
            msgList.add(params.prodId)
        }
        if (msgType.equals("RateProd")){
            msgList.add("RateProd")
            msgList.add(params.prodId)
            msgList.add(params.rating)
            msgList.add(params.review)
        }
        if (msgType.equals("RateFeed")){
            msgList.add("RateFeed")
            msgList.add(params.prodId)
            msgList.add(params.vote)
            msgList.add(params.reviewId)
            msgList.add(params.userId)
        }
        if (msgType.equals("SearchProd")){
            msgList.add("SearchProd")
            msgList.add(params.prodName)
            msgList.add(params.prodId)
        }
        if (msgType.equals("ACKMessage")){
            print "***************"
            msgList.add("ACKMessage")
            print("msgList created")
        }
        
        
        print "Creating Envelope inside RR controller..............."
        print("msgList "+msgList)
        def res = cf.CreateEnvelopee(msgList)
        print "response"+res
        return res
    }
    
    //    def userLogin(){
    //        def usrObj = Users.findByUserIdAndPassword(,)
    //        def usrObj = Users.findByUserId(,)
    //    }
}

