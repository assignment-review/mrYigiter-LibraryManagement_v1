package com.yigiter.librarymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

   // private Long id;
    @NotBlank(message = "Please provide title")
    private String title;

    @NotBlank(message = "Please provide author")
    private String author;

    @NotBlank(message = "Please provide isbn")
    private String isbn;

    @NotNull(message = "Please provide available copies")
    private int availableCopies;


}
