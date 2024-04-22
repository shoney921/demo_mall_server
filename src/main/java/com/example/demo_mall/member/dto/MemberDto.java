package com.example.demo_mall.mallapi.dto;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
public class MemberDto extends User {

    private Long id;

    private Long kakaoId;

    private String email;

    private String mobile;

    private String password;

    private String nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();

    public MemberDto(Long id, Long kakaoId, String email, String mobile, String password, String nickname, boolean social, List<String> roleNames) {
        //super(username, password, authorities);
        super(id.toString(), password, roleNames.stream().map(roleName ->
                new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList()));
        this.id = id;
        this.kakaoId = kakaoId;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        dataMap.put("email", email);
        dataMap.put("mobile", mobile);
        dataMap.put("password", password);
        dataMap.put("nickname", nickname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }
}
