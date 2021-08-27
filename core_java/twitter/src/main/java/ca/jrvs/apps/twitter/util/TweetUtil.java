package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.Arrays;

public class TweetUtil {

  public static Tweet buildTweet(String text, Float lon, Float lat) {
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(Arrays.asList(lon, lat));
    coordinates.setType("Point");

    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }

}
