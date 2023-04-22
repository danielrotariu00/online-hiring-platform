package com.licenta.idm.business.service;

import com.licenta.idm.CreateCandidateRequest;
import com.licenta.idm.CreateCandidateResponse;
import com.licenta.idm.CreateManagerRequest;
import com.licenta.idm.CreateManagerResponse;
import com.licenta.idm.CreateRecruiterRequest;
import com.licenta.idm.CreateRecruiterResponse;
import com.licenta.idm.DeleteUserRequest;
import com.licenta.idm.GetUserRequest;
import com.licenta.idm.GetUserResponse;
import com.licenta.idm.GetUsersResponse;
import com.licenta.idm.LoginRequest;
import com.licenta.idm.UpdatePasswordRequest;
import com.licenta.idm.UserResponse;
import com.licenta.idm.persistence.entity.Role;
import com.licenta.idm.persistence.entity.User;
import com.licenta.idm.persistence.entity.UserRole;
import com.licenta.idm.persistence.repository.RoleRepository;
import com.licenta.idm.persistence.repository.UserRepository;
import com.licenta.idm.persistence.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateCandidateResponse createCandidate(CreateCandidateRequest request) {
        User user = toEntity(request);

        Role candidateRole = roleRepository.findById(1).orElseThrow(
                RuntimeException::new
        );

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(candidateRole)
                .companyId(0L)
                .build();

        User savedUser = userRepository.save(user);
        UserRole savedUserRole = userRoleRepository.save(userRole);

        return toCreateCandidateResponse(savedUser, savedUserRole);
    }

    public CreateRecruiterResponse createRecruiter(CreateRecruiterRequest request) {
        User user = toEntity(request);

        Role recruiterRole = roleRepository.findById(2).orElseThrow(
                RuntimeException::new
        );

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(recruiterRole)
                .companyId(request.getCompanyId())
                .build();

        User savedUser = userRepository.save(user);
        UserRole savedUserRole = userRoleRepository.save(userRole);

        return toCreateRecruiterResponse(savedUser, savedUserRole);
    }

    public CreateManagerResponse createManager(CreateManagerRequest request) {
        User user = toEntity(request);

        Role managerRole = roleRepository.findById(3).orElseThrow(
                RuntimeException::new
        );

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(managerRole)
                .companyId(request.getCompanyId())
                .build();

        User savedUser = userRepository.save(user);
        UserRole savedUserRole = userRoleRepository.save(userRole);

        return toCreateManagerResponse(savedUser, savedUserRole);
    }

    public void createAdmin(User user) {
        Role adminRole = roleRepository.findById(4).orElseThrow(
                RuntimeException::new
        );

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(adminRole)
                .companyId(0L)
                .build();

        userRoleRepository.save(userRole);
    }

    public UserResponse updatePassword(UpdatePasswordRequest request) {
        User user = findUserOrElseThrowException(request.getUserId());
        UserRole role = userRoleRepository.findByUserId(user.getId());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return toUserResponse(userRepository.save(user), role);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        User user = findUserOrElseThrowException(request.getUserId());
        UserRole role = userRoleRepository.findByUserId(user.getId());
        GetUserResponse response = new GetUserResponse();

        response.setUser(toUserDTO(user, role));

        return response;
    }

    public GetUsersResponse getUsers() {
        List<User> users = userRepository.findAll();

        return toGetUsersResponse(users);
    }

    public UserResponse deleteUser(DeleteUserRequest request) {
        User user = findUserOrElseThrowException(request.getUserId());

        UserRole role = userRoleRepository.findByUserId(user.getId());
        userRoleRepository.delete(role);
        userRepository.deleteById(request.getUserId());

        return toUserResponse(user, role);
    }

    public com.licenta.idm.User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserRole userRole = userRoleRepository.findByUserId(user.getId());

        return toUserDTO(user, userRole);
    }

    private User toEntity(CreateCandidateRequest request) {

        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private User toEntity(CreateRecruiterRequest request) {

        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private User toEntity(CreateManagerRequest request) {

        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private UserResponse toUserResponse(User user, UserRole role) {
        UserResponse response = new UserResponse();

        response.setUser(toUserDTO(user, role));

        return response;
    }

    private CreateCandidateResponse toCreateCandidateResponse(User user, UserRole userRole) {
        CreateCandidateResponse response = new CreateCandidateResponse();

        com.licenta.idm.User userDTO = toUserDTO(user, userRole);
        response.setUser(userDTO);

        return response;
    }

    private CreateRecruiterResponse toCreateRecruiterResponse(User user, UserRole userRole) {
        CreateRecruiterResponse response = new CreateRecruiterResponse();

        com.licenta.idm.User userDTO = toUserDTO(user, userRole);
        response.setUser(userDTO);

        return response;
    }

    private CreateManagerResponse toCreateManagerResponse(User user, UserRole userRole) {
        CreateManagerResponse response = new CreateManagerResponse();

        com.licenta.idm.User userDTO = toUserDTO(user, userRole);
        response.setUser(userDTO);

        return response;
    }

    private GetUsersResponse toGetUsersResponse(List<User> users) {
        GetUsersResponse response = new GetUsersResponse();

        List<com.licenta.idm.User> userList = new ArrayList<>();

        for (User user: users) {
            UserRole userRole = userRoleRepository.findByUserId(user.getId());

            userList.add(toUserDTO(user, userRole));
        }
        response.getUsers().addAll(userList);

        return response;
    }

    private com.licenta.idm.User toUserDTO(User user, UserRole userRole) {
        com.licenta.idm.User userDTO = new com.licenta.idm.User();
        com.licenta.idm.UserRole userRoleDTO = toUserRoleDTO(userRole);

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserRole(userRoleDTO);

        return userDTO;
    }

    private com.licenta.idm.Role toRoleDTO(Role role) {
        com.licenta.idm.Role roleDTO = new com.licenta.idm.Role();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    private com.licenta.idm.UserRole toUserRoleDTO(UserRole userRole) {
        com.licenta.idm.Role roleDTO = toRoleDTO(userRole.getRole());
        com.licenta.idm.UserRole userRoleDTO = new com.licenta.idm.UserRole();

        userRoleDTO.setRole(roleDTO);
        userRoleDTO.setCompanyId(userRole.getCompanyId());

        return userRoleDTO;
    }


    private User findUserOrElseThrowException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }
}
