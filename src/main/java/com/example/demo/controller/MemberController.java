package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.demo.service.MemberService;
import com.example.demo.dto.MemberDTO;
import lombok.RequiredArgsConstructor;

//dto 이용하지 않는 경우
/*
@Controller
public class MemberController {
    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save"; //문자열 "save"를 반환하는데, Spring Boot는 이를 뷰 이름으로 해석합함. 따라서 save.html과 같은 템플릿 파일을 찾고, 해당 페이지를 클라이언트에 반환
    }

    @PostMapping("/member/save") //name값을 requestparam에 담아온다
    public String save(@RequestParam("memberEmail")String memberEmail,
                       @RequestParam("memberPassword")String memberPassword,
                       @RequestParam("memberName") String memberName){
        System.out.println("MemberController.save");
        System.out.println("memberEmail = " + memberEmail +", memberPassword = " + memberPassword +", memberName = " + memberName);
        return "index";
    }

}*/

//dto이용하는경우
@Controller
@RequiredArgsConstructor //MemberService에 대한 멤버를 사용 가능
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")    // name값을 requestparam에 담아온다
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "index";
    }

    @PostMapping("/set-nickname")
    public String setNickName(@RequestParam("nickName") String nickName) {
        System.out.println("닉네임을 설정해주세요");
        System.out.println("MemberController.setNickName");
        System.out.println("nickname = " + nickName);
        return "main";
    }
}
//MemberController.class

