package com.example.yunihafsari.fypversion3.model.apps_model;

/**
 * Created by yunihafsari on 06/04/2017.
 */

public class Contact {

    private int id;
    private String name;
    private String phone_number;
    private String email;
    private String relation;
    private String address;
    private String twitter_username;
    private String instagram_username;
    private boolean isChecked;
    private byte[] image;

    public Contact(){

    }

    public Contact(int id, String name, String phone_number, String email, String relation, String address, String twitter_username, String instagram_username, byte[] image) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.relation = relation;
        this.address = address;
        this.twitter_username = twitter_username;
        this.instagram_username = instagram_username;
        this.image = image;
    }

    public Contact(int id, String name, String phone_number, String email) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    public String getInstagram_username() {
        return instagram_username;
    }

    public void setInstagram_username(String instagram_username) {
        this.instagram_username = instagram_username;
    }
}
