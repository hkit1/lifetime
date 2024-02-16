package com.hkit.lifetime.lecture;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @GetMapping("/lecture/{id}/{content_id}")
    public String streamingVideo(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("lecture_id", id);
        if (!service.findByLectureId(id + 1).isEmpty()) {
            model.addAttribute("content_id", id + 1);
        }

        return "video";
    }

    @GetMapping("/api/lecture/{id}/{content_id}/video")
    public StreamingResponseBody stream(HttpServletRequest request, Model model, @PathVariable("id") Integer id, @PathVariable("content_id") Integer content_id) throws Exception {
        LectureContent content = service.findByLectureIdAndId(id, content_id).get();
        File file = new File(content.getUrl());
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

    @PostMapping("/api/lecture/{id}/create")
    public String LectureContentRegister(@PathVariable("id")Integer id, @RequestPart("file")MultipartFile file, LectureContentDto lectureContentDto){

        System.out.println("파일오리지날네임"+file.getOriginalFilename());
        System.out.println("파일타입"+file.getContentType());
        System.out.println("컨텐츠이름"+lectureContentDto.name());


        service.save(id, file, lectureContentDto);

        return "home";
    }

    @PostMapping("/api/lecture/{lectureId}/{contentId}/update")
    public String Lecturecontentupdate(@PathVariable("lectureId") Integer lectureId, @PathVariable("contentId")Integer contentId, @RequestParam Map<String, String> param){
        LectureContentDto lectureContentDto = new LectureContentDto(contentId, lectureId, param.get("name"), param.get("description"), param.get("url"));
        service.update(lectureContentDto);
        return "home";
    }

    @PostMapping("/api/lecture/{lectureId}/{contentId}/delete")
    public String LectureContentDelete(@PathVariable("lectureId") Integer lectureId, @PathVariable("contentId")Integer contentId){
        service.delete(lectureId, contentId);
        return "home";
    }
}
