package ir.sharifi.soroush.soroush_test_project.route;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.soroushbot.models.SoroushMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainRoute extends RouteBuilder {

    @Value("${soroush.bot.consumer}")
    private String consumer;

    @Value("${soroush.bot.producer}")
    private String producer;

    @Override
    public void configure() {


        from(consumer)
                .noStreamCaching()
                .process(exchange -> {
                    SoroushMessage soroushMessage = exchange.getIn().getBody(SoroushMessage.class);
                    soroushMessage.setTo(soroushMessage.getFrom());

                }).to(producer);

    }


}
