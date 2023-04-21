package com.example.springlv2.repository;

import com.example.springlv2.entity.Crud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
//spring data JPA
public interface CrudRepository extends JpaRepository<Crud, Long> {
    Optional<Crud> findByTitle(String title);

    Optional<Crud> findByIdAndUserId(Long id, Long userId);
    //내림차순 정렬
    Collection<Crud> findAllByOrderByModifiedAtDesc();
}

/**
@Repository
public class CrudRepository {

    //JPA코드
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void save(Crud crud){
        //저장
        em.persist(crud);
    }

    //게시글 단일 조회
    @Transactional
    public Optional<Crud> findById(Long id){
        //id 받아와서 찾기
        Crud crud = em.find(Crud.class, id);
        return Optional.ofNullable(crud);
    }

    //게시글 전체 조회
    @Transactional
    public List<Crud> findAll(){
        //JPAQuery
        return em.createQuery("select c from Crud c", Crud.class).getResultList();
    }

//    @Transactional
//    public void update(Crud crud){
//        Crud crud = em.find(Crud.class, id);
//        crud.update(requestDto);
//    }

    @Transactional
    public void delete(Long id){
        Crud crud = em.find(Crud.class, id);
        em.remove(crud);
    }
    **/

    /*
    //글 생성하기
    public String createCrud(Crud crud) {
        //ID 중복을 막기위해서 현재 table을 최대 ID + 1
        crud.setId(++ID);
        //테이블에 생성한 Crud 인스턴스를 저장
        table.put(ID, crud);

        return "게시글이 등록되었습니다";
    }

    //메인 페이지
    public List<CrudResponseDto> getCrudList() {
        //테이블에 저장되어있는 모든 글을 조회
        return table.values().stream().map(CrudResponseDto::new).collect(Collectors.toList());

    }

    //전체목록 말고 하나씩 보기
    public Crud getCrud(Long id) {
//    public CrudResponseDto getCrud(Long id){
        return table.get(id);
    }

    //수정하기
    //위의 getCrud를 호출해서 데이터를 가지고 오면서 id를 확인하므로 위의 메서드를 중복으로 사용 가능하다.
//    public CrudResponseDto updateCrud(Long id,CrudRequestDto requestDto){
//    }


    //삭제
    public void deleteCrud(Long id) {
        table.remove(id);
    }




}
*/