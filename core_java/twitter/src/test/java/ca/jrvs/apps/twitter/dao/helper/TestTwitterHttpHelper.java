package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;

public class TestTwitterHttpHelper extends TestCase {
  private TwitterHttpHelper twitterHttpHelper;
  String consumerKey = System.getenv("consumerKey");
  String consumerSecret = System.getenv("consumerSecret");
  String accessToken = System.getenv("accessToken");
  String tokenSecret = System.getenv("tokenSecret");

  @Before
  public void setUp() {
    twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
  }

  public void testHttpPost() throws Exception {
    HttpResponse httpResponse = twitterHttpHelper.httpPost(
        URI.create("https://api.twitter.com/1.1/statuses/update.json?status=first_tweet2"));
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }

  public void testHttpGet() throws Exception {
    HttpResponse httpResponse = twitterHttpHelper.httpGet(
        URI.create("https://api.twitter.com/1.1/statuses/show.json?id=1429939459517296651"));
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }
}
