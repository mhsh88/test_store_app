package ir.sharifi.soroush.soroush_test_project.route;


import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.soroushbot.models.FileType;
import org.apache.camel.component.soroushbot.models.MinorType;
import org.apache.camel.component.soroushbot.models.SoroushMessage;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@MockEndpoints
@RunWith(CamelSpringBootRunner.class)
class MainRouteTest {


    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(value = "{{soroush.bot.producer}}")
    private MockEndpoint endpoint;


    @Test
    @DirtiesContext
    void checkBotStart() throws InterruptedException {
        SoroushMessage body = new SoroushMessage();
        body.setFrom("1234");
        endpoint.expectedBodiesReceived(body);
        producerTemplate.sendBody("{{soroush.bot.consumer}}", body);
        endpoint.assertIsSatisfied();

        SoroushMessage receivedBody = endpoint.getExchanges().get(0).getIn().getBody(SoroushMessage.class);
        assertEquals(SoroushMessage.class, receivedBody.getClass());
        assertEquals(receivedBody.getFrom(), receivedBody.getTo());

    }

}