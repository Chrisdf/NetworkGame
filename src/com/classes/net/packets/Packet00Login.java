package com.classes.net.packets;

import com.classes.net.Client;
import com.classes.net.Server;

/**
 * Created by chris on 2/18/15.
 */
public class Packet00Login extends Packet {

    private String username;

    public Packet00Login(byte[] data) {

        super(00);
        this.username = readData(data);
    }

    public Packet00Login(String username){

        super(00);
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

        return ("00" + this.username).getBytes();
    }
}
