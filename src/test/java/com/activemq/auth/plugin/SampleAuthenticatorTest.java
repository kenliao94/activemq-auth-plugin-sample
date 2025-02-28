package com.activemq.auth.plugin;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SampleAuthenticatorTest {

    @Mock
    private Broker mockBroker;

    @Mock
    private ProducerBrokerExchange mockProducerExchange;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testInstallPlugin() throws Exception {
    }
}