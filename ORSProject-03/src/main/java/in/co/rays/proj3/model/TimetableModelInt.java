package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.TimetableDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;


/**
 * Interface of Timetable model
 * @author Aniket Rajput
 *
 */
public interface TimetableModelInt {
public long add(TimetableDTO dto)throws DatabaseException,DuplicateRecordException;
public void delete(TimetableDTO dto)throws DatabaseException;
public void update(TimetableDTO dto)throws DatabaseException,DuplicateRecordException,DatabaseException;
public List list()throws DatabaseException;
public List list(int pageNo,int pageSize)throws DatabaseException;
public List search(TimetableDTO dto)throws DatabaseException;
public List search(TimetableDTO dto,int pageNo,int pageSize)throws DatabaseException;
public TimetableDTO findByPK(long pk)throws DatabaseException;
public TimetableDTO checkByCourseName(long courseId,java.util.Date examDate)throws DatabaseException,DuplicateRecordException;
public TimetableDTO checkBySubjectName(long courseId,long subjectId,java.util.Date examDate)throws DatabaseException,DuplicateRecordException;
public TimetableDTO checkBySemester(long courseId,long subjectId,String semester,java.util.Date examDate)throws DatabaseException,DuplicateRecordException;

}
