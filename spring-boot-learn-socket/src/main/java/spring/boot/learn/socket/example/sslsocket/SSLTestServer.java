package spring.boot.learn.socket.example.sslsocket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-06-09
 * @since Jdk 1.8
 */
public class SSLTestServer {
    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getInstance("SSL");

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore tks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream("cert/kserver.ks"), "serverpass".toCharArray());
        tks.load(new FileInputStream("cert/tserver.ks"), "serverpublicpass".toCharArray());

        kmf.init(ks, "serverpass".toCharArray());
        tmf.init(tks);

        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        SSLServerSocket serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(8443);
        serverSocket.setNeedClientAuth(true);

        while (true) {
            try {
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);

                byte[] buffer = new byte[20];
                int length = bis.read(buffer);
                System.out.println("Receive: " + new String(buffer, 0, length).toString());

                bos.write("Hello".getBytes());
                bos.flush();

                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
