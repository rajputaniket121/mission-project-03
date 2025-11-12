package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.FacultyModelInt;

/**
 * Hibernate implements of Faculty model
 * @author Aniket Rajput
 *
 */
public class FacultyModelHibImp implements FacultyModelInt{

	@Override
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(FacultyDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FacultyDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException {
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
	public List search(FacultyDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacultyDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
