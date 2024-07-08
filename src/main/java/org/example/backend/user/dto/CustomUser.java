package org.example.backend.user.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/* CustomUser 클래스는 user라는 DTO가 사용자 정보를 담고 있는데
   해당 정보(형식)를 스프링시큐리티한테 맞는 형식으로 넘겨주기 위한 용도로 생성한 클래스
 */
@Data
public class CustomUser implements UserDetails {

    private User user;

    public CustomUser(User user) {
        this.user = user;
    }

    /* 권한을 응답해주는것(필수기능)
       권한 getter 메소드
       List<UserAuth> --> Collection<SimpleGrantedAuthority> (auth) 변환하여 해당 형태로 사용
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserAuth> authList = user.getAuthList(); // UserAuth (AuthNo, userId, auth) 이 세가지를 알려줘야 하고,

        // SimpleGrantedAuthority() - "ROLE_USER", 해당 정보만 알려주면 됨
        Collection<SimpleGrantedAuthority> roleList = authList.stream()
                                .map((auth) -> new SimpleGrantedAuthority(auth.getAuth()))
                                .collect(Collectors.toList());
        return roleList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
