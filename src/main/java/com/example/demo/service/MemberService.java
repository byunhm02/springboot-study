package com.example.demo.service;
import com.example.demo.dto.KakaoTokenResponseDTO;
import com.example.demo.jwt.JwtTokenProvider;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.springframework.stereotype.Service;
import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class MemberService {

    private final MemberRepository memberRepository; // 먼저 jpa, mysql dependency 추가
    private static final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final JwtTokenProvider jwtTokenProvider;

    public void save(MemberDTO memberDTO) {
        // repsitory의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.createSocialMember(memberDTO);
        memberRepository.save(memberEntity);
        //Repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
        System.out.println("멤버가 데이터베이스에 저장되었습니다.");

    }


    public MemberDTO getMemberFromKakao(String accessToken){
        System.out.println("이제 카카오 서버에서 유저 정보를 가져오겠습니다.");
        //Webclient사용해 카카오 api 호출
        WebClient webClient = WebClient.create();

        String response = webClient.get()
                .uri(KAKAO_USERINFO_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // JSON 응답 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        // 필요한 데이터 추출
        String nickname = properties.get("nickname").getAsString();
        System.out.println("nickname: " + nickname);

        // MemberDTO 생성 및 설정
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName(nickname); // 닉네임을 MemberDTO에 설정

        boolean isNewMember=false;
        // 카카오로부터 받은 정보가 있을 경우 추가 설정
        if (memberDTO.getMemberName() != null) {
            // 사용자 정보 확인을 위한 출력
            System.out.println("카카오에서 가져온 사용자 정보:");
            System.out.println("Member Name: " + memberDTO.getMemberName());
            //System.out.println("Member Email: " + memberDTO.getMemberEmail()); // 예시로 이메일이 있다고 가정

            MemberEntity existingMember = memberRepository.findByMemberName(memberDTO.getMemberName()).orElse(null);
            if (existingMember == null) {
                // 신규 회원일 경우 저장
                isNewMember=true;
                System.out.println("신규회원입니다. 멤버를 저장하고싶어요..ㅠㅠ");
                save(memberDTO); //// DTO를 저장하는 메서드 호출
            }else{
                System.out.println("이미 존재하는 회원입니다.:"+existingMember.getMemberName());
            }
        }else {
            System.out.println("카카오에서 사용자 정보를 가져오지 못했습니다.");
        }
        memberDTO.setNewMember(isNewMember);
        //JWT 토큰 발급
        String jwtAccessToken=jwtTokenProvider.createToken(memberDTO.getMemberName(),3600);
        String jwtRefreshToken=jwtTokenProvider.createToken(memberDTO.getMemberName(),86400);

        //MemberDTO에 토큰 설정
        memberDTO.setAccessToken(jwtAccessToken);
        memberDTO.setRefreshToken(jwtRefreshToken);
        //memberDTO.setNewMember(isNewMember);
        // 토큰 출력 (발급 확인)
        System.out.println("Access Token: " + jwtAccessToken);
        System.out.println("Refresh Token: " + jwtRefreshToken);


        return memberDTO;


    }
    public MemberDTO findByMemberName(String memberName) {
        MemberEntity memberEntity = memberRepository.findByMemberName(memberName).orElse(null);
        if (memberEntity != null) {
            return MemberDTO.toMemberDTO(memberEntity); // 필요시 변환
        }
        return null;
    }

}
//MemberService.class