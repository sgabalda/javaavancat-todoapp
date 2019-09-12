/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.persistence.jdbc;

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
import net.sergigabol.todoapp.task.Tasca;
import net.sergigabol.todoapp.task.persistence.TascaDao;
import net.sergigabol.todoapp.task.persistence.TascaStorageException;
import net.sergigabol.todoapp.user.User;
import net.sergigabol.todoapp.user.persistence.UserStorageException;
import static net.sergigabol.todoapp.user.persistence.jdbc.JDBCUserDao.DELETE;
import static net.sergigabol.todoapp.user.persistence.jdbc.JDBCUserDao.SELECT_ALL;
import static net.sergigabol.todoapp.user.persistence.jdbc.JDBCUserDao.SELECT_ID;

/**
 *
 * @author gabalca
 */
@Dependent
public class JDBCTascaDao implements TascaDao{
    
    private static final Logger LOG = Logger.getLogger(JDBCTascaDao.class.getName());

    public static final String SELECT_ID="SELECT * FROM TASK_TODO WHERE taskid = ?";
    public static final String SELECT_ALL="SELECT * FROM TASK_TODO WHERE userid = ?";
    public static final String INSERT="INSERT INTO TASK_TODO ("
            + "userid,DESCRIPTION,ACABADA)"
            + " VALUES (?,?,?)";
    public static final String DELETE="DELETE FROM TASK_TODO WHERE taskid = ?";
    
    @Resource(name  = "jdbc/todopool")
    private DataSource dataSource;

    @Override
    public void creaTasca(Tasca t) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS)){
            
            ps1.setLong(1, t.getUserid());
            ps1.setString(2, t.getDescripcio());
            ps1.setBoolean(3, t.getAcabada()==true);
            
            ps1.executeUpdate();
            
            ResultSet rs= ps1.getGeneratedKeys();
            
            if(rs.next()){
                t.setTascaid(rs.getLong(1));
                LOG.log(Level.INFO, "Tasca saved {0}", t);
            }else{
                LOG.log(Level.SEVERE, "Tasca saved but no id returned {0}", t);
                throw new TascaStorageException(t,null,"INSERT","Error inserting new Tasca:"
                        + " no id generated",null);
            }
                        
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error saving Tasca {0}", t);
                throw new TascaStorageException(t,null,"INSERT","Error inserting new Tasca:"
                        + " no id generated",ex);
        }
    }

    @Override
    public void eliminaTasca(long id) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(DELETE)){
            
            ps1.setLong(1, id);
            
            ps1.executeUpdate();

        } catch (SQLException ex) {
            throw new TascaStorageException(null,id,"DELETE",
                    "Deleting Tasca by id "+id, ex);
        }
    }

    @Override
    public List<Tasca> getTasquesUsuari(long userid) {
        List<Tasca> result= new ArrayList<>();
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(SELECT_ALL)){
            ps1.setLong(1, userid);
            
            ResultSet rs = ps1.executeQuery();
            
            while(rs.next()){
                Tasca us = getTascaFromResultSet(rs);
                result.add(us);
            }
            return result;
        } catch (SQLException ex) {
            throw new TascaStorageException(null,null,"SELECT_ALL",
                    "Getting all tasques for user "+userid, ex);
        }    
    }

    @Override
    public Tasca getTasca(long id) {
        try(Connection con=dataSource.getConnection();
                PreparedStatement ps1=con.prepareStatement(SELECT_ID)){
            ps1.setLong(1, id);
            
            ResultSet rs = ps1.executeQuery();
            
            if(rs.next()){
                Tasca result = getTascaFromResultSet(rs);
                return result;
            }
            return null;
        } catch (SQLException ex) {
            throw new TascaStorageException(null,id,"SELECT_ID",
                    "Getting tasca by id "+id, ex);
        }
    }
    
    private Tasca getTascaFromResultSet(ResultSet rs) throws SQLException{
        Tasca result=new Tasca();
        result.setTascaid(rs.getLong("taskid"));
        result.setUserid(rs.getLong("userid"));
        result.setDescripcio(rs.getString("DESCRIPTION"));
        result.setAcabada(rs.getBoolean("ACABADA"));
        return result;
    }
    
}
