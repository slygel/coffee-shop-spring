package com.project.coffeeshop.controller;

import com.project.coffeeshop.dto.FeedbackDto;
import com.project.coffeeshop.model.FeedbackModel;
import com.project.coffeeshop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/post/feedback")
    public ResponseEntity<Object> createFeedback(@RequestBody FeedbackDto feedbackDto){
        boolean result = feedbackService.addFeedbackOrder(feedbackDto);
        if(!result){
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbackOrder(){
        ArrayList<FeedbackDto> feedbackDtos = new ArrayList<>(feedbackService.getAllFeedbacks());
        return new ResponseEntity<>(feedbackDtos,HttpStatus.OK);
    }
}
