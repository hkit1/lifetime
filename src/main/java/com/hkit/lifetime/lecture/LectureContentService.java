package com.hkit.lifetime.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hkit.lifetime.lecture.LectureService.savePath;

@Service
@RequiredArgsConstructor
public class LectureContentService {
    private final LectureContentRepository lectureContentRepository;
    private final LectureRepository lectureRepository;

    public void save(Integer id, MultipartFile file, LectureContentDto lectureContentDto){
        Optional<Lecture> lecture = lectureRepository.findById(id);
        System.out.println("+_+_+_"+id);
        if(lecture.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Lecture");
        }
        String uploadDir = savePath + id;
        Path copyOfLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath("temp.mp4"));
        System.out.println("_+_+_+_+_+_+"+ copyOfLocation);
        try{
            file.transferTo(copyOfLocation);
        } catch (IOException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save files");
        }

        LectureContent lectureContent = new LectureContent(null,lecture.get(), lectureContentDto.name(), lectureContentDto.description(), copyOfLocation.toString());
        LectureContent result = lectureContentRepository.save(lectureContent);
        try {
            Files.move(copyOfLocation, Paths.get(uploadDir + File.separator + StringUtils.cleanPath(result.getId() + ".mp4")),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lectureContentRepository.updateUrlByIdAndLecture(uploadDir + File.separator + StringUtils.cleanPath(result.getId() + ".mp4"), lectureContent.getId(), lecture.get());
    }

    public void update(LectureContentDto lectureContentDto){
        Optional<LectureContent> lectureContentOptional = lectureContentRepository.findByLecture_IdAndId(lectureContentDto.lecture_id(), lectureContentDto.id());
        if(lectureContentOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find LectureContent");
        }
        LectureContent lectureContent = lectureContentOptional.get();
        lectureContent.UpdateLectureContent(lectureContentDto);
        lectureContentRepository.save(lectureContent);

    }

    public void delete(Integer lecture_id, Integer content_id) {
        try {
            lectureContentRepository.findByLecture_IdAndId(lecture_id, content_id).ifPresent(lectureContentRepository::delete);
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "can't find Content");
        }
    }

    public List<LectureContent> findByLectureId(Integer lecture_id) {
        return lectureContentRepository.findByLecture_Id(lecture_id);
    }

    public List<LectureContentDto> convertDto(List<LectureContent> content) {
        List<LectureContentDto> list = new ArrayList<>();
        for (LectureContent a : content) {
            list.add(new LectureContentDto(a.getId(), a.getLecture().getId(), a.getName(), a.getDescription(), a.getUrl()));
        }

        return list;
    }

    public Optional<LectureContent> findByLectureIdAndId(Integer lecture_id, Integer content_id) {
        return lectureContentRepository.findByLecture_IdAndId(lecture_id, content_id);
    }
}
