package com.hkit.lifetime.lecture;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final CompanyRepository companyRepository;
    private final SubCategoryRepository subcategoryRepository;
    private final LectureRepository lectureRepository;
    public static final String savePath = "C:\\Users\\HKIT\\temp\\";
    private final AccountRepository accountRepository;
    //이미지, 동영상 저장하는 저장소의 주소가 될 것.

    public void save(LectureInputDto lectureInputDto){
        Optional<Company> isCompany = companyRepository.findByName(lectureInputDto.company_name());

        Optional<SubCategory> isCategory = subcategoryRepository.findByName(lectureInputDto.sub_category());

        Optional<Account> isTeacher = accountRepository.findAccountById(lectureInputDto.teacher_id());

        if(isCompany.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Company");
        }

        if(isCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find SubCategory");
        }

        if(isTeacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can,t find Account");
        }

        Company company = isCompany.get();
        SubCategory category = isCategory.get();
        Account teacher = isTeacher.get();
        System.out.println("------------"+company.getName()+category.getName());
        System.out.println(lectureInputDto.file().getOriginalFilename());


        Lecture lecture = new Lecture(lectureInputDto.id(), lectureInputDto.name(), lectureInputDto.description(), LocalDate.parse(lectureInputDto.created_at(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(lectureInputDto.closed_at(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), teacher, category, company);
        Lecture dwnloadLec = lectureRepository.save(lecture);
        //ispresent 나중에

        if(!lectureInputDto.file().isEmpty()) {
            MultipartFile file = lectureInputDto.file();
            String uploadDir = savePath+dwnloadLec.getId().toString();
            System.out.println(uploadDir);
            File mkdir = new File(uploadDir);
            if(!mkdir.exists()){
                boolean result = mkdir.mkdirs();
                System.out.println(result?"made":"not made");
            }
            Path copyImageLocation = Paths.get(uploadDir + File.separator + "thumbnails.png");
            System.out.println(copyImageLocation);
            try{
                file.transferTo(copyImageLocation);
            } catch (IOException e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save images");
            }
        }


    }

    public void delete(Integer id){
        Optional<Lecture> byId = lectureRepository.findById(id);
        if(byId.isPresent()){

            lectureRepository.delete(byId.get());
            File folder = new File(savePath+ id);
            try{
                while (folder.exists()){
                    File[] folder_list = folder.listFiles();

                    for(File files: Objects.requireNonNull(folder_list)){
                        files.delete();
                    }

                    if (folder_list.length==0 && folder.isDirectory()){
                        folder.delete();
                    }
                }
            }catch (Exception e) {
                e.getStackTrace();
            }


        }

    }

    public LectureOutputDto findlecture(Integer lectureId){
        Optional<Lecture> checkLecture = lectureRepository.findById(lectureId);
        if(checkLecture.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't Find Lecture");
        }
        Lecture lecture = checkLecture.get();

        return new LectureOutputDto(lecture.getId(), lecture.getName(), lecture.getDescription(), lecture.getCreatedAt().toString(), lecture.getClosedAt().toString(), lecture.getTeacher().getName() ,lecture.getCategory().getName(), lecture.getCompany().getName());
    }

    public List<LectureOutputDto> findLectureByTop12() {
        List<Lecture> list = lectureRepository.findAll(PageRequest.of(0, 12)).getContent();
        List<LectureOutputDto> dto = new ArrayList<>();
        for (Lecture current : list) {
            dto.add(new LectureOutputDto(current.getId(), current.getName(), current.getDescription(), current.getCreatedAt().toString(), current.getClosedAt().toString(), current.getTeacher().getName() ,current.getCategory().getName(), current.getCompany().getName()));
        }
//        dto.sort((a, b) -> Integer.parseInt(a.created_at()));
        return dto;
    }

    public Long countByDate(LocalDate date) {
        return lectureRepository.countByCreatedAtAfter(date);
    }

    public Long countAll() {
        return lectureRepository.count();
    }

    public List<Lecture> getAllByPage(Pageable page) {
        return lectureRepository.findAll(page).getContent();
    }
}
