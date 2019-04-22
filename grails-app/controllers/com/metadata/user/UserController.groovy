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
import com.subsystem.UDPComm
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
        print "Receiving......"
        UDPComm t1 = new UDPComm();
        while(true){
            print "Listeninin...."
            t1.receive()
        }
//        Thread t =  new Thread(t1);
//        t.start()
    }
}

