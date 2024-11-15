package com.example.demo.controller;

import com.example.demo.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.KakaoAuthService;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    public static final String KAKAO_CLIENT_ID = "69611c537135468129dbf23caa99c358"; // 실제 client_id로 변경
    private static final String KAKAO_REDIRECT_URI = "http://localhost:8080/callback"; // 실제 redirect URI로 변경
    private final MemberService memberService;
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    // 하나의 생성자로 두 의존성 주입
    public AuthController(MemberService memberService, KakaoAuthService kakaoAuthService,JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.kakaoAuthService = kakaoAuthService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/auth/login")
    public String redirectToKakaoLogin() {
        String kakaoLoginUrl = String.format(
                "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                KAKAO_CLIENT_ID,
                KAKAO_REDIRECT_URI
        );

        return "redirect:" + kakaoLoginUrl;
    }

    //인가코드를 받은 후 리다이렉트되는 곳.
    @GetMapping("/callback")
    public String handleKakaoCallback(@RequestParam("code") String code, Model model) {

        System.out.println("카카오 인증 코드: " + code);

        // code를 이용해 카카오로부터 액세스 토큰을 요청하고 처리함.
        // 이후 액세스 토큰을 사용해 사용자 정보를 가져오는 작업을 진행할 수 있습니다.
        String accessToken=kakaoAuthService.getAccessTokenFromKakao(code);
        System.out.println("Access Token: " + accessToken);
        MemberDTO memberDTO=memberService.getMemberFromKakao(accessToken);
        /*
        if (memberDTO.isNewMember()) {
            model.addAttribute("member",memberDTO);
            return "nicknameForm";
        }

         */

        //로그인 성공: JWT발급
        //String jwtAccessToken=jwtTokenProvider.createToken(memberDTO.getMemberName(),3600); //1시간 유효
        //String refreshToken=jwtTokenProvider.createToken(memberDTO.getMemberName(),86400);//24시간 유효



        System.out.println("로그인성공");
        return "main";
    }

}
