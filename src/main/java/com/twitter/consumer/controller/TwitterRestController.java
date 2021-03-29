package com.twitter.consumer.controller;

import com.twitter.consumer.business.TwitterBusiness;
import com.twitter.consumer.entity.Tweet;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.twitter.consumer.controller.HateoasAssembler.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Twitter rest controller.
 */
@RestController
public class TwitterRestController {

    private final TwitterBusiness twitterBusiness;

    /**
     * Instantiates a new Twitter rest controller.
     *
     * @param twitterBusiness the twitter business
     */
    TwitterRestController(TwitterBusiness twitterBusiness) {
        this.twitterBusiness = twitterBusiness;
    }

    /**
     * Gets tweets.
     *
     * @return the tweets
     */
    @GetMapping("/tweets")
    CollectionModel<Tweet> getTweets() {
        Iterable<Tweet> tweets = twitterBusiness.getAllTweets();
        return assemblyItLink(tweets);
    }


    /**
     * Gets tweet by user.
     *
     * @param user the user
     * @return the tweet by user
     */
    @GetMapping("/tweets/{user}")
    CollectionModel<Tweet> getTweetByUser(@PathVariable String user) {
        List<Tweet> tweets = twitterBusiness.getTweetsByUser(user);
        return assemblyLinks(user, tweets);
    }

    /**
     * Gets validated tweets by user.
     *
     * @param user the user
     * @return the validated tweets by user
     */
    @GetMapping("/validatedTweets/{user}")
    CollectionModel<Tweet> getValidatedTweetsByUser(@PathVariable String user) {
        List<Tweet> tweets = twitterBusiness.getValidatedTweetsByUser(user);
        return assemblyLinks(user, tweets);
    }

    /**
     * Gets tweet by id.
     *
     * @param id the id
     * @return the tweet by id
     */
    @GetMapping("/tweet/{id}")
    EntityModel<Tweet> getTweetById(@PathVariable Long id) {
        Tweet tweet = twitterBusiness.getTweetById(id);
        tweet.add(linkTo(methodOn(TwitterRestController.class).getTweetById(tweet.getId())).withSelfRel());
        return assemblyLink(tweet);
    }

    /**
     * Mark tweet as validated entity model.
     *
     * @param id the id
     * @return the entity model
     */
    @GetMapping("/toggleValidated/{id}")
    EntityModel<Tweet> markTweetAsValidated(@PathVariable Long id) {
        Tweet tweet = twitterBusiness.toggleValidate(id);
        tweet.add(linkTo(methodOn(TwitterRestController.class).getTweetById(tweet.getId())).withSelfRel());
        return assemblyLink(tweet);
    }


    /**
     * Mark tweet as validated hash map.
     *
     * @param top the top
     * @return the hash map
     */
    @GetMapping("/hashtagRanking")
    HashMap<String, Integer> markTweetAsValidated(@RequestParam(defaultValue = "10") int top) {
        HashMap<String, Integer> tweetsWithHashTag = twitterBusiness.getHashtagRanking(top);
        return tweetsWithHashTag;
    }
}
