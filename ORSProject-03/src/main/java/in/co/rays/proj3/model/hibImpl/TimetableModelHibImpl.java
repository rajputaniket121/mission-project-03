package in.co.rays.proj3.model.hibImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.TimetableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.TimetableModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of TimeTable model
 * 
 * @author Aniket Rajput
 *
 */
public class TimetableModelHibImpl implements TimetableModelInt {

	public long add(TimetableDTO dto) throws ApplicationException, DuplicateRecordException {
		TimetableDTO existDto = null;
		existDto = checkBySemester(dto.getCourseId(), dto.getSubjectId(), dto.getSemester(), dto.getExamDate());
		if (existDto != null) {
			throw new DuplicateRecordException("Timetable already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Timetable Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(TimetableDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Timetable Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(TimetableDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		Session session = null;
		Transaction tx = null;
		TimetableDTO existDto = checkBySemester(dto.getCourseId(), dto.getSubjectId(), dto.getSemester(), dto.getExamDate());
		// Check if updated Timetable already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("Timetable is already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Timetable update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public TimetableDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (TimetableDTO) session.get(TimetableDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Timetable by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public TimetableDTO checkByCourseName(long courseId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("examDate", examDate));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimetableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Timetable by Course Name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("subjectId", subjectId));
			criteria.add(Restrictions.eq("examDate", examDate));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimetableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Timetable by Subject Name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public TimetableDTO checkBySemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("subjectId", subjectId));
			criteria.add(Restrictions.eq("semester", semester));
			criteria.add(Restrictions.eq("examDate", examDate));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimetableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Timetable by Semester " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Timetable list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(TimetableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(TimetableDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(TimetableDTO.class);
	        if (dto != null) {
	            if (dto.getId() != null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getSemester() != null && dto.getSemester().length() > 0) {
	                criteria.add(Restrictions.like("semester", dto.getSemester() + "%"));
	            }
	            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
	                criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
	            }
	            if (dto.getExamDate() != null) {
	                criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
	            }
	            if (dto.getExamTime() != null && dto.getExamTime().length() > 0) {
	                criteria.add(Restrictions.like("examTime", dto.getExamTime() + "%"));
	            }
	            if (dto.getCourseId() != null && dto.getCourseId() > 0) {
	                criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
	            }
	            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
	                criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
	            }
	            if (dto.getSubjectId() != null && dto.getSubjectId() > 0) {
	                criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
	            }
	            if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
	                criteria.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
	            }
	        }
	        if (pageSize > 0) {
	            criteria.setFirstResult((pageNo - 1) * pageSize);
	            criteria.setMaxResults(pageSize);
	        }
	        list = criteria.list();
	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in timetable search");
	    } finally {
	        session.close();
	    }
	    return list;
	}

}