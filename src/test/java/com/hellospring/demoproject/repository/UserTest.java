package com.hellospring.demoproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.hellospring.demoproject.enity.Role;
import com.hellospring.demoproject.enity.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PagingAndSort repoPagingAndSort;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        User userRavi = User.builder()
                .email("rav1444i@gmail.com")
                .password("ruva123")
                .firstName("Tuan")
                .lastName("Hoang Xuan")
                .build();
        Role roleEditor = Role.builder().id(2).build();
        Role roleAssist = Role.builder().id(3).build();
        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssist);
        User savedUser = repository.save(userRavi);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    @Transactional
    public void testCreateUserWithTwoRole() {
        User userRavi = User.builder()
                .email("rav124i@gmail.com")
                .password("ruva123")
                .firstName("Tuan")
                .lastName("Hoang Xuan")
                .build();
        Role roleEditor = Role.builder().id(1).build();
        Role roleAssist = Role.builder().id(2).build();
        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssist);
        User savedUser = repository.save(userRavi);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers() {
        Iterable<User> userList = repository.findAll();
        userList.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserByID() {
        User user = repository.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User user = repository.findById(1).get();
        user.setEnabled(true);
        user.setEmail("hoangxthinh@fpt.edu.vn");
        repository.save(user);
    }

    @Test
    public void testUpdateUserRoles() {
        User userThinh = repository.findById(9).get();
        Role roleEditor = Role.builder().id(2).build();
        Role roleAssist = Role.builder().id(3).build();
        userThinh.getRoles().remove(roleEditor);
        userThinh.addRole(roleAssist);
        repository.save(userThinh);
    }

    @Test
    public void testDeleteUser() {
        Integer userID = 7;
        repository.deleteById(userID);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "hoangxthinh@fpt.edu.vn";
        User user = repository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountByID() {
        Integer id = 1;
        Long countByID = repository.countById(id);
        assertThat(countByID).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisabled() {
        Integer id = 5;
        repository.updateEnabledStatus(id, true);
        // Iterable<User> userList = repository.findAll();
        // userList.forEach(user -> System.out.println(user));

    }

    @Test
    public void testEnabled() {
        Integer id = 5;
        repository.updateEnabledStatus(id, true);
        // Iterable<User> userList = repository.findAll();
        // userList.forEach(user -> System.out.println(user));
        int i = 1;

    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repoPagingAndSort.findAll(pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUser() {
        String keyword = "thinh";
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repository.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
