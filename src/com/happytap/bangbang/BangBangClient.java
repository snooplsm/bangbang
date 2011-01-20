package com.happytap.bangbang;


import com.happytap.bangbang.protocols.BangBangProtocol;
import com.happytap.bangbang.protocols.NewGameProtocolHandler;
import com.happytap.bangbang.protocols.ProtocolHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class BangBangClient {

    private InputStream inputStream;

    private OutputStream outputStream;

    private BangBang bangBang;

    private boolean running;

    private byte[] instructions;

    private byte[] initialRead = new byte[1+2];

    private Map<Byte,ProtocolHandler> protocolHandlers = new HashMap<Byte, ProtocolHandler>();

    public BangBangClient(InputStream inputStream, OutputStream outputStream, BangBang bangbang) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.bangBang = bangBang;
        initializeProtocolHandlers();
    }

    protected void initializeProtocolHandlers() {
        protocolHandlers.put(BangBangProtocol.NEW_GAME, new NewGameProtocolHandler(null));
    }

    private void handleInitialReadError(IOException e) {

    }

    public void run() {
        while(running) {
            try {
                int read = inputStream.read(initialRead);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(initialRead));
                byte instruction = ois.readByte();
                short size = ois.readShort();
                ois.close();
                byte[] packet = new byte[(int)size];
                read = inputStream.read(packet);
                ois = new ObjectInputStream(new ByteArrayInputStream(packet));
                try {
                    Object data = ois.readObject();
                    ProtocolHandler handler = protocolHandlers.get(instruction);
                    handler.onPacket(instruction,packet);
                } catch (ClassNotFoundException e) {
                    handleClassNotFoundException(e);
                } catch (IOException ex) {
                    handleObjectReadError(ex);
                }
            } catch (IOException e) {
                handleInitialReadError(e);
            }
        }
    }

    private void handleObjectReadError(IOException ex) {
    }

    private void handleClassNotFoundException(ClassNotFoundException e) {
    }

}
