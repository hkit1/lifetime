package com.hkit.lifetime.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LectureContentController {
    LectureContentService service;

    @Autowired
    public LectureContentController(LectureContentService lectureContentService) {
        service = lectureContentService;
    }

    /**
     * 스트리밍 url 가져오기
     */
    @PostMapping("/lecture/{id}/video")
    public String streamingVideo(@PathVariable("id") Long id) {
        return "";
    }

    /**
     * 강의 회차 정보 가져오기
     */
    @GetMapping("/lecture/{id}/info")
    public String getInfo(@PathVariable("id") Long id) {
        return "";
    }

    @PostMapping("api/lecture/{id}/create")
    public String LectureContentRegister(@PathVariable("id")Integer id,LectureContentDto lectureContentDto){
        System.out.println("+_+_+_"+lectureContentDto.lecture_id());
        System.out.println("_+_+_+"+id);
        if(String.valueOf(id).equals(lectureContentDto.lecture_id())) {
            service.save(lectureContentDto);
        }
        return "home";
    }

    @PostMapping("api/lecture/{lectureId}/{contentId}/update")
    public String Lecturecontentupdate(@PathVariable("lectureId") Integer lectureId, @PathVariable("contentId")Integer contentId, @RequestParam Map<String, String> param){
        LectureContentDto lectureContentDto = new LectureContentDto(contentId,lectureId.toString(), param.get("name"), param.get("description"), param.get("url"));
        service.update(lectureContentDto);
        return "home";
    }

    @PostMapping("api/lecture/{lectureId}/{contentId}/delete")
    public String LectureContentDelete(@PathVariable("lectureId") Integer lectureId, @PathVariable("contentId")Integer contentId){
        service.delete(contentId);
        return "home";
    }






}
