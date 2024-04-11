import utools.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JFrame {
    public ButtonPanel() {
        setTitle("面板");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // 创建按钮1
        JButton button1 = new JButton("自定义序列化数据");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    method1();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(button1);

        // 创建按钮2
        JButton button2 = new JButton("默认Gadget");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    method2();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(button2);

        add(panel);
    }

    // 方法1
    private void method1() throws Exception {
        Run.other();
    }

    // 方法2
    private void method2() throws Exception {
        Run.pop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ButtonPanel().setVisible(true);
            }
        });
    }
}
