package com.example.meongnyangbook.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

  @NotBlank
  @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
      message = "username은 이메일 형식이어야 합니다. 예) tester123@gmail.com")
  private String username;
  @NotBlank
  private String nickname;
  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$", message = "영문과 숫자를 포함한 6자리 이상으로 만들어주세요")
  private String password;
  private String introduce;
  @NotBlank
  private String address;
  @NotBlank
  @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$")
  private String phoneNumber;

  private boolean admin = false;
  private String adminToken = ""; //실제 서비스에서 admin 등록하려면 사업자등록 유효성 검사를 해야할 필요가 있음
}
