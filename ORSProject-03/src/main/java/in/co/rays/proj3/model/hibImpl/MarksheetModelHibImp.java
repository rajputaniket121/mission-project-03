package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.MarksheetModelInt;

/**
 * Hibernate implements of marksheet model
 * 
 * @author Aniket Rajput
 *
 */
public class MarksheetModelHibImp implements MarksheetModelInt {

	@Override
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
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
	public List search(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MarksheetDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
