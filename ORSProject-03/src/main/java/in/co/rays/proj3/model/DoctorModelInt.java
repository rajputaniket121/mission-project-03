package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.DoctorDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface DoctorModelInt {
	
	/**
	 * Interface of Doctor model
	 * @author Aniket Rajput
	 *
	 */
	public long add(DoctorDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(DoctorDTO dto)throws ApplicationException;
	public void update(DoctorDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List search(DoctorDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public DoctorDTO findByPK(long pk)throws ApplicationException;
	public DoctorDTO findByName(String name)throws ApplicationException;
}
