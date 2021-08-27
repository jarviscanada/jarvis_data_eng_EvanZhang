package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;

public class TwitterControllerIntTest extends TestCase {

  private TwitterController twitterController;
  String consumerKey = System.getenv("consumerKey");
  String consumerSecret = System.getenv("consumerSecret");
  String accessToken = System.getenv("accessToken");
  String tokenSecret = System.getenv("tokenSecret");

  //Valid Tweet Id: change when testing testDelete()
  String tweetId = "1430550035092197377";
  String invalidTweetId = "1430550035092197_bc";

  @Before
  public void setUp() {
    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken,
        tokenSecret);
    CrdDao twitterDao = new TwitterDao(twitterHttpHelper);
    Service twitterService = new TwitterService(twitterDao);
    twitterController = new TwitterController(twitterService);
  }

  public void testPostTweet() throws JsonProcessingException {
    String[] args = {"post", "text", "-20.1:179.9"};
    String[] argsInvalidLat= {"post", "text", "90.1:179.9"};

    Tweet tweet = null;
    try {
      tweet = twitterController.postTweet(args);
    } catch (Exception e) {
      fail();
    }
    System.out.println(JsonParser.toJson(tweet, true, false));

    try {
      twitterController.postTweet(argsInvalidLat);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  public void testShowTweet() throws JsonProcessingException {
    String[] argsFields = {"show", "1430724856589455360",
        "id_str,text,coordinates,retweet_count,favorited"};
    String[] argsAll = {"show", "1430724856589455360", "all"};
    String[] argsInvalidField = {"show", "1430724856589455360", "anything"};

    Tweet tweet = null;
    try {
      tweet = twitterController.showTweet(argsFields);
    } catch (Exception e) {
      fail();
    }
    assertEquals(argsFields[1], tweet.getId_str());
    System.out.println(JsonParser.toJson(tweet, true, false));

    try {
      tweet = twitterController.showTweet(argsAll);
    } catch (Exception e) {
      fail();
    }
    assertEquals(argsAll[1], tweet.getId_str());
    System.out.println(JsonParser.toJson(tweet, true, false));

    try {
      twitterController.postTweet(argsInvalidField);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  public void testDeleteTweet() {
    String[] args = {"delete", "1430187937791053833,1430187937791053833"};
    List<Tweet> tweets = null;
    try {
      tweets = twitterController.deleteTweet(args);
    } catch (Exception e) {
      fail();
    }
    assertEquals(2, tweets.size());
  }
}
