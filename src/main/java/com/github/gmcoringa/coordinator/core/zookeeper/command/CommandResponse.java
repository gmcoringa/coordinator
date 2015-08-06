package com.github.gmcoringa.coordinator.core.zookeeper.command;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.Status;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandResponse {

    private static final Logger LOG = LoggerFactory.getLogger(CommandResponse.class);
    static final String UNSTABLE_SERVER_MESSAGE = "not currently serving";

    private final String response;
    private final ResponseStatus status;

    CommandResponse(ResponseStatus status, String response) {
        this.status = status;
        this.response = Strings.nullToEmpty(response);
    }

    static CommandResponse ok(String response) {
        return new CommandResponse(ResponseStatus.OK, response);
    }

    static CommandResponse timeout() {
        return new CommandResponse(ResponseStatus.TIMEOUT, null);
    }

    static CommandResponse error() {
        return new CommandResponse(ResponseStatus.CONNECTION_ERROR, null);
    }

    static CommandResponse ioError() {
        return new CommandResponse(ResponseStatus.IO_ERROR, null);
    }

    public boolean isOk() {
        return status == ResponseStatus.OK;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public Map<String, String> getResponseMap() {
        return getLines(this.response)
                .stream()
                .filter(this::isValidLine)
                .map(this::lineToTuple)
                .collect(Collectors.toMap(Tuple::getKey, Tuple::getValue));
    }

    private List<String> getLines(String response) {
        List<String> lines = new ArrayList<>();

        // Zookeeper down, return down state
        if (response.contains(UNSTABLE_SERVER_MESSAGE) || response.trim().isEmpty()) {
            lines.add("status:" + Status.UNSTABLE.name());
        } else {
            lines.add("status:" + Status.LIVE.name());
        }

        if(response.isEmpty()){
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(response))){
            String line;
            while ((line = reader.readLine()) != null) {

                if (Strings.isNullOrEmpty(line)) {
                    continue;
                }

                lines.add(line);
            }
        } catch (IOException e) {
            // should never happen
            LOG.debug("Failed to read lines", e);
        }

        return lines;
    }

    private boolean isValidLine(String line) {
        int colonIndex = line.indexOf(':');

        return colonIndex > 0 && (colonIndex + 1) < line.length();
    }

    private Tuple<String, String> lineToTuple(String line) {
        int colonIndex = line.indexOf(':');
        String key = line.substring(0, colonIndex);
        String value = line.substring(colonIndex + 1);

        return new Tuple<>(key, value);
    }

    private static class Tuple<K, V> {
        private final K key;
        private final V value;

        public Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

}
