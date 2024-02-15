package com.hkit.lifetime.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    public final RatingRepository repository;

    @GetMapping("/api/lecture/{id}/rating")
    public List<RatingDto> getRating(Integer id) {
        List<RatingDto> list = new ArrayList<>();
        for (Rating r : repository.findByLecture_Id(id)) {
            list.add(new RatingDto(
                    r.getId(),
                    r.getLecture().getId(),
                    r.getAccount().getName(),
                    r.getStar(),
                    r.getText()
            ));
        }
        return list;
    }
}
