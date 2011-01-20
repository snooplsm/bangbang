package com.happytap.bangbang.protocols;

import com.happytap.bangbang.BangBang;
import com.happytap.bangbang.domain.NewGameRequest;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/16/11
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewGameProtocolHandler extends ProtocolHandler<NewGameRequest> {

    public NewGameProtocolHandler(BangBang bangbang) {
        super(bangbang);
    }

    @Override
    public void onPacket(byte instruction, NewGameRequest newGameRequest) {

    }
}
