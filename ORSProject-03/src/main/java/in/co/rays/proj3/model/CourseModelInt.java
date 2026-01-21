package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Interface of Course model
 * @author Aniket Rajput
 *
 */
public interface CourseModelInt {
public long add(CourseDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(CourseDTO dto)throws DatabaseException;
public void update(CourseDTO dto)throws DatabaseException,DuplicateRecordException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(CourseDTO dto)throws DatabaseException;
public List search(CourseDTO dto,int pageNo,int pageSize)throws DatabaseException;
public CourseDTO findByPK(long pk)throws DatabaseException;
public CourseDTO findByName(String name)throws DatabaseException;
}
