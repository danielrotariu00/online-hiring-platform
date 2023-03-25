package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.interfaces.IUserDetailsService;
import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;
import com.licenta.databasemicroservice.business.util.mapper.UserDetailsMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.User;
import com.licenta.databasemicroservice.persistence.entity.UserDetails;
import com.licenta.databasemicroservice.persistence.repository.UserDetailsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class UserDetailsService implements IUserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private MimeContentService mimeContentService;

    @Autowired
    private UrlService urlService;

    private final UserDetailsMapper userDetailsMapper = Mappers.getMapper(UserDetailsMapper.class);

    @Value("${users.images.path}")
    private String usersImagesPath;

    @Override
    public UserDetailsResponse saveUserDetails(Long userId, SaveUserDetailsRequest request) {
        UserDetails userDetails = userDetailsMapper.toModel(request);

        User user = userService.getUserOrElseThrowException(userId);
        City city = cityService.getCityOrElseThrowException(request.getCityId());

        userDetails.setUserId(user.getId());
        userDetails.setCity(city);

        if (userDetailsRepository.findById(userId).isPresent()) {
            userDetails.setUserId(userId);
        }

        return userDetailsMapper.toResponse(
                userDetailsRepository.save(userDetails)
        );
    }

    @Override
    public UserDetailsResponse getUserDetails(Long userId) {

        userService.getUserOrElseThrowException(userId);

        UserDetails  userDetails = userDetailsRepository.findById(userId).orElse(null);

        if(userDetails != null)
            return userDetailsMapper.toResponse(userDetails);
        else
            return UserDetailsResponse.builder().build();
    }

    @Override
    public void saveImage(Long userId, MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        String type = mimeContentService.getMimeType(imageBytes);

        if(!mimeContentService.isImage(type))
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);


        UserDetails  userDetails = userDetailsRepository.findById(userId).orElse(null);

        if(userDetails == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String extension = mimeContentService.getExtensionFromMimeType(type);

        String[] arr = userDetails.getProfilePictureUrl().split("/");
        String currentImagePath = arr[arr.length - 1];
        String oldPath = usersImagesPath + "/" + currentImagePath;
        File file = new File(oldPath);
        file.delete();

        String imageName = userId + "." + extension;
        String path = usersImagesPath + "/" + imageName;
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(imageBytes);
            fos.flush();
        }

        path = urlService.getBaseUrl() + "/images/users/" + imageName;
        userDetails.setProfilePictureUrl(path);
        userDetailsRepository.save(userDetails);
    }
}
