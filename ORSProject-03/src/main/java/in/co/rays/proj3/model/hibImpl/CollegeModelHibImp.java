package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;

public class CollegeModelHibImp implements CollegeModelInt{

	@Override
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(CollegeDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
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
	public List search(CollegeDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollegeDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollegeDTO fingByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
