package ir.sharifi.soroush.soroush_test_project.route;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.soroushbot.models.MinorType;
import org.apache.camel.component.soroushbot.models.SoroushMessage;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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
                    if(Pattern.matches("http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?",soroushMessage.getBody())) {
                        String command = "youtube-dl -f 22 --proxy socks5://127.0.0.1:9050/ " + soroushMessage.getBody();

                        Process proc = Runtime.getRuntime().exec(command);

                        // Read the output

                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(proc.getInputStream()));

                        String line = "";
                        StringBuilder st = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            log.info(line);
                            st.append(line).append("\n");
                        }

                        proc.waitFor();
                        soroushMessage.setType(MinorType.TEXT);
                        soroushMessage.setBody(st.toString());

                    }
                }).to(producer);

        rest("/")
                .get().route().transform().constant("Hello World!");

        restConfiguration()
                .component("netty-http")
                .host("0.0.0.0")
                .port(8080)
                .bindingMode(RestBindingMode.auto);

    }


}
