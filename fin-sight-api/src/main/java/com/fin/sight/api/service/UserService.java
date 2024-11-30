package com.fin.sight.api.service;

import com.fin.sight.common.dto.CreateUserRequest;
import com.fin.sight.common.dto.CreateUserResponse;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.utils.GuidUtils;
import com.fin.sight.api.entities.Credentials;
import com.fin.sight.api.entities.User;
import com.fin.sight.api.repository.CredentialsRepository;
import com.fin.sight.api.repository.UserRepository;
import com.fin.sight.common.utils.JwtUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
     * @return the create User response.
     */
    public CreateUserResponse registerUser(@NotNull final CreateUserRequest request) {
        try {
            if (doesUserExists(request.emailId())) {
                throw new InvalidRequestException("Email id already registered.");
            }
            String guid = GuidUtils.generateGuid();
            String jwt = jwtUtils.createJwt(request.emailId(), guid);
            User user = createUser(request, guid);
            userRepository.save(user);
            Credentials credentials = createCredentials(request, guid);
            credentialsRepository.save(credentials);
            return new CreateUserResponse(guid, jwt);
        } catch (Exception e) {
            log.error("Exception occurred:", e);
            return null;
        }
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
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmailId(request.emailId());
        user.setPhoneNumber(request.phoneNumber());
        user.setAge(request.age());
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
        credentials.setEmailId(request.emailId());
        credentials.setPassword(request.password());
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
}
