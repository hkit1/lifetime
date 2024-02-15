package com.hkit.lifetime.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LectureController {
    private final LectureService service;
    private final LectureContentService content;
    private final String savePath = "C:\\Users\\dydxo\\temp\\";

    @PostMapping("/api/lecture/create")
    public String lectureRegister(LectureDto lectureDto) {
        service.save(lectureDto);
        return "home";
    }

    @PostMapping("/api/lecture/delete/{id}")
    public String lectureDelete(@PathVariable Integer id){
        service.delete(id);
        return "home";
    }

    @GetMapping("/lecture/{lectureId}")
    public String viewLecture(@PathVariable("lectureId") Integer lectureId, Model model){
        LectureDto lecturedto = service.findlecture(lectureId);
        List<LectureContentDto> contentdto = content.convertDto(content.findByLectureId(lecturedto.id()));
        model.addAttribute("lecture",lecturedto);
        model.addAttribute("contentList", contentdto);

        return "view";
    }

    @GetMapping("/lecture/{lectureId}/view")
    public String viewLecturecontent(@PathVariable("lectureId") Integer lectureId, Model model) {
        LectureDto lecturedto = service.findlecture(lectureId);
        model.addAttribute(lecturedto);
        return "home";
    }

    @RequestMapping("api/lecture/{lectureId}/image")
    @ResponseBody
    public ResponseEntity<byte[]> lectureImage(@PathVariable Integer lectureId) throws IOException{
        String Path = savePath+"\\"+lectureId.toString()+"\\"+"thumbnails.png";
        InputStream imageStream = new FileInputStream(Path);
        byte[] toByteArray = imageStream.readAllBytes();


        return new ResponseEntity<>(toByteArray,HttpStatus.OK);

    }
}
