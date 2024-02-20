package com.hkit.lifetime.rating;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    public final RatingRepository ratingRepository;
    public final LectureRepository lectureRepository;
    public final AccountRepository accountRepository;

    @GetMapping("/api/lecture/{id}/rating")
    public List<RatingDto> getRating(@PathVariable Integer id) {
        List<RatingDto> list = new ArrayList<>();
        for (Rating r : ratingRepository.findByLecture_Id(id)) {
            list.add(new RatingDto(
                    r.getLecture().getId(),
                    r.getAccount().getName(),
                    r.getStar(),
                    r.getText()
            ));
        }
        return list;
    }

    public void registerRating(RatingDto ratingDto){

        Lecture findLecture = lectureRepository.findById(ratingDto.lectureId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lecture Not Found"));

        Account findAccount = accountRepository.findAccountByName(ratingDto.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));

        Rating rating = new Rating(findLecture, findAccount, ratingDto.star(), ratingDto.text());
        ratingRepository.save(rating);

    }
}
