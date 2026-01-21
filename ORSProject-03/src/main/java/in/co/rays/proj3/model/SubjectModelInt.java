package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;


/**
 * Interface of Subject model
 * @author Aniket Rajput
 *
 */
public interface SubjectModelInt {
public long add(SubjectDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(SubjectDTO dto)throws DatabaseException;
public void update(SubjectDTO dto)throws DatabaseException,DuplicateRecordException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(SubjectDTO dto)throws DatabaseException;
public List search(SubjectDTO dto,int pageNo,int pageSize)throws DatabaseException;
public SubjectDTO findByPK(long pk)throws DatabaseException;
public SubjectDTO findByName(String name)throws DatabaseException;
}
