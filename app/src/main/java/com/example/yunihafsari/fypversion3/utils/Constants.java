package com.example.yunihafsari.fypversion3.utils;

/**
 * Created by yunihafsari on 12/03/2017.
 */

public class Constants {

    // firebase
    public static final String ARG_USERS = "users";
    public static final String ARG_RECEIVER = "receiver";
    public static final String ARG_RECEIVER_UID = "receiver_uid";
    public static final String ARG_CHAT_ROOMS = "chat_rooms";
    public static final String ARG_FIREBASE_TOKEN = "firebaseToken";
    public static final String ARG_FRIENDS = "friends";
    public static final String ARG_UID = "uid";

    // chat
    public static final String CHAT_RECEIVER= "chat_receiver";
    public static final String CHAT_UID ="chat_uid";
    public static final String CHAT_FIREBASETOKEN = "chat_firebaseToken";

    // this phone user profile
    public static final String USER_USERNAME = "user_username";
    public static final String USER_PHONE_NUMBER = "user_phone_number";

    // contact friend
    public static final String CONTACT_ID = "contact_id";
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_PHONE_NUMBER = "contact_phone_number";
    public static final String CONTACT_EMAIL = "contact_email";
    public static final String CONTACT_RELATION = "contact_relation";
    public static final String CONTACT_ADDRESS = "contact_address";
    public static final String CONTACT_TWITTER_USERNAME = "contact_twitter_username";
    public static final String CONTACT_INSTAGRAM_USERNAME = "contact_instagram_username";

    // database
    //COLUMNS
    public static final String ROW_ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String RELATION = "relation";
    public static final String ADDRESS = "address";
    public static final String TWITTER_USERNAME = "twitter_username";
    public static final String INSTAGRAM_USERNAME = "instagram_username";
    public static final String FIREBASE = "firebase";
    public static final String IMAGE = "image";

    //column for instagram table
    public static final String INSTAGRAM_TABLE_ID = "id";
    public static final String INSTAGRAM_ID = "instagram_id";
    public static final String INSTAGRAM_FULL_NAME = "full_name";
    public static final String INSTAGRAM_USERNAMEE = "username";

    //DB PROPERTIES
    public static final String DB_NAME = "b_DB";
    public static final String TB_NAME = "b_TB";
    public static final String TB_INSTAGRAM = "b_instagram";
    public static final int DB_VERSION = 10;

    //CREATE TABLE STATEMENT
    public static final String CREATE_TB = "CREATE TABLE b_TB(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "phone_number TEXT NOT NULL," +
            "relation TEXT NOT NULL," +
            "address TEXT NOT NULL," +
            "twitter_username TEXT NOT NULL," +
            "instagram_username TEXT NOT NULL," +
            "Firebase INTEGER NOT NULL," +
            "image BLOB NOT NULL)";


    //CREATE INSTAGRAM TABLE STATEMENT
    public static final String CREATE_INSTAGRAM_TB = "CREATE TABLE b_instagram(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "instagram_id TEXT NOT NULL," +
            "full_name TEXT NOT NULL," +
            "username TEXT NOT NULL)";

    // TWITTER API
    public static final String BASE_URL="https://api.twitter.com";
    public static final String ACCESS_TOKEN = "AAAAAAAAAAAAAAAAAAAAALeozgAAAAAA%2BLCdvNqMnnbz2ZN9SaSsBjJOSJo%3DufvvTQDDOrvrtSi3zst9NQS5sSbn5R0ek494jqTu3qDG3uWxey";
    // GET https://api.twitter.com/1.1/friends/list.json?cursor=-1&screen_name=twitterapi&skip_status=true&include_user_entities=false
    public static final int FOLLOWING_CURSOR = -1;
    public static final boolean FOLLOWING_SKIP_STATUS = true;
    public static final boolean FOLLOWING_INCLUDE_USER_ENTITIES = false;


    // INSTAGRAM API
    public static final String BASE_URL_INSTAGRAM ="https://api.instagram.com";
    public static final String DEVELOPER_TOKEN = "5329403910.cbe7ca6.c819e156366745dab3b3f5af7c5c5417";
    //public static final String DEVELOPER_TOKEN = "1698017712.7309492.56b708d02c864e2e88cdcdee58637565";


    // INSTAGRAM ATTRIBUTES (referring to FYPVersion1 admin by abubakara94_here, member ali and yuni)
    //public static final String BASE_URL="https://api.instagram.com";
    public static final String CLIENT_ID ="cbe7ca613e4f4002a6f2c55a5b57e02f";
    public static final String CLIENT_SECRET ="7dc1cac231c74aff95fa4b9dd3351b18";
    public static final String REDIRECT_URL ="https://www.instagram.com/abubakar94_here/";
    public static final String AUTHERIZATION_CODE ="authorization_code";
    public static final String SCOPE = "follower_list+public_content";

    // ENCRYPTION
    public static final String MESSAGE_ENCRYPTION ="message_encryption";
    public static final String ENCRYPTION_CODE = "encryption_code";


    // LOGIN INSTAGRAM
    public static final String INSTAGRAM_TOKEN = "instagram_token";
    public static final String INSTAGRAM_LOGIN_USERNAME = "instagram_login_username";

    // LOGIN TWITTER
    public static final String TWITTER_KEY = "jtXArs1O7abQDiTwz0AG5s5GT";
    public static final String TWITTER_SECRET = "a9aDeWRZZbVspmG1WFsvwWCzj7FGPAdjP9a1YJ48XFg9HFuMiX";
    public static final String TWITTER_LOGIN_USERNAME = "twitter_login_username";
}
