package ir.sharifi.soroush.soroush_test_project.route;


import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sharifi.soroush.soroush_test_project.model.BotFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.soroushbot.models.FileType;
import org.apache.camel.component.soroushbot.models.MinorType;
import org.apache.camel.component.soroushbot.models.SoroushMessage;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.apache.http.entity.ContentType.MULTIPART_FORM_DATA;

@Component
@Slf4j
public class MainRoute extends RouteBuilder {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
                    String header = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
                    if(Objects.nonNull(header) && !header.isEmpty()){
                        File file = new File(header);
                        file.delete();
                    }
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
//                        String command = "youtube-dl -f 22 --proxy socks5://localhost:9050 " + soroushMessage.getBody();

                        ProcessBuilder builder = new ProcessBuilder();
//                        builder.command("youtube-dl", "-f", "18", "--proxy", "socks5://localhost:9050", "--get-filename", soroushMessage.getBody());
                        builder.command("youtube-dl", "-f", "18", "--get-filename", soroushMessage.getBody());
//                            builder.command(command);
//                        builder.directory(new File(System.getProperty("user.home")));
                        Process process = builder.start();

                        String fileName = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().findFirst().orElse("");

//                        builder.command("youtube-dl", "-f", "18", "--proxy", "socks5://localhost:9050", soroushMessage.getBody());
                        builder.command("youtube-dl", "-f", "18", soroushMessage.getBody());
                        process = builder.start();
                        StreamGobbler streamGobbler =
                                new StreamGobbler(process.getInputStream(), log::info);
                        Executors.newSingleThreadExecutor().submit(streamGobbler);
                        int exitCode = process.waitFor();
                        assert exitCode == 0;
//
//                        Process proc = Runtime.getRuntime().exec(command);
//
//                        // Read the output
//
//                        BufferedReader reader =
//                                new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//                        String line = "";
//                        StringBuilder st = new StringBuilder();
//                        while ((line = reader.readLine()) != null) {
//                            log.info(line);
//                            st.append(line).append("\n");
//                        }
//
//                        proc.waitFor();
//                        st.append(System.getProperty("os.name")).append("\n");
//                        st.append(System.getProperty("os.arch")).append("\n");
//                        st.append(System.getProperty("os.version")).append("\n");
                        soroushMessage.setType(MinorType.TEXT);
//                        soroushMessage.setBody(st.toString());
                        soroushMessage.setBody(fileName);
                        exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
                        exchange.getIn().setHeader("custom_header", soroushMessage);

                    }
                })
                .process(exchange -> {
                    MultipartEntityBuilder multipartEntityBuilder =
                            MultipartEntityBuilder.create();
//                    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                    String filename = (String)
                            exchange.getIn().getHeader(Exchange.FILE_NAME);

//                    File file = exchange.getIn().getBody(File.class);
                    File file = new File(filename);
//                    exchange.getIn().setHeader("custom_file_object",file);
                    if(!file.exists()){
                        throw new FileNotFoundException("file not found");
                    }

                    multipartEntityBuilder.addPart("file",
                            new FileBody(file, MULTIPART_FORM_DATA, filename));

                    exchange.getIn().setBody(multipartEntityBuilder.build());
                })

                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data"))

                .to("https://bot.sapp.ir/{{soroush.bot.token}}/uploadFile")

                .process(exchange -> {
                    SoroushMessage soroushMessage = (SoroushMessage) exchange.getIn().getHeader("custom_header");
                    String body = exchange.getIn().getBody(String.class);
                    BotFileResponse botFileResponse = objectMapper.readValue(body, BotFileResponse.class);
                    String filename = (String)
                            exchange.getIn().getHeader(Exchange.FILE_NAME);
                    File file = new File(filename);
                    if(botFileResponse.getResultCode() == 200){
                        soroushMessage.setType(MinorType.FILE);
                        soroushMessage.setFileType(FileType.ATTACHMENT);
                        soroushMessage.setFileUrl(botFileResponse.getFileUrl());
                        soroushMessage.setFileName((String) exchange.getIn().getHeader(Exchange.FILE_NAME));

                        soroushMessage.setFileSize((double) file.length());
                        soroushMessage.setTo(soroushMessage.getFrom());
                    }soroushMessage.setTo(soroushMessage.getFrom());


                    exchange.getIn().setHeaders(new HashMap<>());
                    exchange.getIn().setBody(soroushMessage);
                    try {
                        file.delete();
                    }catch (Exception ignored){

                    }

                })
                .to(producer);


        rest("/")
                .get().produces("application/json").route().transform().constant("Hello World!");

        restConfiguration()
                .component("netty-http")
                .host("0.0.0.0")
                .port(System.getenv("PORT"))
                .bindingMode(RestBindingMode.auto);

    }


}

class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
