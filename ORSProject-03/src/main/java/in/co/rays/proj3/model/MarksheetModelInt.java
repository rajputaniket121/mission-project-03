package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Interface of Marksheet model
 * @author Aniket Rajput
 *
 */
public interface MarksheetModelInt {
public long add(MarksheetDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(MarksheetDTO dto)throws DatabaseException;
public void update(MarksheetDTO dto)throws DatabaseException,DuplicateRecordException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(MarksheetDTO dto)throws DatabaseException;
public List search(MarksheetDTO dto,int pageNo,int pageSize)throws DatabaseException;
public MarksheetDTO findByPK(long pk)throws DatabaseException;
public MarksheetDTO findByRollNo(String rollNo)throws DatabaseException;
public List getMeritList(int pageNo,int pageSize)throws DatabaseException;
}
