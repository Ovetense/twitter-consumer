package com.twitter.consumer.controller;

import com.twitter.consumer.validator.LanguageValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import static com.twitter.consumer.controller.TwitterInstance.streamFeed;

/**
 * The type Stream rest controller.
 */
@RestController
@Validated
public class StreamRestController {

    /**
     * Stream.
     *
     * @param q                 the terms to filter by
     * @param followerThreshold the follower threshold
     * @param lang              the language of the persisted tweets
     */
    @GetMapping("/stream")
    public void stream(@RequestParam(value = "q") String q,
                       @RequestParam(value = "followerThreshold", defaultValue = "1500") int followerThreshold,
                       @RequestParam(value = "lang", defaultValue = "es") @LanguageValidator String lang) {
        Twitter twitter = TwitterInstance.getTwitterInstance();
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(twitter.getConfiguration());
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        twitterStream.addListener(streamFeed(followerThreshold));
        FilterQuery query = new FilterQuery(q).language(lang);
        twitterStream.filter(query);
    }
}
