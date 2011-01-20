package com.happytap.bangbang.protocols;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/16/11
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BangBangProtocol {

    private BangBangProtocol() {}

    /**
     * start a new game
     */
    public static final byte NEW_GAME = 0;

    /**
     * client is ready
     */
    public static final byte CLIENT_READY = 1;

    /**
     * shot fired
     */
    public static final byte SHOT_FIRED = 10;

    /**
     *
     */
    public static final byte SHOT_LANDED = 11;

}
