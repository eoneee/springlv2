package com.example.springlv2.entity;
import com.example.springlv2.dto.CrudRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Crud extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private이기 때문에 controller에서 못쓰니까 method -> Getter
    private String title;
    private String username;
    private String content;
    private String password;

    public Crud(CrudRequestDto requestDto) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.content = content;
        this.password = password;
    }

    public void update(CrudRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }

    //Getter method -> @Getter로 선언하면 없애도 되는 부분
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public String getContent() {
//        return content;
//    }

}

