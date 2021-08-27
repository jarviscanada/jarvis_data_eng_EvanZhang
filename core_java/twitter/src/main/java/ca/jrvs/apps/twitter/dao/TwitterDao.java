package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";
  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private final HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) { this.httpHelper = httpHelper; }

  /**
   *
   * @param entity entity that to be created
   * @return Created Tweet Entity
   */
  @Override
  public Tweet create(Tweet entity) {
    URI uri;
    try {
      uri = getPostUri(entity);
    } catch (URISyntaxException | UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Invalid Tweet input", e);
    }
    HttpResponse httpResponse = httpHelper.httpPost(uri);

    return parseResponseBody(httpResponse, HTTP_OK);
  }

  /**
   * Finds specified Tweet by given Tweet ID
   * @param o Tweet ID
   * @return Found Tweet Entity
   */
  @Override
  public Tweet findById(String o) {
    URI uri = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + o);
    HttpResponse httpResponse = httpHelper.httpGet(uri);

    return parseResponseBody(httpResponse, HTTP_OK);
  }

  /**
   * Deletes specified Tweet by given Tweet ID
   * @param o Tweet ID
   * @return Deleted Tweet Entity
   */
  @Override
  public Tweet deleteById(String o) {
    URI uri = URI.create(API_BASE_URI + DELETE_PATH + "/" + o +".json");
    HttpResponse httpResponse = httpHelper.httpPost(uri);

    return parseResponseBody(httpResponse, HTTP_OK);
  }

  private URI getPostUri(Tweet entity) throws UnsupportedEncodingException, URISyntaxException {
    StringBuilder parameters = new StringBuilder();
    //required parameter
    parameters.append("status" + EQUAL).append(URLEncoder.encode(entity.getText(), "UTF-8"));

    //optional parameters
    if (entity.getCoordinates() != null) {
      parameters.append(AMPERSAND + "long" + EQUAL)
          .append(URLEncoder.encode(entity.getCoordinates().getCoordinates().get(0).toString(), "UTF-8"));
      parameters.append(AMPERSAND + "lat" + EQUAL)
          .append(URLEncoder.encode(entity.getCoordinates().getCoordinates().get(1).toString(), "UTF-8"));
    }

    return  new URI(API_BASE_URI + POST_PATH + QUERY_SYM + parameters);
  }

  /**
   * Check response status code
   * Converts Response Entity to Tweet
   */
  public Tweet parseResponseBody(HttpResponse httpResponse, Integer expectedResponseCode) {
    Tweet tweet;

    //Check response status
    int status = httpResponse.getStatusLine().getStatusCode();
    if (status != expectedResponseCode) {
      try {
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status:" + status);
    }

    if (httpResponse.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    //Convert response body to string
    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(httpResponse.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to string", e);
    }

    //Deserialize JSON String to Tweet object
    try {
      tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }

    return tweet;
  }
}
