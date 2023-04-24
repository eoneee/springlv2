package com.example.springlv2.entity;
import com.example.springlv2.dto.CrudRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Entity
@Setter
@NoArgsConstructor
public class Crud extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //pirvate -> controller에 못씀 : method -> Getter
    private Long id;
    //private이기 때문에 controller에서 못쓰니까 method -> Getter
    private String title;
    private String username;
    private String content;
    private String password;
    public Crud(CrudRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
    }
    public void update(CrudRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
    }


}

