import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import java.net.URL;


import javax.net.ssl.*;
import java.security.KeyStore;
//import java.security.*;
import java.security.cert.*;

public class SslClient
{
  public static void main(String[] args)
  {
    try {
      String certPath = new String("../server/foo-cert.pem");
      boolean foundSert = false;
      for (String s: args)
      {
        certPath = s;
        foundSert = true;
        break;
      }
      if (!foundSert) {
        System.out.println("Using default certPath: " + certPath);
      }


      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      InputStream caInput = new BufferedInputStream(new FileInputStream(certPath));
      //InputStream caInput = new BufferedInputStream(new FileInputStream("../ca/ca-cert.pem"));
      //InputStream caInput = new BufferedInputStream(new FileInputStream("fake-cert/cert.pem"));

      Certificate ca = cf.generateCertificate(caInput);
      try {
        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
      }
      finally {
        caInput.close();
      }

      String keyStoreType = KeyStore.getDefaultType();
      KeyStore keyStore = KeyStore.getInstance(keyStoreType);
      keyStore.load(null, null);
      keyStore.setCertificateEntry("ca", ca);

      String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
      tmf.init(keyStore);

      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null, tmf.getTrustManagers(), null);

      //URL url = new URL("https://127.0.0.1:1111");
      //URL url = new URL("https://www.google.com");
      //HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
      //urlConnection.setSSLSocketFactory(context.getSocketFactory());
      //HostnameVerifier allHostsValid = new HostnameVerifier() {
      //  public boolean verify(String hostname, SSLSession session)
      //  {
      //    System.out.println("verifyng host: " + hostname);
      //    return true;
      //  }
      //};
      //urlConnection.setHostnameVerifier(allHostsValid);

      SSLSocketFactory fact = (SSLSocketFactory) context.getSocketFactory();
      SSLSocket socket = (SSLSocket) fact.createSocket("127.0.0.1", 1111);

      BufferedOutputStream dos = new BufferedOutputStream(socket.getOutputStream());
      DataInputStream dis = new DataInputStream(socket.getInputStream());

      dos.write("hello".getBytes());
      dos.flush();

      ArrayList<Byte> bytes = new ArrayList<Byte>();
      while (true)
      {
        try 
        {
          Byte b = dis.readByte();
          //System.out.println(b);
          bytes.add(b);
        }
        catch (Exception e)
        {
          System.out.println("end of reading");
          break;
        }
      }
      byte[] array = new byte[bytes.size()];
      int i = 0;
      for (Byte b: bytes)
      {
        array[i++] = b;
      }
      String resp = new String(array);
      //InputStream in = urlConnection.getInputStream();
      System.out.println("done: " + resp);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());

      e.printStackTrace();
    }
  }
}
