package com.example.user_service.UserService.services.impl;

import com.example.user_service.UserService.entities.Hotel;
import com.example.user_service.UserService.entities.Rating;
import com.example.user_service.UserService.entities.User;
import com.example.user_service.UserService.exception.ResourceNotFoundException;
import com.example.user_service.UserService.repositories.UserRepositories;
import com.example.user_service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired  //For autowired the bean must be present in the configuration class(Application class)
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        //Generate the unique UserId by using the (UUID) class which is present in util package
        String randomUserId = UUID.randomUUID().toString();
        logger.info("updating user id");
        user.setUserId(randomUserId);
        logger.info("user id updated");
        logger.info("user details {} {} {}", user.getName(), user.getEmail(), user.getUserId(), user.getAbout());
        return userRepositories.save(user);
    }

    @Override
    public List<User> getAllUser() {
        //we are also implement the communication logic with rating service here using the (rest template)
        return userRepositories.findAll();
    }

    @Override
    public User getUser(String userId) {

        //TODO : We are doing this to get the rating from the rating service into the user service

//        //get user from database with the help of user repository
//        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id is not found on server"+userId));
//
//        //fetch rating of the above user from RATING SERVICE
//        //http://localhost:8083/ratings/users/f92f1e2e-15b8-4bee-929b-ed0ee7c03230
//        //ENABLE COMMUNICATION BETWEEN THE SERVICES USING (REST_TEMPLATE)
//
//        //Pass the url of the rating
//        //this way is not the dynamic way
//        //ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/f92f1e2e-15b8-4bee-929b-ed0ee7c03230", ArrayList.class);
//
//
//        //This way of the communication is hardcoded that's why many problems are occur at the time of deployment(when the port or the host is change)
//        //To make communication dynamic we are going to use the service registry because the service registry contains all the information of services
//        ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);
//        logger.info("{} ",ratingsOfUser);
//
//        user.setRatings(ratingsOfUser); //set the rating of the user in the user service
//        return user;

        //TODO: Now we are going to get the hotel by using the rating in the user service


        User user = userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id is not found on server"+userId));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating-> {

            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/b8a11203-117a-41cd-9cc8-f0934bcc85c8  (url to get the hotel)
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(),Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code: {} ", forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);


            //return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}

