package in.co.rays.proj3.model.hibImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.TimetableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.TimetableModelInt;


/**
 * Hibernate implements of TimeTable model
 * 
 * @author Aniket Rajput
 *
 */
public class TimetableModelHibImp implements TimetableModelInt {

	@Override
	public long add(TimetableDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(TimetableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TimetableDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
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
	public List search(TimetableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(TimetableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimetableDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimetableDTO checkByCourseName(long courseId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimetableDTO checkBySemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}


}
