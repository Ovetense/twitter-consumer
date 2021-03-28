package com.twitter.consumer.data;

import com.twitter.consumer.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Tweet repository.
 */
@Repository
@Component
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    /**
     * Finds validated tweets by user.
     *
     * @param user the user
     * @return the list
     */
    @Query("SELECT t FROM Tweet t WHERE t.validated = true AND t.user = ?1")
    List<Tweet> findByValidated(String user);

    /**
     * Find tweets by user.
     *
     * @param user the user
     * @return the list
     */
    List<Tweet> findByUser(String user);

    /**
     * Find tweets with hashtags.
     *
     * @return the list
     */
    @Query("SELECT t.text FROM Tweet t WHERE t.text LIKE '%#%'")
    List<String> findTweetsWithHashtags();

}
