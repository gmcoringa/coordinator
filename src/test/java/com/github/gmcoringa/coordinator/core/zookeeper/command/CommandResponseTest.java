package com.github.gmcoringa.coordinator.core.zookeeper.command;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.Status;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandResponseTest {

    private CommandResponse subject;

    @Test
    public void shouldReturnStatusUnstableWhenResponseContainsUnstableMessage() {
        String lines = "\nsome content\nother:\n" + CommandResponse.UNSTABLE_SERVER_MESSAGE;

        subject = new CommandResponse(ResponseStatus.OK, lines);
        Map<String, String> responseMap = subject.getResponseMap();
        assertEquals(Status.UNSTABLE.name(), responseMap.get("status"));
    }

    @Test
    public void shouldReturnStatusLiveWhenResponseDoNotContainsUnstableMessage() {
        String lines = "\nsome content\nother:\n";

        subject = new CommandResponse(ResponseStatus.OK, lines);
        Map<String, String> responseMap = subject.getResponseMap();
        assertEquals(Status.LIVE.name(), responseMap.get("status"));
    }

    @Test
    public void shouldReturnMapWithStatusOnlyWhenThereIsNoValidLines() {
        String lines = "\nsome content\nother:\nempty";

        subject = new CommandResponse(ResponseStatus.OK, lines);
        Map<String, String> responseMap = subject.getResponseMap();
        assertTrue(responseMap.containsKey("status"));
        assertEquals(1, responseMap.size());
    }

    @Test
    public void shouldReturnMapWithStatusUnstableOnlyWhenResponseIsNull() {
        subject = new CommandResponse(ResponseStatus.OK, null);
        Map<String, String> responseMap = subject.getResponseMap();
        assertEquals(Status.UNSTABLE.name(), responseMap.get("status"));
        assertEquals(1, responseMap.size());
    }

    @Test
    public void shouldReturnMapWithStatusUnstableOnlyWhenResponseIsEmpty() {
        subject = new CommandResponse(ResponseStatus.OK, null);
        Map<String, String> responseMap = subject.getResponseMap();
        assertEquals(Status.UNSTABLE.name(), responseMap.get("status"));
        assertEquals(1, responseMap.size());
    }

    @Test
    public void shouldReturnEntriesWhenResponseAreValid() {
        String lines = "Zookeeper version: 3.4.6-1569965, built on 02/20/2014 09:09 GMT\n"
                + "Latency min/avg/max: 0/0/0\n"
                + "Zxid: 0x2c\n"
                + "Mode: standalone\n";

        subject = new CommandResponse(ResponseStatus.OK, lines);
        Map<String, String> responseMap = subject.getResponseMap();
        assertEquals(5, responseMap.size());
        assertTrue(responseMap.containsKey("Zookeeper version"));
        assertTrue(responseMap.containsKey("Latency min/avg/max"));
        assertTrue(responseMap.containsKey("Zxid"));
        assertTrue(responseMap.containsKey("Mode"));
        assertTrue(responseMap.containsKey("status"));
    }
}
