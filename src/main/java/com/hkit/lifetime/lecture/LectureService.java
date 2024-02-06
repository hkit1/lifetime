package com.hkit.lifetime.lecture;

import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final CompanyRepository companyRepository;
    private final SubCategoryRepository subcategoryRepository;
    private final LectureRepository lectureRepository;

    public void save(LectureDto lectureDto){
        Optional<Company> isCompany = companyRepository.findByName(lectureDto.company_name());

        Optional<SubCategory> isCategory = subcategoryRepository.findByName(lectureDto.sub_category());

        if(isCompany.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Company");
        }

        if(isCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find SubCategory");
        }

        Company company = isCompany.get();
        SubCategory category = isCategory.get();
        System.out.println("------------"+company.getName()+category.getName());

        Lecture lecture = new Lecture(lectureDto.id(), lectureDto.name(), LocalDate.parse(lectureDto.created_at(), DateTimeFormatter.BASIC_ISO_DATE), LocalDate.parse(lectureDto.closed_at(),DateTimeFormatter.BASIC_ISO_DATE), category, company);
        lectureRepository.save(lecture);

    }

    public void delete(Integer id){
        Optional<Lecture> byId = lectureRepository.findById(id);
        byId.ifPresent(lectureRepository::delete);
    }

    public LectureDto findlecture(String lectureName){
        Optional<Lecture> checkLecture = lectureRepository.findByName(lectureName);
        if(checkLecture.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't Find Lecture");
        }
        Lecture lecture = checkLecture.get();
        return new LectureDto(lecture.getId(), lecture.getName(),lecture.getCreatedAt().toString(),lecture.getClosedAt().toString(),lecture.getCategory().getName(),lecture.getCompany().getName());
    }

    public List<LectureDto> findLectureByTop20() {
        List<Lecture> list = lectureRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't Find Lecture");
        }
        List<LectureDto> dto = new ArrayList<>();
        for (Lecture current : list) {
            dto.add(new LectureDto(current.getId(), current.getName(), current.getCreatedAt().toString(), current.getClosedAt().toString(), current.getCategory().getName(), current.getCompany().getName()));
        }
        return dto;
    }
}
