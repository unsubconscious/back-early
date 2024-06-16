package org.example.backend.user.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("org.example.backend") // application.properties 의 하위 속성 경로 지정
public class JwtProps {

    // 🔐시크릿키 : JWT 시그니처 암호화를 위한 정보
    private String secretKey;
}
