package com.hkit.lifetime.lecture;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
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
    @GetMapping("/lecture/{id}/video")
    public String streamingVideo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("lecture_id", id);
        // TODO 다음 회차 ID 입력
        model.addAttribute("content_id", "");
        return "";
    }

    @GetMapping("/api/lecture/{id}/video")
    public StreamingResponseBody stream(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception {
        model.addAttribute("lecture_id", id);
        // TODO 여기에 다음 회차 ID 입력
        model.addAttribute("content_id", "");

        // TODO 여기에 영상 경로 입력
        File file = new File("");
        try (InputStream is = new FileInputStream(file)) {
            return os -> {
                readAndWrite(is, os);
            };
        }
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
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
        LectureContentDto lectureContentDto = new LectureContentDto(contentId, lectureId, param.get("name"), param.get("description"), param.get("url"));
        service.update(lectureContentDto);
        return "home";
    }

    @PostMapping("api/lecture/{lectureId}/{contentId}/delete")
    public String LectureContentDelete(@PathVariable("lectureId") Integer lectureId, @PathVariable("contentId")Integer contentId){
        service.delete(lectureId, contentId);
        return "home";
    }
}
