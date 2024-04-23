package com.example.demo_mall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String mobile;

    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void addRole(MemberRole memberRole) {
        memberRoleList.add(memberRole);
    }

    public void clearRole() {
        memberRoleList.clear();
    }

    public void changPw(String pw) {
        this.password = pw;
    }

    public void changeSocial(boolean social) {
        this.social = social;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeId(Long id) {
        this.id = id;
    }

    public void changeMobile(String mobile) {
        this.mobile = mobile;
    }

    public void changeName(String name) {
        this.name = name;
    }

}
