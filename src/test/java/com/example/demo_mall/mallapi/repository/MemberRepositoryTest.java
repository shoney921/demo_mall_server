package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Member;
import com.example.demo_mall.mallapi.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .nickname("USER" + i)
                    .password(passwordEncoder.encode("1111"))
                    .mobile("01023232" + i)
                    .build();

            member.addRole(MemberRole.USER);
            if (i > 5) {
                member.addRole(MemberRole.MANAGER);
            }
            if (i > 7) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        }
    }

    @Test
    public void testRead() {
        Long id = 1L;

        Member withRoles = memberRepository.getWithRoles(id);

        log.info(withRoles);
        log.info(withRoles.getMemberRoleList());
    }

    @Test
    public void getEmailByNameAndMobile_ValidInput_ReturnsEmail_RealDB() {
        // Given
        String name = "이상헌";
        String mobile = "01066876050";
        String expectedEmail = "ddmczp@naver.com";

        // When
        String actualEmail = memberRepository.getEmailByNameAndMobile(name, mobile).orElseThrow();

        // Then
        assertEquals(expectedEmail, actualEmail);
    }


    @Test
    public void getEmailByNameAndMobile_ValidInput_ReturnsEmail() {
        // Given
        String name = "이상헌";
        String mobile = "01066876050";
        String expectedEmail = "example@example.com";

        MemberRepository memberRepositoryMock = mock(MemberRepository.class);
        when(memberRepositoryMock.getEmailByNameAndMobile(name, mobile)).thenReturn(Optional.of(expectedEmail));

        // When
        String actualEmail = memberRepositoryMock.getEmailByNameAndMobile(name, mobile).orElseThrow();

        // Then
        assertEquals(expectedEmail, actualEmail);
        verify(memberRepositoryMock, times(1)).getEmailByNameAndMobile(name, mobile);
    }

    @Test
    public void getEmailByNameAndMobile_InvalidInput_ReturnsNull() {
        // Given
        String name = "InvalidName";
        String mobile = "InvalidMobile";

        MemberRepository memberRepositoryMock = mock(MemberRepository.class);
        when(memberRepositoryMock.getEmailByNameAndMobile(name, mobile)).thenReturn(null);

        // When
        String actualEmail = memberRepositoryMock.getEmailByNameAndMobile(name, mobile).orElseThrow();

        // Then
        assertEquals(null, actualEmail);
        verify(memberRepositoryMock, times(1)).getEmailByNameAndMobile(name, mobile);
    }
}