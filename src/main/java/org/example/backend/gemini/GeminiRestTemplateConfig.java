package org.example.backend.gemini;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //하나이상의 bean 메서드를 정의 하여 ioc 컨테이너에서 빈 정의를 사용된다는것을 나타냄
@RequiredArgsConstructor// Lombok 라이브러리에서 제공하는 어노테이션으로, 클래스에 선언된 final 필드와 @NonNull 필드를 매개변수로 하는 생성자를 자동으로 생성합니다. 이를 통해 코드를 간결하게 유지할 수 있으며, 의존성 주입을 위한 생성자를 쉽게 만들 수 있습니다.
public class GeminiRestTemplateConfig {
    @Bean
    @Qualifier("geminiRestTemplate")
    public RestTemplate geminiRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> execution.execute(request, body));

        return restTemplate;

    }
}
