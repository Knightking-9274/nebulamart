package com.nebulamart.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nebulamart.backend.entities.Rating;
import com.nebulamart.backend.entities.User;
import com.nebulamart.backend.exceptions.ProductException;
import com.nebulamart.backend.exceptions.UserException;
import com.nebulamart.backend.requests.RatingRequest;
import com.nebulamart.backend.services.RatingService;
import com.nebulamart.backend.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating>createRating(@RequestBody RatingRequest req,
                                @RequestHeader("Authorization")String jwt)throws UserException,ProductException{
            User user = userService.UserProfileByJwt(jwt);
            Rating rating = ratingService.createRating(req, user);
            return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>>getProductsRating(@PathVariable Long productId,
                            @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
}
