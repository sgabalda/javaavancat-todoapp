/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.user.persistence;

import java.util.List;
import net.sergigabol.todoapp.user.User;

/**
 *
 * @author gabalca
 */
public interface UserDao {
    public User getUserById(long id);
    public User getUserByUsername(String username);
    public List<User> getAllUsers();
    public void deleteUser(long id);
    public void createUser(User user);
}
