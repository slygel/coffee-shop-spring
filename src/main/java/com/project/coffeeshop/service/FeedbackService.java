package com.project.coffeeshop.service;

import com.project.coffeeshop.dto.FeedbackDto;
import com.project.coffeeshop.dto.FeedbackUser;
import com.project.coffeeshop.entity.Feedback;
import com.project.coffeeshop.entity.Order;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.model.FeedbackModel;
import com.project.coffeeshop.model.OrderModel;
import com.project.coffeeshop.repo.FeedbackRepository;
import com.project.coffeeshop.repo.OrderRepository;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // add authentication
    @Transactional
    public boolean addFeedbackOrder(FeedbackDto feedbackDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            User user = userRepository.getUserByUsername(currentUserName).get(0);

            Order order = orderRepository.getById(feedbackDto.getOrderId());

            Feedback feedback = new Feedback();
            feedback.setTitle(feedbackDto.getTitle());
            feedback.setContent(feedbackDto.getContent());
            feedback.setOrder(order);
            feedback.setUser(user);

            feedbackRepository.save(feedback);
            return true;
        }
        return false;
    }

    @Transactional
    public List<FeedbackUser> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackUser> feedbackUsers = new ArrayList<>();
        for (Feedback feedback : feedbacks){
            feedbackUsers.add(new FeedbackUser(feedback));
        }
        return feedbackUsers;
    }

}
