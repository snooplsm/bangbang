package com.happytap.bangbang;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/16/11
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BangBangServer extends BangBangClient {

    public BangBangServer(InputStream inputStream, OutputStream outputStream, BangBang bangbang) {
        super(inputStream, outputStream, bangbang);
    }
}
