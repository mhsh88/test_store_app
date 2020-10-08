package ir.sharifi.soroush.soroush_test_project;

import ir.sharifi.soroush.soroush_test_project.model.ResponseType;
import org.springframework.stereotype.Component;

@Component
public class HelloBean {
    public ResponseType sayHello() {
        return new ResponseType("Hello, world!");
    }
}