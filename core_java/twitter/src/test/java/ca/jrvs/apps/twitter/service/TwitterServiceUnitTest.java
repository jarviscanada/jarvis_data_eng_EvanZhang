package ca.jrvs.apps.twitter.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest extends TestCase {

  @Mock
  CrdDao crdDao;

  @InjectMocks
  TwitterService twitterService;

  @Test
  public void postTweet() {
    when(crdDao.create(any())).thenReturn(new Tweet());

    Tweet validTweet = TweetUtil.buildTweet("test", (float) 50.0, (float) 50.0);

    String invalidText = "@someone: \"If we make it easier for more foreign visitors to visit... "
        + "(it) grows the #economy.\" Pass the #PAct some Website that's not valid + extra text";
    Tweet invalidTweetText = TweetUtil.buildTweet(invalidText, (float) 250.0, (float) 50.0);
    Tweet invalidTweetLong = TweetUtil.buildTweet("test", (float) 250.0, (float) 50.0);
    Tweet invalidTweetLat = TweetUtil.buildTweet("test", (float) 50.0, (float) -90.1);
    Tweet invalidTweetNoCoord = TweetUtil.buildTweet("test", null, (float) 50.0);

    //Valid call: should work
    try {
      twitterService.postTweet(validTweet);
    } catch (IllegalArgumentException e) {
      fail();
    }

    //Invalid calls: rest should fail
    try {
      twitterService.postTweet(invalidTweetText);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterService.postTweet(invalidTweetLong);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterService.postTweet(invalidTweetLat);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterService.postTweet(invalidTweetNoCoord);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    when(crdDao.findById(any())).thenReturn(new Tweet());

    String validTweetId = "1430550035092197377";
    String invalidTweetId = "1430550035092197_bc";

    String[] fieldsA = {"coordinates", "retweet_count", "favorited", "id"};
    String[] fieldsB = {"created_at", "id_str", "text", "entities", "favorite_count", "retweeted"};
    String[] invalidFields = {"coordination", "retweet_count", "favorited", "id"};

    //Valid
    try {
      twitterService.showTweet(validTweetId, fieldsA);
    } catch (IllegalArgumentException e) {
      fail();
    }
    try {
      twitterService.showTweet(validTweetId, fieldsB);
    } catch (IllegalArgumentException e) {
      fail();
    }

    //Invalid
    try {
      twitterService.showTweet(invalidTweetId, fieldsA);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterService.showTweet(validTweetId, invalidFields);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTweets() {
    when(crdDao.deleteById(any())).thenReturn(new Tweet());

    String[] validTweetIds = {"1430550035092197377", "1430550035092197378", "1430550035092197379"};
    String[] invalidTweetIds = {"1430550035092197377", "1430550035092197378", "143055003509219737a"};

    //Valid
    try {
      twitterService.deleteTweets(validTweetIds);
    } catch (IllegalArgumentException e) {
      fail();
    }

    //Invalid
    try {
      twitterService.deleteTweets(invalidTweetIds);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

}
