package com.hkit.lifetime.lecture;

import lombok.RequiredArgsConstructor;
import lombok.Value;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureContentService {
    private final LectureContentRepository lectureContentRepository;
    private final LectureRepository lectureRepository;

    public void save(Integer id,MultipartFile file){


        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(lecture.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Lecture");
        }
        String uploadDir = "C:\\Users\\";
        Path copyOfLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        System.out.println("_+_+_+_+_+_+"+copyOfLocation.toString());
        try{
            file.transferTo(copyOfLocation);
        } catch (IOException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save files");
        }

        LectureContent lectureContent = new LectureContent(null,lecture.get(),file.getOriginalFilename(), file.getContentType(), copyOfLocation.toString());
        lectureContentRepository.save(lectureContent);
    }

    public void update(LectureContentDto lectureContentDto){
        Optional<LectureContent> lectureContentOptional = lectureContentRepository.findByLecture_Id(lectureContentDto.id());
        if(lectureContentOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find LectureContent");
        }
        LectureContent lectureContent = lectureContentOptional.get();
        lectureContent.UpdateLectureContent(lectureContentDto);
        lectureContentRepository.save(lectureContent);

    }

    public void delete(Integer id){
        try {
            lectureContentRepository.findByLecture_Id(id).ifPresent(lectureContentRepository::delete);
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "can't find Content");
        }
    }


}
