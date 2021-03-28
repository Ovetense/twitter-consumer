package com.twitter.consumer.controller;

import com.twitter.consumer.entity.Tweet;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Hateoas assembler.
 */
public class HateoasAssembler {

    /**
     * Assembly links collection model.
     *
     * @param user   the user
     * @param tweets the tweets
     * @return the collection model
     */
    public static CollectionModel<Tweet> assemblyLinks(@PathVariable String user, List<Tweet> tweets) {
        for (final Tweet tweet : tweets) {
            Link selfLink = linkTo(methodOn(TwitterRestController.class)
                    .getTweetById(tweet.getId())).withSelfRel();
            Link selfLink2 = linkTo(methodOn(TwitterRestController.class)
                    .getTweetByUser(tweet.getUser())).withSelfRel();
            tweet.add(selfLink, selfLink2);
        }
        Link link = linkTo(methodOn(TwitterRestController.class)
                .getTweetByUser(user)).withSelfRel();
        return CollectionModel.of(tweets, link);
    }

}
