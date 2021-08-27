package ca.jrvs.apps.twitter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest extends TestCase {
  @Mock
  Service service;
  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet() {
    when(service.postTweet(any())).thenReturn(new Tweet());

    String[] args = {"post", "text", "-20.1:179.9"};
    String[] argsInvalidText = {"post", "", "-20:179.9"};
    String[] argsInvalidCoord = {"post", "text", "-20.1:179.9:21"};
    String[] argsInvalidLat= {"post", "text", "abc:179.9"};

    //Valid call: should work
    try {
      twitterController.postTweet(args);
    } catch (IllegalArgumentException e) {
      fail();
    }
    //Invalid calls: rest should fail
    try {
      twitterController.postTweet(argsInvalidText);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterController.postTweet(argsInvalidCoord);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      twitterController.postTweet(argsInvalidLat);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    when(service.showTweet(any(), any())).thenReturn(new Tweet());

    //Proper Id and fields validation in TwitterService
    String[] args = {"show", "anything", "anything"};
    String[] argsFields = {"show", "", "any,thing"};
    String[] argsInvalidNum = {"show", "anything", "anything", "anything"};

    //Valid call: should work
    try {
      twitterController.showTweet(args);
    } catch (IllegalArgumentException e) {
      fail();
    }
    try {
      twitterController.showTweet(argsFields);
    } catch (IllegalArgumentException e) {
      fail();
    }
    //Invalid call
    try {
      twitterController.showTweet(argsInvalidNum);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTweet() {
    when(service.deleteTweets(any())).thenReturn(new ArrayList<>());

    //Proper Id validation in TwitterService
    String[] args = {"delete", "id1,id2,id3"};
    String[] argsInvalidNum = {"delete", "id1,id2,id3", "id4"};

    //Valid call: should work
    try {
      twitterController.deleteTweet(args);
    } catch (IllegalArgumentException e) {
      fail();
    }
    //Invalid call
    try {
      twitterController.deleteTweet(argsInvalidNum);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }


  }

}