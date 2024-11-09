package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.demo.dto.MemberDTO;

//import javax.persistence.*;

//jdk17부터는 아래처럼 해야함..?
import jakarta.persistence.*;

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

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());

        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }
}
