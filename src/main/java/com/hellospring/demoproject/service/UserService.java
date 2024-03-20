package com.hellospring.demoproject.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hellospring.demoproject.enity.Role;
import com.hellospring.demoproject.enity.User;
import com.hellospring.demoproject.repository.PagingAndSort;
import com.hellospring.demoproject.repository.RoleRepository;
import com.hellospring.demoproject.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    public static final int USER_PER_PAGE = 3;
    @Autowired
    private UserRepository repository;
    @Autowired
    private PagingAndSort repoPagingAndSort;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) repository.findAll();
    }

    public List<Role> roleList() {
        return (List<Role>) roleRepository.findAll();
    }

    public Page<User> listByPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, USER_PER_PAGE);
        return repoPagingAndSort.findAll(pageable);
    }

    public void save(User user) {
        encodePassword(user);
        repository.save(user);
    }

    private void encodePassword(User user) {
        String endcodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(endcodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = repository.getUserByEmail(email);
        if (userByEmail == null)
            return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (userByEmail != null)
                return false;
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = repository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        repository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        repository.updateEnabledStatus(id, enabled);
    }
}
