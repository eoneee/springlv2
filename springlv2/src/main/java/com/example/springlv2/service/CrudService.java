package com.example.springlv2.service;

import com.example.springlv2.dto.CrudRequestDto;
import com.example.springlv2.dto.CrudResponseDto;
import com.example.springlv2.dto.MsgResponseDto;
import com.example.springlv2.entity.Crud;
import com.example.springlv2.entity.User;
import com.example.springlv2.jwt.JwtUtil;
import com.example.springlv2.repository.CrudRepository;
import com.example.springlv2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrudService {

    private final CrudRepository crudRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //글 생성하기
    @Transactional
    public CrudResponseDto createCrud(CrudRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            //요청받은 dto로 db에 저장할 객체 Crud crud 만들기
            crudRepository.saveAndFlush(requestDto);
            //브라우저에서 받아온 데이터를 저장하기 위해서 crud객체로 변환
            return new CrudResponseDto(crud);
        } else {
            return null;
        }
    }

    //메인 페이지
    @Transactional(readOnly = true) //JPA를 사용할 경우, 변경감지 작업을 수행하지 않아서 성능이점 있음
    public List<CrudResponseDto> getCrudList() {
        //테이블에 저장되어있는 모든 글을 조회
//        return crudRepository.findAll().stream().map(CrudResponseDto::new).collect(Collectors.toList());
        //내림차순
        return crudRepository.findAllByOrderByModifiedAtDesc().stream().map(CrudResponseDto::new).collect(Collectors.toList());


    }


    //전체목록 말고 하나씩 보기
    @Transactional(readOnly = true)
    public CrudResponseDto getCrud(Long id) {
        //조회하기 위해 받아온 crud의 id를 사용해서 해당 crud인스턴스가 테이블에 존재 하는지 확인하고 가져오기
        //Crud crud = table.get(id);->repository한테서 id를 가져오면 됨
        Crud crud = checkCrud(id);
//        위에서 예외 처리 해줌
//        if(crud != null){
//            return new CrudResponseDto(crud);
//        }else{
//            return new CrudResponseDto();
//        }
        return new CrudResponseDto(crud);
    }

    //수정하기
    @Transactional
    public CrudResponseDto updateCrud(Long id, CrudRequestDto requestDto, HttpServletRequest request) {
        //수정하기 위해 받아온 crud의 id를 사용하여 해당 crud 인스턴스가 존재하는지 확인하고 가져오기
        //Crud crud = table.get(id);
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Crud crud = crudRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    ()->new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );

            crud.update(requestDto);
            return new CrudResponseDto(crud);
        } else {
            return null;
        }
    }




    //삭제
    @Transactional
    public MsgResponseDto deleteCrud(Long id, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    ()->new IllegalArgumentException("사용자가 존재하지 않습니다")
            );
            Crud crud = crudRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    ()-> new IllegalArgumentException("글이 존재하지 않습니다.")
            );
            crudRepository.delete(crud);
            return new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        }else{
            return new MsgResponseDto("게시글 작성자만 삭제 가능, 권한 없음",HttpStatus.OK.value());
        }
    }

    private Crud checkCrud(Long id) {
        Crud crud = crudRepository.findById(id).orElseThrow(
                ()->new NullPointerException("글이 존재하지 않습니다.")
        );
        return crud;
    }

    public CrudResponseDto getCrudByTitle(String title) {
        Crud crud = crudRepository.findByTitle(title).orElseThrow(
                () -> new NullPointerException("해당하는 제목의 글이 없습니다.")
        );
        return new CrudResponseDto(crud);
    }
}