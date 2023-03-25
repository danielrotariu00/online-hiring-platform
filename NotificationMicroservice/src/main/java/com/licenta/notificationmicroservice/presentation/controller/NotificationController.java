package com.licenta.notificationmicroservice.presentation.controller;


import com.licenta.notificationmicroservice.business.interfaces.INotificationService;
import com.licenta.notificationmicroservice.business.model.NotificationDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate){
        this.notificationService = notificationService;
    }

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