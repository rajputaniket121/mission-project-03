package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of Student model
 * @author Aniket Rajput
 *
 */
public class StudentModelHibImpl implements StudentModelInt {

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		StudentDTO existDto = null;
		existDto = findByEmailId(dto.getEmail());
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
			throw new ApplicationException("Exception in Student Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(StudentDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Student Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		StudentDTO existDto = findByEmailId(dto.getEmail());
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
			throw new ApplicationException("Exception in Student update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public StudentDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		StudentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (StudentDTO) session.get(StudentDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Student by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		Session session = null;
		StudentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", emailId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (StudentDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Student by Email " + e.getMessage());

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
			Criteria criteria = session.createCriteria(StudentDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Students list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(StudentDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(StudentDTO.class);
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
	            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
	                criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
	            }
	            if (dto.getEmail() != null && dto.getEmail().length() > 0) {
	                criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
	            }
	            if (dto.getCollegeId() != null && dto.getCollegeId() > 0) {
	                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
	            }
	            if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
	                criteria.add(Restrictions.like("collegeName", dto.getCollegeName() + "%"));
	            }
	        }
	        if (pageSize > 0) {
	            criteria.setFirstResult((pageNo - 1) * pageSize);
	            criteria.setMaxResults(pageSize);
	        }
	        list = criteria.list();
	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in student search");
	    } finally {
	        session.close();
	    }
	    return list;
	}

}