package com.fin.sight.api.service;

import com.fin.sight.api.entities.Credentials;
import com.fin.sight.api.entities.User;
import com.fin.sight.api.repository.CredentialsRepository;
import com.fin.sight.api.repository.UserRepository;
import com.fin.sight.common.dto.*;
import com.fin.sight.common.exceptions.InvalidCredentialsException;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.GuidUtils;
import com.fin.sight.common.utils.JwtUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, CredentialsRepository credentialsRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Method to register a user.
     *
     * @param request: the API request.
     * @return the createUser response.
     */
    public CreateUserResponse registerUser(@NotNull final CreateUserRequest request) {
        if (Objects.nonNull(request.getGoogleOAuthToken())) {
            TokenData tokenData = jwtUtils.verifyGoogleOAuthToken(request.getGoogleOAuthToken());
            request.setEmailId(tokenData.emailId());
            request.setFirstName(tokenData.firstName());
            request.setLastName(tokenData.lastName());
        }
        if (doesUserExists(request.getEmailId())) {
            throw new InvalidRequestException("Email id already registered.");
        }
        String guid = GuidUtils.generateGuid();
        String jwt = jwtUtils.createJwt(request.getEmailId(), guid, request.getGoogleOAuthToken());
        User user = createUser(request, guid);
        userRepository.save(user);
        Credentials credentials = createCredentials(request, guid);
        credentialsRepository.save(credentials);
        return new CreateUserResponse(guid, jwt);
    }

    /**
     * Method to get the jwt details for the user logging in. In case of in-correct credentials, it will throw
     * {@link InvalidCredentialsException}.
     *
     * @param request: the user request.
     * @return {@link LoginUserResponse} object.
     */
    public LoginUserResponse loginUser(@NotNull final LoginUserRequest request) {
        Credentials credentials = getUserCredentials(request.emailId(), request.password());
        if (Objects.isNull(credentials)) {
            throw new InvalidCredentialsException("Invalid User name or password.");
        }
        String jwt = jwtUtils.createJwt(credentials.getEmailId(), credentials.getUserGuid(), credentials.getThirdPartyToken());
        return new LoginUserResponse(jwt, credentials.getUserGuid());
    }

    /**
     * Method to create the user object.
     *
     * @param request: the user create request.
     * @param guid:    the generated GUID.
     * @return the User object of type {@link User}
     */
    private User createUser(@NotNull final CreateUserRequest request, @NotNull final String guid) {
        User user = new User();
        user.setGuid(guid);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailId(request.getEmailId());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAge(request.getAge());
        return user;
    }

    /**
     * Method to create the user's credentials.
     *
     * @param request: the user create request.
     * @param guid:    the generated Guid.
     * @return the credentials object of type {@link Credentials}
     */
    private Credentials createCredentials(@NotNull final CreateUserRequest request, @NotNull final String guid) {
        Credentials credentials = new Credentials();
        credentials.setEmailId(request.getEmailId());
        if (Objects.nonNull(request.getPassword())) {
            credentials.setPassword(request.getPassword());
            credentials.setThirdPartySign(false);
        } else {
            credentials.setThirdPartySign(true);
        }
        credentials.setUserGuid(guid);
        credentials.setActive(true);
        return credentials;
    }

    /**
     * Method to check whether a user exists or not.
     *
     * @param emailId: The email id for the user.
     * @return true, if the user exists, else false.
     */
    private boolean doesUserExists(@NotNull final String emailId) {
        List<Credentials> credentials = credentialsRepository.findByEmailIdAndIsActive(emailId, true);
        return !credentials.isEmpty();
    }

    /**
     * Method to get the credentials of the user.
     *
     * @param emailId:  the email id for the user.
     * @param password: the password for the user.
     * @return the matched credentials or the NULL, for in-correct user details.
     */
    private Credentials getUserCredentials(@NotNull final String emailId, @NotNull final String password) {
        Credentials credentials = credentialsRepository.findByEmailIdAndPasswordAndIsActive(emailId, password, true);
        if (Objects.nonNull(credentials)) {
            return credentials;
        }
        return null;
    }
}
