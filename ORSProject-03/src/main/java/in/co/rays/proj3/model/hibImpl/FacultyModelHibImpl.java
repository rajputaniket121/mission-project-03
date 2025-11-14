package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of Faculty model
 * @author Aniket Rajput
 *
 */
public class FacultyModelHibImpl implements FacultyModelInt{

	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		FacultyDTO existDto = null;
		existDto = findByEmailId(dto.getEmailId());
		if (existDto != null) {
			throw new DuplicateRecordException("Email id already exist");
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
			throw new ApplicationException("Exception in Faculty Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(FacultyDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Faculty Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(FacultyDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		FacultyDTO existDto = findByEmailId(dto.getEmailId());
		// Check if updated Email id already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("Email id is already exist");
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
			throw new ApplicationException("Exception in Faculty update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public FacultyDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (FacultyDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Faculty by Email " + e.getMessage());

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
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Faculty list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(FacultyDTO.class);
	        if (dto != null) {
	            if (dto.getId() != null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
	                criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
	            }
	            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
	                criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
	            }
	            if (dto.getGender() != null && dto.getGender().length() > 0) {
	                criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
	            }
	            if (dto.getQualification() != null && dto.getQualification().length() > 0) {
	                criteria.add(Restrictions.like("qualification", dto.getQualification() + "%"));
	            }
	            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
	                criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
	            }
	            if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
	                criteria.add(Restrictions.like("emailId", dto.getEmailId() + "%"));
	            }
	            if (dto.getCollegeId() > 0) {
	                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
	            }
	            if (dto.getCourseId() > 0) {
	                criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
	            }
	            if (dto.getSubjectId() > 0) {
	                criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
	            }
	            if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
	                criteria.add(Restrictions.like("collegeName", dto.getCollegeName() + "%"));
	            }
	            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
	                criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
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
	        throw new ApplicationException("Exception in faculty search");
	    } finally {
	        session.close();
	    }
	    return list;
	}

}