package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.demo.entity.MemberEntity;


//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO { //회원정보 필드로 정의
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    //lombok 어노테이션으로 getter,setter,생성자 ,tostring 메서드 생략 가능
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());


        return memberDTO;
    }
}
