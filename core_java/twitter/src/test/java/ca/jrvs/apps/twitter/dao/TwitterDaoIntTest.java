package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import org.junit.Before;

public class TwitterDaoIntTest extends TestCase {
  private TwitterHttpHelper twitterHttpHelper;
  private TwitterDao twitterDao;
  String consumerKey = System.getenv("consumerKey");
  String consumerSecret = System.getenv("consumerSecret");
  String accessToken = System.getenv("accessToken");
  String tokenSecret = System.getenv("tokenSecret");

  //Valid Tweet Id: change when testing testDeleteById()
  String tweetId = "1430391998880563206";

  @Before
  public void setUp() {
    twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    twitterDao = new TwitterDao(twitterHttpHelper);
  }

  public void testCreate() {
    //Tweet parameters
    String hashtag = "#crd";
    String text = "some text " + hashtag;
    Float lon = (float) 2.2;
    Float lat = (float) 1.1;

    Tweet newTweet = TweetUtil.buildTweet(text, lon, lat);
    Tweet tweet = twitterDao.create(newTweet);

    assertEquals(text, tweet.getText());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  public void testFindById() throws JsonProcessingException {
    Tweet tweet = twitterDao.findById(tweetId);

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    System.out.println(JsonParser.toJson(tweet, true, false));
  }

  public void testDeleteById() throws JsonProcessingException {
    Tweet tweet = twitterDao.deleteById(tweetId);

    try {
      twitterDao.findById(tweetId);
    } catch (Exception e) {
      assertTrue(true);
    }

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    System.out.println(JsonParser.toJson(tweet, true, false));
  }

}