package com.example.springlv2.dto;

import com.example.springlv2.entity.Crud;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrudResponseDto {
    private Long id;
    private String title;
    private String username;
//    private
    private String content;

    public CrudResponseDto(Crud crud) {
        this.id = crud.getId();
        this.title = crud.getTitle();
        this.username = crud.getUsername();
        this.content = crud.getContent();
    }
}
