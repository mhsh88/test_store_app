package ir.sharifi.soroush.soroush_test_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BotFileResponse {
    private String fileUrl;
    private String resultMessage;
    private int resultCode;
}
