package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.StudentModelInt;


/**
 * Hibernate implements of Student model
 * @author Aniket Rajput
 *
 */
public class StudentModelHibImp implements StudentModelInt {

	@Override
	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
