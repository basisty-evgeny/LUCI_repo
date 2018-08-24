package com.luci.util;

public class Constant {
    /**
     * debug mode in development
     */
    public static final boolean DEBUG = false;

    /**
     * private user information repository identification
     */
    public static final String USERINFO_ID = "luci_userinfo";

    /**
     * api backend server configuration
     */
    public static final String HOST = "http://192.168.2.43:8000";
    public static final String BASE_URL = "/";
    public static String getAPIBaseUrl() {
        return HOST + BASE_URL;
    }

    /**
     * url list
     */
    public static final String URL_SIGNUP = "signup";
    public static final String URL_SIGNIN = "signin";
    public static final String URL_LISTCHANNEL = "channel/list";
    public static final String URL_CREATECHANNEL = "channel/create";
    public static final String URL_UPDATECHANNEL = "channel/update";
    public static final String URL_DELETECHANNEL = "channel/delete";

    /**
     * Icecast host
     */
    public static final String ICE_HOST    = "192.168.2.137";

    /**
     * Broadcast port that server listens incoming streams
     */
    public static final int    ICE_PORT    = 8002;

    /**
     * Mount point of incoming source
     */
    public static final String ICE_MOUNT   = "/mountpoint.ogg";

    /**
     * Credentials
     */
    public static final String ICE_USER    = "user1";
    public static final String ICE_PASS    = "pass1";
}
