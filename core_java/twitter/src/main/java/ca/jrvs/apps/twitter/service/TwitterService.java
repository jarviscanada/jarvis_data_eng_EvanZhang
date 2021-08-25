package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TwitterService implements Service {
  private final CrdDao crdDao;
  private final String[] tweetFields = {"created_at", "id", "id_str", "text", "entities",
      "coordinates", "retweet_count", "favorite_count", "favorited", "retweeted"};

  public TwitterService(CrdDao crdDao) {
    this.crdDao = crdDao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    //Business logic
    validatePostTweet(tweet);

    return (Tweet) crdDao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateTweetId(id);
    Tweet tweet = (Tweet) crdDao.findById(id);

    HashMap<String, Boolean> fieldMap = new HashMap<>();
    Arrays.stream(tweetFields).forEach(field -> fieldMap.put(field, false));
    Arrays.stream(fields).forEach(field -> fieldMap.put(field, true));

    if (fieldMap.size() != tweetFields.length) {
      throw new IllegalArgumentException("Invalid field parameters");
    }

    //setting fields not in the list to null
    if (!fieldMap.get("created_at")) {
      tweet.setCreated_at(null);
    }
    if (!fieldMap.get("id")) {
      tweet.setId(null);
    }
    if (!fieldMap.get("id_str")) {
      tweet.setId_str(null);
    }
    if (!fieldMap.get("text")) {
      tweet.setText(null);
    }
    if (!fieldMap.get("entities")) {
      tweet.setEntities(null);
    }
    if (!fieldMap.get("coordinates")) {
      tweet.setCoordinates(null);
    }
    if (!fieldMap.get("retweet_count")) {
      tweet.setRetweet_count(null);
    }
    if (!fieldMap.get("favorite_count")) {
      tweet.setFavorite_count(null);
    }
    if (!fieldMap.get("favorited")) {
      tweet.setFavorited(null);
    }
    if (!fieldMap.get("retweeted")) {
      tweet.setRetweeted(null);
    }

    return tweet;
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweets = new ArrayList<>();
    Arrays.stream(ids).forEach(id -> { validateTweetId(id);
      tweets.add((Tweet) crdDao.deleteById(id));
    });

    return tweets;
  }

  /**
   * Checks if Tweet is valid
   * Text must be within 140 characters
   * If coordinates are specified:
   * Latitude is valid at {-90, 90} degrees, Longitude: {-180, 180}
   * @param tweet Tweet to be posted
   */
  public void validatePostTweet(Tweet tweet) {
    if (tweet.getText().length() > 140) {
      throw new IllegalArgumentException("Text exceeds max number of allowed characters");
    }

    if (tweet.getCoordinates() != null) {
      Float lon = tweet.getCoordinates().getCoordinates().get(0);
      Float lat = tweet.getCoordinates().getCoordinates().get(1);
      if (lon > 180.0 | lon < -100.0 | lat > 90.0 | lat < -90.0) {
        throw new IllegalArgumentException("Lat/long out of range");
      }
    }
  }

  // Id must be valid 64 bit number
  public void validateTweetId(String id) {
    try {
      Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Tweet Id", e);
    }
  }
}