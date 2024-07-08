package org.example.backend.gemini;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data //케터 세터 기타등등 다 모은거
@AllArgsConstructor //인자가 있는 생성자
@NoArgsConstructor  //기본생성자 만들기
@Builder //
public class ChatRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content {
        private Parts parts;
    }

    @Getter @Setter
    public static class Parts {
        private String text;

    }

    @Getter @Setter
    public static class GenerationConfig {
        private int candidate_count;
        private int max_output_tokens;
        private double temperature;

    }

    public ChatRequest(String prompt) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Parts parts = new Parts();

        parts.setText(prompt);
        content.setParts(parts);

        this.contents.add(content);
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(1);
        this.generationConfig.setMax_output_tokens(800);
        this.generationConfig.setTemperature(0.7);
    }
}
