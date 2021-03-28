package com.twitter.consumer.controller;

import com.twitter.consumer.entity.Tweet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;

/**
 * The type Twitter consumer application tests.
 */
@TestMethodOrder(Alphanumeric.class)
class TwitterConsumerApplicationTests {

    private static final String URL = "http://localhost:8080";

    /**
     * Test 01 ping.
     */
    @Test
    public void test01Ping() {
        given().when().get(URL + "/tweets").then().statusCode(200);
    }

    /**
     * Test 02 subscribe noq.
     */
    @Test
    public void test02SubscribeNoq() {
        given().when().get(URL + "/stream").then().statusCode(400);
    }

    /**
     * Test 03 subscribe.
     */
    @Test
    public void test03Subscribe() {
        given().param("q", "a").when().get(URL + "/stream").then().statusCode(200);
    }

    /**
     * Test 04 subscribe lang.
     */
    @Test
    public void test04SubscribeLang() {
        given().param("q", "acciona").param("lang", "fr").when().get(URL + "/stream").then().statusCode(200);
    }

    /**
     * Test 05 subscribe forbidden lang.
     */
    @Test
    public void test05SubscribeForbiddenLang() {
        given().param("q", "acciona").param("lang", "en").when().get(URL + "/stream").then().statusCode(400);
    }

    /**
     * Test 06 subscribe threshold lang.
     */
    @Test
    public void test06SubscribeThresholdLang() {
        given().param("q", "acciona").param("lang", "en").param("followerThreshold", "2000").when().get(URL + "/stream").then().statusCode(400);
    }

    /**
     * Test 07 subscribe threshold.
     */
    @Test
    @Disabled
    public void test07SubscribeThreshold() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        given().param("q", "acciona").param("lang", "es").param("followerThreshold", "2000").when().get(URL + "/stream").then().statusCode(200);
    }

    /**
     * Test 08 get by id and user.
     */
    @Test
    public void test08getByIdAndUser() {
        var user = given().when().get(URL + "/tweet/1").then().statusCode(200).extract().body().as(Tweet.class).getUser();
        given().when().get(URL + "/tweets/" + user).then().statusCode(200);
    }

    /**
     * Test 09 validate tweet.
     */
    @Test
    public void test09validateTweet() {
        var validated = given().when().get(URL + "/tweet/1").then().statusCode(200).extract().body().as(Tweet.class).isValidated();
        var validatedAfter = given().when().get(URL + "/toggleValidated/1").then().statusCode(200).extract().body().as(Tweet.class).isValidated();
        Assertions.assertNotEquals(validated,validatedAfter);
    }

    /**
     * Test 10 check validated tweets.
     */
    @Test
    public void test10checkValidatedTweets() {
        var user = given().when().get(URL + "/tweet/1").then().statusCode(200).extract().body().as(Tweet.class).getUser();
        given().when().get(URL + "/validatedTweets/" + user).then().statusCode(200).and().body("content.id", everyItem(notNullValue()));
    }

    /**
     * Test 11 ranking.
     */
    @Test
    public void test11ranking() {
        given().when().get(URL + "/hashtagRanking/").then().statusCode(200);
    }

    /**
     * Test 12 ranking top.
     */
    @Test
    public void test12rankingTop() {
        var map = given().when().param("top", "15").get(URL + "/hashtagRanking/").then().statusCode(200).extract().body().as(HashMap.class);
        Assertions.assertEquals(15, map.size());
    }


}
