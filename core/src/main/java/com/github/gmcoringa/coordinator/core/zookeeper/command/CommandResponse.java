package com.github.gmcoringa.coordinator.core.zookeeper.command;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.Status;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandResponse {

    private static final Logger LOG = LoggerFactory.getLogger(CommandResponse.class);

    private final String response;
    private final ResponseStatus status;

    CommandResponse(ResponseStatus status, String response) {
        this.status = status;
        this.response = response;
    }

    static final CommandResponse ok(String response) {
        return new CommandResponse(ResponseStatus.OK, response);
    }

    static final CommandResponse timeout() {
        return new CommandResponse(ResponseStatus.TIMEOUT, null);
    }

    static final CommandResponse error() {
        return new CommandResponse(ResponseStatus.CONNECTION_ERROR, null);
    }

    static final CommandResponse ioError() {
        return new CommandResponse(ResponseStatus.IO_ERROR, null);
    }

    public ResponseStatus getStatus(){
        return status;
    }

    public String getResponse() {
        return response;
    }

    public Map<String, String> getResponseMap() {
        List<String> lines = getLines(this.response);
        Map<String, String> responseMap = new HashMap<>(lines.size() * 2);

        for (String line : lines) {
            int colonIndex = line.indexOf(':');

            if (colonIndex > 0) {
                String key = line.substring(0, colonIndex);
                String value = "";
                boolean hasValue = (colonIndex + 1) < line.length();

                if(hasValue){
                    value = line.substring(colonIndex + 1);
                }

                responseMap.put(key, value);
            }
        }

        return responseMap;
    }

    public boolean isOk(){
        return status == ResponseStatus.OK;
    }

    private List<String> getLines(String response) {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new StringReader(response));
        String line = null;

        // Zookeeper down, return down state
        if(response.contains("not currently serving")){
            lines.add("status:" + Status.UNSTABLE.name());
        } else {
            lines.add("status:" + Status.LIVE.name());
        }

        try {
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

}
