/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.user.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;
import net.sergigabol.todoapp.user.User;
import net.sergigabol.todoapp.user.persistence.UserDao;
import net.sergigabol.todoapp.user.persistence.UserStorageException;

/**
 *
 * @author gabalca
 */
@Dependent
public class JDBCUserDao implements UserDao{

    private static final Logger LOG = Logger.getLogger(JDBCUserDao.class.getName());

    public static final String SELECT_ID="SELECT * FROM USERS_TODO WHERE userid = ?";
    public static final String SELECT_ALL="SELECT * FROM USERS_TODO";
    public static final String INSERT="INSERT INTO USERS_TODO ("
            + "USERNAME,PASSWORD,ADMINUSER)"
            + " VALUES (?,?,?)";
    public static final String DELETE="DELETE FROM USERS_TODO WHERE userid = ?";
    
    @Resource(name  = "jdbc/todopool")
    private DataSource dataSource;
    
    @Override
    public User getUserById(long id) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(SELECT_ID)){
            ps1.setLong(1, id);
            
            ResultSet rs = ps1.executeQuery();
            
            if(rs.next()){
                User result = getUserFromResultSet(rs);
                return result;
            }
            return null;
        } catch (SQLException ex) {
            throw new UserStorageException(id,"SELECT_ID",
                    "Getting user by id "+id, ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result= new ArrayList<>();
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(SELECT_ALL)){
            
            ResultSet rs = ps1.executeQuery();
            
            while(rs.next()){
                User us = getUserFromResultSet(rs);
                result.add(us);
            }
            return result;
        } catch (SQLException ex) {
            throw new UserStorageException((User)null,"SELECT_ALL",
                    "Getting all users ", ex);
        }    
    }

    @Override
    public void deleteUser(long id) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(DELETE)){
            
            ps1.setLong(1, id);
            
            ps1.executeUpdate();

        } catch (SQLException ex) {
            throw new UserStorageException(id,"DELETE",
                    "Deleting user by id "+id, ex);
        }
    }

    @Override
    public void createUser(User user) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS)){
            
            ps1.setString(1, user.getUsername());
            ps1.setString(2, user.getPassword());
            ps1.setBoolean(3, user.getAdmin()==true);
            
            ps1.executeUpdate();
            
            ResultSet rs= ps1.getGeneratedKeys();
            
            if(rs.next()){
                user.setUserid(rs.getLong(1));
                LOG.log(Level.INFO, "User saved {0}", user);
            }else{
                LOG.log(Level.SEVERE, "User saved but no id returned {0}", user);
                throw new UserStorageException(user,"INSERT","Error inserting new item:"
                        + " no id generated",null);
            }
                        
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error saving user {0}", user);
                throw new UserStorageException(user,"INSERT","Error inserting new item:"
                        + " no id generated",ex);
        }
    }
    
    private User getUserFromResultSet(ResultSet rs) throws SQLException{
        User result=new User();
        result.setUserid(rs.getLong("userid"));
        result.setUsername(rs.getString("USERNAME"));
        result.setPassword(rs.getString("PASSWORD"));
        result.setAdmin(rs.getBoolean("ADMINUSER"));
        return result;
    }
    
}
