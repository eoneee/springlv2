package com.example.springlv2.service;

import com.example.springlv2.dto.CrudRequestDto;
import com.example.springlv2.dto.CrudResponseDto;
import com.example.springlv2.dto.MsgResponseDto;
import com.example.springlv2.entity.Crud;
import com.example.springlv2.entity.User;
import com.example.springlv2.entity.UserRoleEnum;
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
            User user = checkJwtToken(request);
            //요청받은 dto로 db에 저장할 객체 Crud crud 만들기
            Crud crud = new Crud(requestDto);

            crud.addUser(user);
            crudRepository.save(crud);
            //브라우저에서 받아온 데이터를 저장하기 위해서 crud객체로 변환
            return new CrudResponseDto(crud);
    }

    //메인 페이지
    @Transactional(readOnly = true) //JPA를 사용할 경우, 변경감지 작업을 수행하지 않아서 성능이점 있음
    public List<CrudResponseDto> getCrudList() {
        //테이블에 저장되어있는 모든 글을 조회
        //내림차순
        return crudRepository.findAllByOrderByCreatedAtDesc().stream().map(CrudResponseDto::new).collect(Collectors.toList());


    }


    //전체목록 말고 하나씩 보기
    @Transactional(readOnly = true)
    public CrudResponseDto getCrud(Long id) {
        //조회하기 위해 받아온 crud의 id를 사용해서 해당 crud인스턴스가 테이블에 존재 하는지 확인하고 가져오기
        //Crud crud = table.get(id);->repository한테서 id를 가져오면 됨
        Crud crud = checkCrud(id);
        return new CrudResponseDto(crud);
    }

    //수정하기
    @Transactional
    public CrudResponseDto updateCrud(Long id, CrudRequestDto requestDto, HttpServletRequest request) {
            User user = checkJwtToken(request);
            //게시글 체크
            Crud crud = checkCrud(id);
            //권한 체크
            isCrudUser(user,crud);
            crud.update(requestDto);
            return new CrudResponseDto(crud);
    }




    //삭제
    @Transactional
    public MsgResponseDto deleteCrud(Long id, HttpServletRequest request){
        User user = checkJwtToken(request);
        //게시글 체크
        Crud crud = checkCrud(id);
        //권한 체크
        isCrudUser(user, crud);
        crudRepository.deleteById(id);
            return new MsgResponseDto("게시글 삭제 성공",HttpStatus.OK.value());
    }

    //게시글 존재 여부 확인
    private Crud checkCrud(Long id) {
        Crud crud = crudRepository.findById(id).orElseThrow(
                ()->new NullPointerException("글이 존재하지 않습니다.")
        );
        return crud;
    }


    //권한 여부
    public void isCrudUser(User user,Crud crud){
        if(!crud.getUser().getUsername().equals(user.getUsername()) && !user.getUsername().equals(UserRoleEnum.ADMIN)){
            throw new IllegalArgumentException("권한 없음");
        }
    }

    public CrudResponseDto getCrudByTitle(String title) {
        Crud crud = crudRepository.findByTitle(title).orElseThrow(
                () -> new NullPointerException("해당하는 제목의 글이 없습니다.")
        );
        return new CrudResponseDto(crud);
    }


    public User checkJwtToken(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰 검증
        if(jwtUtil.validateToken(token)){
            //토큰에서 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("로그인 후 이용해 주세요");
        }
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()   -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }
}
