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
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
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
    private static final DecimalFormat df2 = new DecimalFormat("#.##");

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
                    if (Objects.nonNull(header) && !header.isEmpty()) {
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
                        builder.command("youtube-dl", "-f", "18", "--proxy", "socks5://localhost:9050", "--get-filename", soroushMessage.getBody());
//                        builder.command("youtube-dl", "-f", "22", "--get-filename", soroushMessage.getBody());
//                            builder.command(command);
//                        builder.directory(new File(System.getProperty("user.home")));
                        Process process = builder.start();

                        String fileName = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().findFirst().orElse("");

                        builder.command("youtube-dl", "-f", "18", "--proxy", "socks5://localhost:9050", soroushMessage.getBody());
//                        builder.command("youtube-dl", "-f", "22", soroushMessage.getBody());
                        process = builder.start();
                        StreamGobbler streamGobbler =
                                new StreamGobbler(process.getInputStream(), log::info);
                        Executors.newSingleThreadExecutor().submit(streamGobbler);
                        int exitCode = process.waitFor();
                        assert exitCode == 0;

                        soroushMessage.setType(MinorType.TEXT);
                        soroushMessage.setBody(fileName);
                        exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
                        exchange.getIn().setHeader("custom_header", soroushMessage);

                    }
                })
                .process(exchange -> {
                    MultipartEntityBuilder multipartEntityBuilder =
                            MultipartEntityBuilder.create();

                    String filename = (String)
                            exchange.getIn().getHeader(Exchange.FILE_NAME);

                    File file = new File(filename);
                    long totalSize = file.length();
                    if (!file.exists()) {
                        throw new FileNotFoundException("file not found");
                    }

                    multipartEntityBuilder.addPart("file",
                            new FileBody(file, MULTIPART_FORM_DATA, filename));

                    HttpEntity yourEntity = multipartEntityBuilder.build();

                    class ProgressiveEntity implements HttpEntity {
                        @Override
                        public void consumeContent() throws IOException {
                            yourEntity.consumeContent();
                        }

                        @Override
                        public InputStream getContent() throws IOException,
                                IllegalStateException {
                            return yourEntity.getContent();
                        }

                        @Override
                        public Header getContentEncoding() {
                            return yourEntity.getContentEncoding();
                        }

                        @Override
                        public long getContentLength() {
                            return yourEntity.getContentLength();
                        }

                        @Override
                        public Header getContentType() {
                            return yourEntity.getContentType();
                        }

                        @Override
                        public boolean isChunked() {
                            return yourEntity.isChunked();
                        }

                        @Override
                        public boolean isRepeatable() {
                            return yourEntity.isRepeatable();
                        }

                        @Override
                        public boolean isStreaming() {
                            return yourEntity.isStreaming();
                        } // CONSIDER put a _real_ delegator into here!

                        @Override
                        public void writeTo(OutputStream outstream) throws IOException {

                            class ProxyOutputStream extends FilterOutputStream {

                                public ProxyOutputStream(OutputStream proxy) {
                                    super(proxy);
                                }

                                public void write(int idx) throws IOException {
                                    out.write(idx);
                                }

                                public void write(byte[] bts) throws IOException {
                                    out.write(bts);
                                }

                                public void write(byte[] bts, int st, int end) throws IOException {
                                    out.write(bts, st, end);
                                }

                                public void flush() throws IOException {
                                    out.flush();
                                }

                                public void close() throws IOException {
                                    out.close();
                                }
                            }

                            class ProgressiveOutputStream extends ProxyOutputStream {
                                long totalSent;
                                final long startTime;
                                long endTime;


                                public ProgressiveOutputStream(OutputStream proxy) {
                                    super(proxy);
                                    totalSent = 0;
                                    startTime = System.currentTimeMillis();
                                }

                                public void write(byte[] bts, int st, int end) throws IOException {


                                    totalSent += end;
                                    endTime = System.currentTimeMillis();
                                    double megaByte = totalSent * 1.0 / 1024 / 1024;
                                    log.info("upload file progress: {} mb {} kb/s overall speed {} % uploaded",
                                            df2.format(megaByte),
                                            df2.format(megaByte * 1000 * 1024 * 1.0 /(endTime - startTime)),
                                            df2.format((totalSent * 1.0 / totalSize) * 100));


                                    out.write(bts, st, end);
                                }
                            }

                            yourEntity.writeTo(new ProgressiveOutputStream(outstream));
                        }

                    }
                    ProgressiveEntity myEntity = new ProgressiveEntity();
                    exchange.getIn().setBody(myEntity);
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
                    if (botFileResponse.getResultCode() == 200) {
                        soroushMessage.setType(MinorType.FILE);
                        soroushMessage.setFileType(FileType.ATTACHMENT);
                        soroushMessage.setFileUrl(botFileResponse.getFileUrl());
                        soroushMessage.setFileName((String) exchange.getIn().getHeader(Exchange.FILE_NAME));

                        soroushMessage.setFileSize((double) file.length());
                        soroushMessage.setTo(soroushMessage.getFrom());
                    }
                    soroushMessage.setTo(soroushMessage.getFrom());


                    exchange.getIn().setHeaders(new HashMap<>());
                    exchange.getIn().setBody(soroushMessage);
                    try {
                        file.delete();
                    } catch (Exception ignored) {

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
    private final InputStream inputStream;
    private final Consumer<String> consumer;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
