package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;

/**
 * Interface of User model
 * @author Aniket Rajput
 *
 */
public interface UserModelInt {
public long add(UserDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(UserDTO dto)throws DatabaseException;
public void update(UserDTO dto)throws DatabaseException,DuplicateRecordException;
public UserDTO findByPK(long pk)throws DatabaseException;
public UserDTO findByLogin(String login)throws DatabaseException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(UserDTO dto,int pageNo,int pageSize)throws DatabaseException;
public List search(UserDTO dto)throws DatabaseException;
public boolean changePassword(long id,String newPassword,String oldPassword)throws ApplicationException, RecordNotFoundException;
public UserDTO authenticate(String login,String password)throws DatabaseException;
public boolean forgetPassword(String login)throws ApplicationException, RecordNotFoundException;
public boolean resetPassword(UserDTO dto)throws ApplicationException,RecordNotFoundException;
public long registerUser(UserDTO dto)throws DuplicateRecordException,ApplicationException;
public List getRoles(UserDTO dto)throws DatabaseException;

}
