package com.github.gmcoringa.coordinator.core.zookeeper.command;

import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@Component
public class ZookeeperCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperCommand.class);

    private final int timeoutMS;

    @Autowired
    public ZookeeperCommand(@Value("${zookeeper.connection.timeoutMS:15000}") int timeout) {
        this.timeoutMS = timeout;
    }

    public CommandResponse performCommand(Word word, String host, int port) {

        try (Socket socket = new Socket(host, port)) {
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(timeoutMS);

            socket.getOutputStream().write(word.name().toLowerCase().getBytes());
            socket.getOutputStream().flush();

            String response = CharStreams.toString(new InputStreamReader(socket.getInputStream()));
            return CommandResponse.ok(response);
        } catch (SocketException | UnknownHostException e) {
            LOG.debug("Failed to connect Zookeeper instance: {}:{}.", host, port, e);
            return CommandResponse.error();
        } catch (SocketTimeoutException e) {
            LOG.debug("Connection timeout from {}.", host, e);
            return CommandResponse.timeout();
        } catch (IOException e) {
            LOG.debug("Failed to read response from {}.", host, e);
            return CommandResponse.ioError();
        }
    }

}
