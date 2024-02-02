package com.hkit.lifetime.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureContentService {
    private final LectureContentRepository lectureContentRepository;
    private final LectureRepository lectureRepository;

    public void save(LectureContentDto lectureContentDto){
        Optional<Lecture> lecture = lectureRepository.findById(Integer.valueOf(lectureContentDto.lecture_id()));
        System.out.println("+_+_+_"+lectureContentDto.lecture_id());
        if(lecture.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Lecture");
        }

        LectureContent lectureContent = new LectureContent(null,lecture.get(),lectureContentDto.name(),lectureContentDto.description(),lectureContentDto.url());
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
