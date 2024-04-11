package burp;

import utools.Run;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IExtensionStateListener, IContextMenuFactory{
    private  IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    public PrintWriter stderr;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks iBurpExtenderCallbacks) {
        this.callbacks = iBurpExtenderCallbacks;
        //插件名字
        this.callbacks.setExtensionName("LDAPDeserialize");
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.stderr = new PrintWriter(callbacks.getStderr(), true);
        stdout.println("LDAP任意链反序列化测试工具");
        //注册菜单栏
        callbacks.registerContextMenuFactory(this);
    }

    @Override
    public void extensionUnloaded() {
        stdout.println("Extension was uploaded");
    }


    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {

        List<JMenuItem> menuItems = new ArrayList<>();
        JMenuItem item = new JMenu("LDAPDeserialize");
        JMenuItem self = new JMenuItem("self");
        JMenuItem gadget = new JMenuItem("Gadget");

        self.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Run.other();
                } catch (Exception ex) {
                    stderr.println(ex.getMessage());
                }
            }
        });

        gadget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Run.pop();
                } catch (Exception ex) {
                    stderr.println(ex.getMessage());
                    stderr.println();
                }
            }
        });

        item.add(self);
        item.add(gadget);
        menuItems.add(item);
        return menuItems;
    }
}
