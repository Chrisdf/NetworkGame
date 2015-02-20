package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;

/**
 * Created by chris on 2/18/15.
 */
public class Packet01Disconnect extends Packet {

    private String username;

    public Packet01Disconnect(byte[] data) {

        super(01);
        this.username = readData(data);
    }

    public Packet01Disconnect(String username) {

        super(01);
        this.username = username;
    }

    @Override
    public void writeData(Client client) {

        client.sendData(getData());
    }

    @Override
    public void writeData(Server server) {

        server.globalSendData(getData());
    }

    @Override
    public byte[] getData() {

        return ("01" + this.username).getBytes();
    }

    public String getUsername() {

        return username;
    }
}
