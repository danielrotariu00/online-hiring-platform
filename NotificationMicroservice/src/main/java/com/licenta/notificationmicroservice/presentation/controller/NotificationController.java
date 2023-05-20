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
@CrossOrigin
@RequestMapping(value="/api/users/{userId}/notifications")
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate){
        this.notificationService = notificationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<NotificationDTO> getByUserId(@PathVariable Long userId) {
        return notificationService.getAllByUserId(userId);
    }

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.PUT)
    public void updateIsRead(@PathVariable Long userId, @PathVariable Long notificationId, @RequestBody Boolean isRead) {
        notificationService.updateIsRead(userId, notificationId, isRead);
    }

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long userId, @PathVariable Long notificationId) {
        notificationService.delete(userId, notificationId);
    }
}