package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.demo.dto.MemberDTO;
import jakarta.persistence.*;
import com.example.demo.entity.MemberEntity;

@Entity // JPA Entity로 선언
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberEntity {

    @Id // 기본키(primary key)로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정 (필요에 따라 변경 가능)
    private Long id;

    @Column(unique = true) // unique 제약 조건을 예시로 설정
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @Column
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private OAuthProvider oAuthProvider;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(null); // 소셜로그인이 아니므로 null
        memberEntity.setNickName(memberDTO.getNickName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setOAuthProvider(null); // 일반 회원은 oAuthProvider를 null로 설정
        memberEntity.setOAuthProvider(null); // 일반 회원은 oAuthProvider를 null로 설정
        return memberEntity;
    }
    // 소셜 회원용 생성 메서드 (비밀번호 없음)
    public static MemberEntity createSocialMember(MemberDTO socialMemberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(socialMemberDTO.getId());
        memberEntity.setMemberEmail(socialMemberDTO.getMemberEmail()); // 소셜에서 제공된 이메일
        memberEntity.setMemberName(socialMemberDTO.getMemberName()); // 소셜에서 제공된 이름 또는 ID
        memberEntity.setNickName(null);  //최초 로그인시 닉네임입력 필요하므로 null로 설정
        memberEntity.setMemberPassword(null);
        memberEntity.setOAuthProvider(socialMemberDTO.getOAuthProvider());
        return memberEntity;
    }
}

