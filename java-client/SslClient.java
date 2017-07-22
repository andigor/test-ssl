import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SslClient
{
  private static String targetURL = new String("https://127.0.0.1:1111");

  public static void main(String[] args)
  {
    try {
      URL url = new URL(targetURL);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

      System.out.println("the end");
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
