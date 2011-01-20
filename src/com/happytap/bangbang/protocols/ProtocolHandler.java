package com.happytap.bangbang.protocols;

import com.happytap.bangbang.BangBang;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/16/11
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProtocolHandler<T> {

    private BangBang bangbang;

    public ProtocolHandler(BangBang bangbang) {
        this.bangbang = bangbang;
    }

    public abstract void onPacket(byte instruction, T data);

}
