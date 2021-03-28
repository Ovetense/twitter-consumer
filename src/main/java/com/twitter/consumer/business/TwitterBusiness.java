package com.twitter.consumer.business;

import com.twitter.consumer.entity.Tweet;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The interface Twitter business.
 */
public interface TwitterBusiness {


    /**
     * Gets all tweets.
     *
     * @return the all tweets
     */
    Iterable<Tweet> getAllTweets();

    /**
     * Gets tweets by user.
     *
     * @param user the user
     * @return the tweets by user
     */
    List<Tweet> getTweetsByUser(String user);

    /**
     * Gets validated tweets by user.
     *
     * @param user the user
     * @return the validated tweets by user
     */
    List<Tweet> getValidatedTweetsByUser(String user);

    /**
     * Gets tweet by id.
     *
     * @param id the id
     * @return the tweet by id
     */
    Optional<Tweet> getTweetById(Long id);

    /**
     * Save tweet.
     *
     * @param tweet the tweet
     * @return the tweet
     */
    Tweet save(Tweet tweet);

    /**
     * Gets hashtag ranking.
     *
     * @param top the top
     * @return the hashtag ranking
     */
    HashMap<String,Integer> getHashtagRanking(int top);
}
