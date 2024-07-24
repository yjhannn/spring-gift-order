package gift.controller;

import gift.service.KakaoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/oauth")
public class KakaoLoginController {
    private KakaoLoginService kakaoLoginService;
    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoLoginService.getAccessToken(code);
        return ResponseEntity.ok(accessToken);
    }
}
