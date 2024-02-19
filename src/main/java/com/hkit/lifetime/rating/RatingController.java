package com.hkit.lifetime.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/api/lecture/{lecture_id}/rate/create")
    public String createRating(@PathVariable(name = "lecture_id") Integer lecture_id,
                               ReqRatingDto reqRatingDto){

        RatingDto ratingDto = new RatingDto(
                lecture_id,
                reqRatingDto.name(),
                Integer.parseInt(reqRatingDto.star()),
                reqRatingDto.text()
        );

        ratingService.registerRating(ratingDto);

        return "home";
    }

}
