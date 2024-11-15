package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.OAuthProvider;

import java.util.Properties;

//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO { //회원정보 필드로 정의
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String nickName; // 사용자 닉네임
    private OAuthProvider oAuthProvider; // 소셜 로그인 제공자 정보 (필요 시)

    @JsonProperty("nickname") // 카카오 응답의 "name" 필드를 "memberName"에 매핑
    private String memberName;

    // 신규 회원 여부를 나타내는 플래그
    private boolean isNewMember;

    // jwt토큰 위해 추가된 필드
    private String accessToken;
    private String refreshToken;

    //@JsonProperty("properties")
    //private Properties properties;

    // 카카오 응답의 "kakao_account" 객체 매핑
    //@JsonProperty("kakao_account")
    //private KakaoAccount kakaoAccount;

    //카카오 응답의 properties내 nickname을 가져오는 메서드
    //public String getMemberName() {
    //    if (properties != null && properties.getNickName()!=null){
    //        return properties.getNickName();
    //    }else if (kakaoAcoount!=null)
    //}



    //lombok 어노테이션으로 getter,setter,생성자 ,tostring 메서드 생략 가능
    //일반 회원용 메서드
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setNickName(memberEntity.getNickName());
        memberDTO.setMemberName(null);
        memberDTO.setOAuthProvider(null);
        return memberDTO;
    }
    //소셜 회원용 메서드
    public static MemberDTO socialMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(null); // 소셜 로그인 회원은 비밀번호 없음
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setNickName(memberEntity.getNickName());
        memberDTO.setOAuthProvider(memberEntity.getOAuthProvider());
        return memberDTO;
    }
}
