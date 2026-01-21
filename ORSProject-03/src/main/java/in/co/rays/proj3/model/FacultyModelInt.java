package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Interface of Faculty model
 * @author Aniket Rajput
 *
 */
public interface FacultyModelInt {
public long add(FacultyDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(FacultyDTO dto)throws DatabaseException;
public void update(FacultyDTO dto)throws DatabaseException,DatabaseException, DuplicateRecordException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(FacultyDTO dto)throws DatabaseException;
public List search(FacultyDTO dto,int pageNo,int pageSize)throws DatabaseException;
public FacultyDTO findByPK(long pk)throws DatabaseException;
public FacultyDTO  findByEmailId(String emailId)throws DatabaseException;
}
