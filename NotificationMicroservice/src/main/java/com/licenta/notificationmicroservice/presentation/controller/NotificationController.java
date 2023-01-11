package com.licenta.notificationmicroservice.presentation.controller;

// import com.shubh.kafkachat.constants.KafkaConstants;
// import com.shubh.kafkachat.model.Message;
import com.licenta.notificationmicroservice.business.interfaces.INotificationService;
import com.licenta.notificationmicroservice.business.model.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class NotificationController {

    @Autowired
    private INotificationService notificationService;
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/group")
//    public Message broadcastGroupMessage(@Payload Message message) {
//        //Sending this message to all the subscribers
//        return message;
//    }
//
//    @MessageMapping("/newUser")
//    @SendTo("/topic/group")
//    public Message addUser(@Payload Message message,
//                           SimpMessageHeaderAccessor headerAccessor) {
//        // Add user in web socket session
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }

    @RequestMapping(value="/users/{userId}/notifications", method = RequestMethod.GET)
    public Iterable<NotificationDTO> getByUserId(@PathVariable Long userId) {

        return notificationService.getAllByUserId(userId);
    }

    @RequestMapping(value = "/notifications/{notificationId}", method = RequestMethod.PUT)
    public void updateIsRead(@PathVariable Long notificationId, @RequestBody Boolean isRead) {

        notificationService.updateIsRead(notificationId, isRead);
    }

    @RequestMapping(value = "/notifications/{notificationId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long notificationId) {

        notificationService.delete(notificationId);
    }

}