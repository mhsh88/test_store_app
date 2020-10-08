package ir.sharifi.soroush.soroush_test_project.route;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
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
        onException(Exception.class)
                .handled(true)
                .process(exchange -> {
                    Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    SoroushMessage soroushMessage = exchange.getIn().getBody(SoroushMessage.class);
                    soroushMessage.setBody(exception.getLocalizedMessage());
                    soroushMessage.setType(MinorType.TEXT);
                    soroushMessage.setTo(soroushMessage.getFrom());
                }).to(producer);


        from(consumer)
                .noStreamCaching()
                .process(exchange -> {
                    SoroushMessage soroushMessage = exchange.getIn().getBody(SoroushMessage.class);
                    soroushMessage.setTo(soroushMessage.getFrom());
                    if (Pattern.matches("http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?", soroushMessage.getBody())) {
//                        String command = "youtube-dl --proxy socks5://localhost:9050 " + soroushMessage.getBody();
                        String command = "dpkg -l | grep -qw youtube-dl || apt-get install youtube-dl ";

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
//                        st.append(System.getProperty("os.name")).append("\n");
//                        st.append(System.getProperty("os.arch")).append("\n");
//                        st.append(System.getProperty("os.version")).append("\n");
                        soroushMessage.setType(MinorType.TEXT);
                        soroushMessage.setBody(st.toString());

                    }
                }).to(producer);

        rest("/")
                .get().produces("application/json").route().transform().constant("Hello World!");

        restConfiguration()
                .component("netty-http")
                .host("0.0.0.0")
                .port(System.getenv("PORT"))
                .bindingMode(RestBindingMode.auto);

    }


}
