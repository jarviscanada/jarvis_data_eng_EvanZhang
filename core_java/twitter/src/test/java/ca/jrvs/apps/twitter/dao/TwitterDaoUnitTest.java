package ca.jrvs.apps.twitter.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isNotNull;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest extends TestCase {

  //Simplified Tweet Object
  static final String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "        \"hashtags\":[],\n"
      + "        \"user_mentions\":[]\n"
      + "   },\n"
      + "   \"coordinates\":null,\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";
  static final String tweetId = "1097607853932564480";

  @Mock
  private TwitterHttpHelper mockHelper;

  @InjectMocks
  private TwitterDao dao;

  @Test
  public void testCreate() throws Exception {
    //Test failed request
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Float lon = (float) 1d;
    Float lat = (float) -1d;
    //Expect exception here
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetUtil.buildTweet(text, lon, lat));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create((TweetUtil.buildTweet(text, lon, lat)));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void testFindById() throws IOException {
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.
          findById(tweetId);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.findById(tweetId);

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertEquals(tweetId, tweet.getId_str());
  }

  @Test
  public void testDeleteById() throws IOException {
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.deleteById(tweetId);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.deleteById(tweetId);

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertEquals(tweetId, tweet.getId_str());
  }
}
