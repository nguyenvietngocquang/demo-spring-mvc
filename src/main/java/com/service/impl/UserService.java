package com.service.impl;

import com.enu.ErrorCode;
import com.exception.CustomException;
import com.input.create.UserFormCreate;
import com.input.login.UserFormLogin;
import com.input.search.UserSearch;
import com.input.update.UserFormUpdate;
import com.model.User;
import com.repository.impl.UserRepository;
import com.service.BaseService;
import org.apache.log4j.Logger;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService extends BaseService<UserRepository, User, Long> {

    Logger logger = Logger.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserRepository getRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            users = findAll();
        } catch (NullPointerException e) {
            logger.info(e);
        }
        return users;
    }

    public User create(UserFormCreate userFormCreate) throws CustomException {
        User user = null;
        user = userRepository.findByEmail(userFormCreate.getEmail());
        if (user != null)
            throw new CustomException(ErrorCode.EMAIL_EXISTED);
        user = userRepository.findByUserName(userFormCreate.getUserName());
        if (user != null)
            throw new CustomException(ErrorCode.USERNAME_EXISTED);
        user = new User(userFormCreate);
        user = userRepository.insert(user);
        return user;
    }

    public User update(UserFormUpdate userFormUpdate) {
        User user = userRepository.findOne(userFormUpdate.getId());
        user.setName(userFormUpdate.getName());
        user.setPassword(userFormUpdate.getPassword());
        user = userRepository.update(user);
        return user;
    }

    public User login(UserFormLogin userFormLogin) {
        String input = userFormLogin.getUserName();
        User user;
        if (input.contains("@"))
            user = userRepository.findByEmail(input);
        else
            user = userRepository.findByUserName(input);
        if (user == null)
            return null;
        else {
            String password = user.getPassword();
            if (userFormLogin.getPassword().equals(password))
                return user;
            return null;
        }
    }

    public List<User> search(UserSearch userSearch){
        String queryStr = buildQuery(userSearch);
        HashMap<String,Object> params = buildParams(userSearch);
        Query query = userRepository.buildQueryHasParameters(queryStr,false,params);
        List<User> users = query.getResultList();
        return users;
    }

    private HashMap<String,Object> buildParams(UserSearch userSearch){
        HashMap<String,Object> params = new HashMap<>();
        if (!userSearch.getEmail().isEmpty())
            params.put("email",userSearch.getEmail());
        if (!userSearch.getUserName().isEmpty())
            params.put("email",userSearch.getUserName());
        return params;
    }

    private String buildQuery(UserSearch userSearch){
        String query = " from User u where 1=1 ";
        if (!userSearch.getEmail().isEmpty())
            query += "and u.email = :email";
        if (!userSearch.getUserName().isEmpty())
            query += "and u.userName = :userName";
        return query;
    }
}
