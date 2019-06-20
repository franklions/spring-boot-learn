package spring.boot.learn.socket.example.sslsocket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-06-09
 * @since Jdk 1.8
 */
public class SSLTestClient {
    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getInstance("SSL");

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore tks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream("cert/kclient.ks"), "clientpass".toCharArray());
        tks.load(new FileInputStream("cert/tclient.ks"), "clientpublicpass".toCharArray());

        kmf.init(ks, "clientpass".toCharArray());
        tmf.init(tks);

        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLSocket sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket("localhost", 8443);
        InputStream input = sslSocket.getInputStream();
        OutputStream output = sslSocket.getOutputStream();

        BufferedInputStream bis = new BufferedInputStream(input);
        BufferedOutputStream bos = new BufferedOutputStream(output);

        bos.write("Hello".getBytes());
        bos.flush();

        byte[] buffer = new byte[20];
        int length = bis.read(buffer);
        System.out.println(new String(buffer, 0, length));

        sslSocket.close();
    }
}
