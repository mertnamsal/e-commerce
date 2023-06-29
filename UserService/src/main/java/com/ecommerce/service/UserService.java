package com.ecommerce.service;

import com.ecommerce.model.UserProfile;
import com.ecommerce.rabbitmq.model.CreateUser;
import com.ecommerce.repository.IUserRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceManagerImpl<UserProfile,Long> {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public void save(CreateUser createUser) {
        UserProfile userProfile = createUserToUser(createUser);
        save(userProfile);
    }
    public UserProfile createUserToUser(CreateUser user){
        return UserProfile.builder()
                .authid(user.getAuthid())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }
}
