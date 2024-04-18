package com.example.basiccrud.controller;

import com.example.basiccrud.dto.MemberDto;
import com.example.basiccrud.entity.Member;
import com.example.basiccrud.repository.MemberRepository;
import com.example.basiccrud.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
    //private final MemberRepository memberRepository;
    //의존성에 의하여 생성자 매개변수를 추가
    private final MemberService memberService;
    public MemberController(MemberRepository memberRepository, MemberService memberService) {
        //의존성에 의하여 생성자 매개변수를 추가
        this.memberService = memberService;
    }


    @GetMapping("show")
    public String showAll(Model model){
        List<MemberDto> memberDtoList = memberService.showAllMembers();
//        List<Member> memberList = memberRepository.findAll();
//        MemberDto dto = null;
//        List<MemberDto> dtoList = null;
//        for (Member member : memberList) {
//            dto.setId(member.getId());
//            dto.setName(member.getName());
//            dto.setAge(member.getAge());
//            dto.setAddress(member.getAddr());
//            dtoList.add(dto);
//        }

//        2. 방법(MemberDto static Method로 사용하는 법
//        List<MemberDto> dtoList = null;
//        for (Member member : memberList){
//            dtoList.add(MemberDto.fromMemberEntity(member));
//        }

//        3. Static Method와 Stream 사용
//        List<MemberDto> memberDtoList = memberRepository
//                .findAll()
//                .stream()
//                .map(x -> MemberDto.fromMemberEntity(x))
//                .toList();
//        log.info(memberDtoList.toString());

        model.addAttribute("title", "회원정보");
        model.addAttribute("memberDto", memberDtoList);
        return "showMember";
    }

    @GetMapping("insert")
    public String updateForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
    return "insert";
    }

    @PostMapping("insert")
    //@Valid 는 검증
    public String insertMember(@Valid @ModelAttribute("memberDto")MemberDto dto,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("======== Validation Error ======= ");
            return "insert";
        }
        memberService.insertMember(dto);
        log.info(dto.toString());
        //가져온 Dto를 entity Member class에 옮겨 담는다
//        Member member = dto.fromMemberDto(dto);
//        log.info("Member = " + member.toString());

        // 저장합니다
//        memberRepository.save(member);
        return "redirect:/member/show";
    }

    @GetMapping("update")
    public String updateMember(@RequestParam("updateId")Long id,
                               Model model){
        MemberDto memberDto = memberService.getOneMember(id);
////        log.info("======== id ======" + String.valueOf(id));
//        //가져온 ID를 데이터베이스에서 검색
//        Member member = memberRepository.findById(id).orElse(null);
//        // 검색한 결과를 MemberDto 에 옮겨 담기
//        MemberDto dto = MemberDto.fromMemberEntity(member);
//
//        // 위 두가지를 한 번에 처리하기
//        MemberDto memberDto = memberRepository.findById(id)
//                .map(x-> MemberDto.fromMemberEntity(x))
//                .orElse(null);
        // Dto를 모델에 담아서 updateForm 으로 전송
        model.addAttribute("memberDto",memberDto);
        return "updateMember";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("memberDto")MemberDto dto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "updateForm";
        }
        memberService.update(dto);
//            public String update(@RequestParam("id") Long id,
//                                 @RequestParam("name") String name,
//                                 @RequestParam("age") int age,
//                                 @RequestParam("addr") String addr){
//        @ModelAttribute로 받을 수 있고 @RequestParam으로도 받을 수 있지만 받아올 값이 많을 때는 전가 훨씬
//        코드가 간결해진다
//        log.info(id + name + age + addr);
//        log.info("============Update==========" + dto.toString());
        // 1. Dto를 Entity로 변경
//        Member member = dto.fromMemberDto(dto);
//        // 2. 저장
//        memberRepository.save(member);
        //save는 같은 ID가 들어가면 덮어씌운다
        //update가 따로 존재하지 않는다
        return "redirect:/member/show";
    }

    //삭제처리
//    @PostMapping("delete")
    @PostMapping("/delete/{deleteId}")
//    public String delete(@RequestParam("deleteId") Long id){
    public String delete(@PathVariable("deleteId")Long id){
//        log.info("삭제할 아이디 : " + id);
        memberService.delete(id);
//        memberRepository.deleteById(id);
        return "redirect:/member/show";
    }

    //검색 처리하기
    @GetMapping("/search")
    public String searchMember(@RequestParam("type") String type,
                               @RequestParam("keyword") String keyword,
                               Model model){
//        log.info("type : " + type);
//        log.info("keyword : " + keyword);
        List<MemberDto> memberDtoList = memberService.searchMember(type, keyword);
//        List<MemberDto> memberDtoList = new ArrayList<>();
//        switch (type) {
//            case "name" :
//                //이름으로 DB 검색
//                memberDtoList = memberRepository.searchName(keyword)
//                        .stream()
//                        .map(x-> MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            case "addr" :
//                //주소로 DB 검색
//                memberDtoList = memberRepository.searchAdress(keyword)
//                        .stream()
//                        .map(x-> MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            default:
//                //전체 검색
//                memberDtoList = memberRepository.searchQuery()
//                        .stream()
//                        .map(x-> MemberDto.fromMemberEntity(x))
//                        .toList();
//                        // 타입이 다르기 때문에 stream의 map을 이용하여 넣어준다
//                break;
//        }
        model.addAttribute("memberDto", memberDtoList);
        //showMember에서 memberDto로 받기 때문에 맞춰준다
        return "showMember";
    }


}
