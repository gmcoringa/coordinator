package com.github.gmcoringa.coordinator.core.zookeeper.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandServiceTest {

    @Mock
    private ZookeeperCommand command;

    @InjectMocks
    private CommandService commandService;

    @Test
    public void shouldBeAliveWhenReturnIsAMOK() {
        when(command.performCommand(Word.RUOK, "localhost", 2181)).thenReturn(CommandResponse.ok("imok"));
        Assert.assertTrue(commandService.isAlive("localhost", 2181));
    }

    @Test
    public void shouldNotBeAliveWhenReturnIsNotAMOK() {
        when(command.performCommand(Word.RUOK, "localhost", 2181)).thenReturn(null);
        assertFalse(commandService.isAlive("localhost", 2181));
    }

}
