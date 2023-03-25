package com.licenta.jobapplicationmicroservice.persistence.document;

import com.licenta.jobapplicationmicroservice.business.model.Job;
import com.licenta.jobapplicationmicroservice.business.model.Message;
import com.licenta.jobapplicationmicroservice.business.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "job_applications")
public class JobApplication {

    @Id
    private String id;

    private Long userId;

    private Job job;

    private JobApplicationStatus status;

    private List<Message> messageList;

    private Review review;

    private LocalDateTime updatedAt;
}
