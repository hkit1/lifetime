package com.hkit.lifetime.lecture;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Controller
public class LectureContentController {
    final LectureContentService service;

    @Autowired
    public LectureContentController(LectureContentService lectureContentService) {
        service = lectureContentService;
    }

    /**
     * 스트리밍 url 가져오기
     */
    @GetMapping("/lecture/{id}/{content_id}")
    public String streamingVideo(@PathVariable("id") Integer id,@PathVariable("content_id")Integer content_id ,Model model) {
        model.addAttribute("lecture_id", id);
        model.addAttribute("content_id", content_id);


        return "video";
    }

    @GetMapping("/api/lecture/{id}/{content_id}/video")
    public ResponseEntity<StreamingResponseBody> stream(HttpServletRequest request, Model model, @PathVariable("id") Integer id, @PathVariable("content_id") Integer content_id) throws Exception {
        LectureContent content = service.findByLectureIdAndId(id, content_id).get();
        File file = new File(content.getUrl());

        if (!file.isFile()){
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody streamingResponseBody = outputStream -> {
            try {
                final InputStream inputStream = new FileInputStream(file);

                byte[] bytes = new byte[2048];
                int length;

                while ((length = inputStream.read(bytes)) >= 0) {
                    outputStream.write(bytes, 0, length);
                }
                inputStream.close();
                outputStream.flush();
            } catch (Exception e) {
                log.error("Exception while reading and streamin data {} ", e.getMessage());
            }
        };

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "video/mp4");
        responseHeaders.add("Content-Length", Long.toString(file.length()));

        return ResponseEntity.ok().headers(responseHeaders).body(streamingResponseBody);

//        try (InputStream is = new FileInputStream(file)) {
//            return os -> {
//                readAndWrite(is, os);
//            };
//        }
    }

//    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
//        byte[] data = new byte[2048];
//        int read;
//        while ((read = is.read(data)) > 0) {
//            os.write(data, 0, read);
//        }
//        os.flush();
//    }

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
