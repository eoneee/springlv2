package com.example.springlv2.controller;
import com.example.springlv2.dto.CrudRequestDto;
import com.example.springlv2.dto.CrudResponseDto;
import com.example.springlv2.dto.MsgResponseDto;
import com.example.springlv2.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
@RestController
@RequiredArgsConstructor
//클라이언츠의 요청을 하나씩 연결해줌
@RequestMapping("/api")
public class CrudController {

    private final CrudService crudService;

    //글 생성하기
    @PostMapping("/post")
    public CrudResponseDto createCrud(@RequestBody CrudRequestDto requestDto, HttpServletRequest request) {
        //브라우저에서 요청해온 데이터를 잘 불러와서 서비스에 던져줌
        return crudService.createCrud(requestDto, request);
    }

    //메인 페이지
    @GetMapping("/posts")
    public List<CrudResponseDto> getCrudList() {
        return crudService.getCrudList();
    }

    //전체목록 말고 하나씩 보기
    @GetMapping("post/{id}")
    public CrudResponseDto getCrud(@PathVariable Long id) {
        return crudService.getCrud(id);
    }

    //수정하기
    @PutMapping("/post/{id}")
        public CrudResponseDto updateCrud(@PathVariable Long id, @RequestBody CrudRequestDto requestDto, HttpServletRequest request) {
        return crudService.updateCrud(id,requestDto,request);
    }


    //삭제
    @DeleteMapping("/post/{id}")
    public MsgResponseDto deleteCrud(@PathVariable Long id, HttpServletRequest request) {
        return  crudService.deleteCrud(id,request);

    }

    @GetMapping("/title/{title}")
    public CrudResponseDto getCrudByTitle(@PathVariable String title){
        return crudService.getCrudByTitle(title);
    }


}
