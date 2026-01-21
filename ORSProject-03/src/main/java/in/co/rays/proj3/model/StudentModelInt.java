package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;



/**
 * Interface of Student model
 * @author Aniket Rajput
 *
 */
public interface StudentModelInt {
public long add(StudentDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(StudentDTO dto)throws DatabaseException;
public void update(StudentDTO dto)throws DatabaseException,DuplicateRecordException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(StudentDTO dto)throws DatabaseException;
public List search(StudentDTO dto,int pageNo,int pageSize)throws DatabaseException;
public StudentDTO findByPK(long pk)throws DatabaseException;
public StudentDTO findByEmailId(String emailId)throws DatabaseException;
}
