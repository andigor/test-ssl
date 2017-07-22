import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;

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
      //KeyStore ksKeys = KeyStore.getInstance("JKS");
      //ksKeys.load(new FileInputStream("testKeys"), null);
      
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      InputStream caInput = new BufferedInputStream(new FileInputStream("../server/foo-cert.pem"));

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

      URL url = new URL("https://127.0.0.1:1111");
      //URL url = new URL("https://www.google.com");
      HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
      urlConnection.setSSLSocketFactory(context.getSocketFactory());
      HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session)
        {
          System.out.println("verifyng host: " + hostname);
          return true;
        }
      };
      urlConnection.setHostnameVerifier(allHostsValid);
      InputStream in = urlConnection.getInputStream();
      System.out.println("done");
    }
    catch (Exception e) {
      System.out.println(e.getMessage());

      e.printStackTrace();
    }
  }
}
