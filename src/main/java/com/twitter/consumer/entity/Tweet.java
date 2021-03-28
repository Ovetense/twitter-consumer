package com.twitter.consumer.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

/**
 * The type Tweet.
 */
@Entity
public class Tweet extends RepresentationModel<Tweet> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    public String location;
    @Column
    public String text;
    @Column
    public boolean validated;
    @Column
    public String user;

    /**
     * Instantiates a new Tweet.
     */
    public Tweet() {
    }

    /**
     * Instantiates a new Tweet.
     *
     * @param location  the location
     * @param text      the text
     * @param validated the validated
     * @param user      the user
     */
    public Tweet(String location, String text, boolean validated, String user) {
        this.location = location;
        this.text = text;
        this.validated = validated;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", text='" + text + '\'' +
                ", validated=" + validated +
                ", user='" + user + '\'' +
                '}';
    }
}
