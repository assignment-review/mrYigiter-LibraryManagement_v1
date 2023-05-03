package com.yigiter.librarymanagement.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDTO {

  //  private Long id;

  @NotNull(message = "Please provide name")
  private String name;
  @Email  // email e valid işlemi yapar format kontrolu
  @NotNull(message = "Please provide email")
  private String email;
  @NotNull(message = "Please provide phone number")  //(541) 317-8828
  @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4}$",message = "Please provide valid phone number")
  private String phoneNumber;

}