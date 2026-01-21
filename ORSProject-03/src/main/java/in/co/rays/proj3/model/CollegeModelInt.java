package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;



public interface CollegeModelInt {
	public long add(CollegeDTO dto)throws DatabaseException,DuplicateRecordException;
	public void delete(CollegeDTO dto)throws DatabaseException;
	public void update(CollegeDTO dto)throws DatabaseException,DuplicateRecordException;
	public List list()throws DatabaseException;
	public List list(int pageNo,int pageSize)throws DatabaseException;
	public List search(CollegeDTO dto)throws DatabaseException;
	public List search(CollegeDTO dto,int pageNo,int pageSize)throws DatabaseException;
	public CollegeDTO findByPK(long pk)throws DatabaseException;
	public CollegeDTO fingByName(String name)throws DatabaseException;

}
