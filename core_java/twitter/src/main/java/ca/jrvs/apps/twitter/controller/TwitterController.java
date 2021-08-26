package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.springframework.util.StringUtils;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";
  private static final String INVALID_NUM_ARGS = "Invalid number of arguments:";

  private static final String POST_EXCEPTION = "USAGE : "
      + "TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"";
  private static final String SHOW_EXCEPTION = "USAGE : "
      + "TwitterCLIApp show \"tweet_id\" \"tweet_fields\"\n"
      + "Use \"all\" for all valid tweet_fields or:\n"
      + "created_at,id,id_str,text,entities,coordinates,"
      + "retweet_count,favorite_count,favorited,retweeted";
  private static final String[] ALL_FIELDS = {"created_at","id","id_str","text",
      "entities","coordinates","retweet_count","favorite_count","favorited","retweeted"};
  private static final String DELETE_EXCEPTION = "USAGE : "
      + "TwitterCLIApp delete \"tweet_ids\"";

  private final Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args args[1]: "tweet_text", args[2]: "latitude:longitude"
   * @return Tweet to be posted
   */
  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(INVALID_NUM_ARGS + "\n" + POST_EXCEPTION);
    }

    String text = args[1];
    String coord = args[2];
    String[] coordArray = coord.split(COORD_SEP);
    if (coordArray.length != 2 || StringUtils.isEmpty(text)) {
      throw new IllegalArgumentException("Invalid argument format:\n" + POST_EXCEPTION);
    }

    float lat;
    float lon;
    try {
      lat = Float.parseFloat(coordArray[0]);
      lon = Float.parseFloat(coordArray[1]);
    } catch (NumberFormatException  e) {
      throw new IllegalArgumentException("Invalid Coordinates format:\n" + POST_EXCEPTION, e);
    }

    Tweet tweet = TweetUtil.buildTweet(text, lon, lat);

    return service.postTweet(tweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   * Pass tweet_fields as one string, with fields seperated by commas
   * Use "all" or see TwitterService for valid tweet_fields
   *
   * @param args args args[1]: "tweet_id", args[2]: "tweet_fields"
   * @return Tweet to be shown
   */
  @Override
  public Tweet showTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(INVALID_NUM_ARGS + "\n" + SHOW_EXCEPTION);
    }

    String id = args[1];
    String[] fields;
    if (args[2].equals("all")) {
      fields = ALL_FIELDS;
    } else {
      fields = args[2].split(COMMA);
    }

    return service.showTweet(id, fields);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   * Pass tweet_ids as one string, with each id seperated by a comma
   *
   * @param args args args[1]: "tweet_ids"
   * @return Tweet to be shown
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException(INVALID_NUM_ARGS + "\n" + DELETE_EXCEPTION);
    }

    String[] tweet_ids = args[1].split(COMMA);

    return service.deleteTweets(tweet_ids);
  }
}
