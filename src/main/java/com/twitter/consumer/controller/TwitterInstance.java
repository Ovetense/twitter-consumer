package com.twitter.consumer.controller;

import com.twitter.consumer.data.TweetRepository;
import com.twitter.consumer.entity.Tweet;
import com.twitter.consumer.util.BeanUtil;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;


/**
 * The type Twitter instance.
 */
public class TwitterInstance {

    /**
     * Instantiates a new Twitter instance.
     */
    public TwitterInstance() {
    }

    /**
     * Gets configuration builder.
     *
     * @return the configuration builder
     */
    public static ConfigurationBuilder getConfigurationBuilder() {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        return cb.setDebugEnabled(true)
                .setOAuthConsumerKey("X")
                .setOAuthConsumerSecret("X")
                .setOAuthAccessToken("X")
                .setOAuthAccessTokenSecret("X").setJSONStoreEnabled(true);

    }

    /**
     * Gets twitter instance.
     *
     * @return the twitter instance
     */
    public static Twitter getTwitterInstance() {
        ConfigurationBuilder cb = getConfigurationBuilder();
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();

    }

    /**
     * Stream feed status listener.
     *
     * @param followerThreshold the follower threshold
     * @return the status listener
     */
    public static StatusListener streamFeed(int followerThreshold) {

        return new StatusListener() {

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }

            @Override
            public void onStallWarning(StallWarning warning) {
            }

            @Override
            public void onStatus(Status status) {
                String statusJson = DataObjectFactory.getRawJSON(status);
                JSONObject JSONComplete = new JSONObject(statusJson);
                int userTweetFollower = JSONComplete.getJSONObject("user").getInt("followers_count");
                Tweet tweet = new Tweet(JSONComplete.getJSONObject("user").getString("location"),
                        JSONComplete.getString("text"), false, JSONComplete.getJSONObject("user").getString("screen_name"));
                if (userTweetFollower > followerThreshold)
                    BeanUtil.getBean(TweetRepository.class).save(tweet);
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }
        };
    }

}
