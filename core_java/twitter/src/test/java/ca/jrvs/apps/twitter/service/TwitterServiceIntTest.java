package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest extends TestCase {

  private TwitterService twitterService;
  String consumerKey = System.getenv("consumerKey");
  String consumerSecret = System.getenv("consumerSecret");
  String accessToken = System.getenv("accessToken");
  String tokenSecret = System.getenv("tokenSecret");

  //Valid Tweet Id: change when testing testDeleteById()
  String tweetId = "1430550035092197377";
  String invalidTweetId = "1430550035092197_bc";

  @Before
  public void setUp() {
    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken,
        tokenSecret);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    twitterService = new TwitterService(twitterDao);
  }

  public void testPostInvalidTweet() throws JsonProcessingException {
    //Text length exceeds 140 characters
    String text = "@someone: \"If we make it easier for more foreign visitors to visit... "
        + "(it) grows the #economy.\" Pass the #PAct some Website that's not valid + extra text";
    Tweet tweet = TweetUtil.buildTweet(text, (float) 80.0, (float) 22.1);
    try {
      twitterService.postTweet(tweet);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      System.out.println(JsonParser.toJson(tweet, true, false));
    }

    //Invalid lat/long
    tweet = TweetUtil.buildTweet("text", (float) 181.0, (float) 22.1);
    try {
      twitterService.postTweet(tweet);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      System.out.println(JsonParser.toJson(tweet, true, false));
    }
  }

  public void testPostValidTweet() throws JsonProcessingException {
    String text = "@someone: \"If we make it easier for more foreign visitors to visit... "
        + "(it) grows the #economy.\" Pass the #PAct some Website that's not valid";
    Float lon = (float) 2.2;
    Float lat = (float) 1.1;

    Tweet newTweet = TweetUtil.buildTweet(text, lon, lat);
    Tweet tweet = twitterService.postTweet(newTweet);

    assertEquals(text, tweet.getText());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    System.out.println(JsonParser.toJson(tweet, true, false));
  }

  public void testInvalidShowTweet() {
    try {
      twitterService.showTweet(invalidTweetId, new String[]{"coordinates"});
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  public void testValidShowTweet() throws JsonProcessingException {
    String[] fieldsA = {"coordinates", "retweet_count", "favorited", "id"};
    Tweet tweet = twitterService.showTweet(tweetId, fieldsA);
    assertNull(tweet.getRetweeted());
    assertNotNull(tweet.getRetweet_count());
    System.out.println(JsonParser.toJson(tweet, true, false));

    String[] fieldsB = {"created_at", "id_str", "text", "entities", "favorite_count", "retweeted"};
    tweet = twitterService.showTweet(tweetId, fieldsB);
    assertNull(tweet.getId());
    assertNotNull(tweet.getText());
    System.out.println(JsonParser.toJson(tweet, true, false));
  }

  public void testDeleteTweets() {
    String[] tweetIds = {"1430391998880563206", "1430390190120620034"};
    List<Tweet> tweets = twitterService.deleteTweets(tweetIds);

    assertEquals(tweetIds.length, tweets.size());
  }
}