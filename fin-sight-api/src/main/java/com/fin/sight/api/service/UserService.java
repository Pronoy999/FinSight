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
import com.fin.sight.common.utils.ResponseGenerator;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
     * @param request:  the API request.
     * @param apiToken: the API token.
     * @return the createUser response.
     */
    public ResponseEntity<?> registerUser(@NotNull final CreateUserRequest request, final String apiToken) {
        jwtUtils.verifyApiToken(apiToken);
        if (doesUserExists(request.getEmailId())) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.CONFLICT, "User already exists with the given email id.");
        }
        String guid = request.getUserGuid();
        User user = new User();
        if (ObjectUtils.isEmpty(guid)) {
            guid = GuidUtils.generateGuid();
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGuid(guid);
        user.setEmailId(request.getEmailId());
        user.setPhoneNumber(request.getPhoneNumber());
        user = userRepository.save(user);

        Credentials credentials = new Credentials();
        credentials.setUser(user);
        credentials.setUserGuid(guid);
        credentials.setEmailId(request.getEmailId());
        credentials.setThirdPartyToken(request.getGoogleOAuthToken());
        credentials.setThirdPartyUserId(request.getThirdPartyUserId());
        credentials.setActive(true);
        credentials = credentialsRepository.save(credentials);

        return ResponseGenerator.generateSuccessResponse(new CreateUserResponse(user.getGuid(), null), HttpStatus.CREATED);
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
    private Credentials getUserCredentials(@NotNull final String emailId, final String password) {
        if (StringUtils.isNotEmpty(password)) {
            Credentials credentials = credentialsRepository.findByEmailIdAndPasswordAndIsActive(emailId, password, true);
            if (Objects.nonNull(credentials)) {
                return credentials;
            }
        } else {
            List<Credentials> credentialsList = credentialsRepository.findByEmailIdAndIsActive(emailId, true);
            if (!credentialsList.isEmpty()) {
                return credentialsList.get(0);
            }
        }
        return null;
    }
}
