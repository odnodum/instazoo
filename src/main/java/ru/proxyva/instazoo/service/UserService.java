package ru.proxyva.instazoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.proxyva.instazoo.entity.User;
import ru.proxyva.instazoo.entity.enums.ERole;
import ru.proxyva.instazoo.exceptions.UserExistException;
import ru.proxyva.instazoo.payload.request.SignupRequest;
import ru.proxyva.instazoo.repository.UserRepository;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception exception) {
            LOG.error("Error during registration. {}", exception.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }
}
