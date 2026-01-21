package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.DoctorDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface DoctorModelInt {
	
	/**
	 * Interface of Doctor model
	 * @author Aniket Rajput
	 *
	 */
	public long add(DoctorDTO dto)throws DatabaseException,DuplicateRecordException;
	public void delete(DoctorDTO dto)throws DatabaseException;
	public void update(DoctorDTO dto)throws DatabaseException,DuplicateRecordException;
	public List list()throws DatabaseException;
	public List search(DoctorDTO dto,int pageNo,int pageSize)throws DatabaseException;
	public DoctorDTO findByPK(long pk)throws DatabaseException;
	public DoctorDTO findByName(String name)throws DatabaseException;
}
