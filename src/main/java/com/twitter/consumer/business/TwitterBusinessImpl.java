package com.twitter.consumer.business;

import com.twitter.consumer.data.TweetRepository;
import com.twitter.consumer.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Twitter business.
 */
@Component
public class TwitterBusinessImpl implements TwitterBusiness {

    private final TweetRepository tweetRepository;

    /**
     * Instantiates a new Twitter business.
     *
     * @param tweetRepository the tweet repository
     */
    TwitterBusinessImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }


    @Override
    public Iterable<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    @Override
    public List<Tweet> getTweetsByUser(String user) {
        return tweetRepository.findByUser(user);
    }

    @Override
    public List<Tweet> getValidatedTweetsByUser(String user) {
        return tweetRepository.findByValidated(user);
    }

    @Override
    public Optional<Tweet> getTweetById(Long id) {
        return tweetRepository.findById(id);
    }

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public HashMap<String, Integer> getHashtagRanking(int top) {
        List<String> tweetsWithHashtags = tweetRepository.findTweetsWithHashtags();
        HashMap<String, Integer> ranking = new HashMap<>();
        List<String> hashtagList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\B(\\#[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+\\b)(?!;)");

        tweetsWithHashtags.stream().map(pattern::matcher).forEach(matcher -> {
            while (matcher.find()) {
                hashtagList.add(matcher.group());
            }
        });

        hashtagList.forEach(h -> {
            if (ranking.containsKey(h)) {
                ranking.put(h, ranking.get(h) + 1);
            } else {
                ranking.put(h, 1);
            }
        });

        return ranking.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(top)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
