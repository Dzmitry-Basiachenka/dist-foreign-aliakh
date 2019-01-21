package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertSame;

import com.copyright.rup.dist.common.test.TestUtils;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.spring.SpringCamelContext;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Verifies {@link SnsNotificationMessageConverter}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 17/01/19
 *
 * @author Pavel Liakh
 */
public class SnsNotificationMessageConverterTest {

    private SnsNotificationMessageConverter snsNotificationMessageConverter;
    private String snsNotificationBody;

    @Before
    public void setUp() {
        snsNotificationMessageConverter = new SnsNotificationMessageConverter();
        snsNotificationMessageConverter.initObjectMapper();
        snsNotificationBody = TestUtils.fileToString(this.getClass(), "sns_notification_message.json");
    }

    @Test
    public void testConvert() throws IOException {
        Exchange exchange = new DefaultExchange(new SpringCamelContext());
        Message in = new DefaultMessage(new SpringCamelContext());
        in.setBody(snsNotificationBody);
        exchange.setIn(in);
        assertNotSame(in, exchange.getOut());
        snsNotificationMessageConverter.convert(exchange);
        Message actualOut = exchange.getOut();
        assertSame(in, actualOut);
        assertEquals("{\"details\":[{\"rh_account_number\":\"1000009256\"}]}",
            actualOut.getBody());
        Map<String, Object> actualHeaders = actualOut.getHeaders();
        assertNotNull(actualHeaders.get("source"));
        assertEquals("FDA", actualHeaders.get("source"));
    }
}
