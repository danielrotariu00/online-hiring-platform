package com.licenta.idm.presentation.endpoint;

import com.licenta.idm.AuthorizeRequest;
import com.licenta.idm.AuthorizeResponse;
import com.licenta.idm.CreateCandidateRequest;
import com.licenta.idm.CreateCandidateResponse;
import com.licenta.idm.CreateManagerRequest;
import com.licenta.idm.CreateManagerResponse;
import com.licenta.idm.CreateRecruiterRequest;
import com.licenta.idm.CreateRecruiterResponse;
import com.licenta.idm.DeleteUserRequest;
import com.licenta.idm.GetUserRequest;
import com.licenta.idm.GetUserResponse;
import com.licenta.idm.GetUsersRequest;
import com.licenta.idm.GetUsersResponse;
import com.licenta.idm.LoginRequest;
import com.licenta.idm.LoginResponse;
import com.licenta.idm.LogoutRequest;
import com.licenta.idm.UpdatePasswordRequest;
import com.licenta.idm.User;
import com.licenta.idm.UserResponse;
import com.licenta.idm.business.service.JwtProvider;
import com.licenta.idm.business.service.UserService;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://licenta.com/idm";

	private final UserService userService;
	private final JwtProvider jwtProvider;

	@Autowired
	public UserEndpoint(UserService userService, JwtProvider jwtProvider) {
		this.userService = userService;
		this.jwtProvider = jwtProvider;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCandidateRequest")
	@ResponsePayload
	public CreateCandidateResponse createCandidate(@RequestPayload CreateCandidateRequest request) {
		return userService.createCandidate(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createRecruiterRequest")
	@ResponsePayload
	public CreateRecruiterResponse createRecruiter(@RequestPayload CreateRecruiterRequest request) {
		return userService.createRecruiter(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createManagerRequest")
	@ResponsePayload
	public CreateManagerResponse createManager(@RequestPayload CreateManagerRequest request) {
		return userService.createManager(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePasswordRequest")
	@ResponsePayload
	public UserResponse updatePassword(@RequestPayload UpdatePasswordRequest request) throws MalformedClaimException {
		if (jwtProvider.isAuthorized(request.getToken(), request.getUserId())) {
			return userService.updatePassword(request);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
	@ResponsePayload
	public GetUserResponse getUser(@RequestPayload GetUserRequest request) throws MalformedClaimException {
		if (jwtProvider.hasAdminRights(request.getToken()) || jwtProvider.hasManagerRights(request.getToken())) {
			return userService.getUser(request);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
	@ResponsePayload
	public GetUsersResponse getUsers(@RequestPayload GetUsersRequest request) throws MalformedClaimException {
		if (jwtProvider.hasAdminRights(request.getToken())) {
			return userService.getUsers();
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
	@ResponsePayload
	public UserResponse deleteUser(@RequestPayload DeleteUserRequest request) throws MalformedClaimException {
		if (jwtProvider.hasAdminRights(request.getToken()) || jwtProvider.hasManagerRights(request.getToken())) {
			return userService.deleteUser(request);
		 } else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		 }
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
	@ResponsePayload
	public LoginResponse login(@RequestPayload LoginRequest request) throws JoseException {
		User user = userService.login(request);
		String jwt = jwtProvider.generateJwt(user);
		LoginResponse response = new LoginResponse();

		response.setToken(jwt);
		response.setUser(user);

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "logoutRequest")
	@ResponsePayload
	public void logout(@RequestPayload LogoutRequest request) {
		jwtProvider.invalidate(request.getToken());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizeRequest")
	@ResponsePayload
	public AuthorizeResponse authorize(@RequestPayload AuthorizeRequest request) throws MalformedClaimException {
		JwtClaims claims = jwtProvider.validate(request.getToken());
		AuthorizeResponse response = new AuthorizeResponse();

		long userId = Long.parseLong(claims.getSubject());
		int roleId = Integer.parseInt(claims.getStringClaimValue("roleId"));
		long companyId = Long.parseLong(claims.getStringClaimValue("companyId"));

		response.setUserId(userId);
		response.setRoleId(roleId);
		response.setCompanyId(companyId);

		return response;
	}
}
