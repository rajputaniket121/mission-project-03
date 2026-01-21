package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of course model
 * @author Aniket Rajput
 *
 */
public class CourseModelHibImpl implements CourseModelInt {

	public long add(CourseDTO dto) throws DatabaseException, DuplicateRecordException {
		CourseDTO existDto = null;
		existDto = findByName(dto.getCourseName());
		if (existDto != null) {
			throw new DuplicateRecordException("Course name already exist");
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
			throw new DatabaseException("Exception in Course Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(CourseDTO dto) throws DatabaseException {
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
			throw new DatabaseException("Exception in Course Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(CourseDTO dto) throws DatabaseException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		CourseDTO existDto = findByName(dto.getCourseName());
		// Check if updated Course name already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("Course name is already exist");
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
			throw new DatabaseException("Exception in Course update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public CourseDTO findByPK(long pk) throws DatabaseException {
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CourseDTO) session.get(CourseDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in getting Course by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public CourseDTO findByName(String name) throws DatabaseException {
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("courseName", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CourseDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in getting Course by Name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws DatabaseException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws DatabaseException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(CourseDTO.class);
	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            criteria.setFirstResult(pageNo);
	            criteria.setMaxResults(pageSize);
	        }
	        list = criteria.list();
	    } catch (HibernateException e) {
	        throw new DatabaseException("Exception : Exception in Courses list");
	    } finally {
	        session.close();
	    }

	    return list;
	}

	public List search(CourseDTO dto) throws DatabaseException {
		return search(dto, 0, 0);
	}

	public List search(CourseDTO dto, int pageNo, int pageSize) throws DatabaseException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(CourseDTO.class);
	        if (dto != null) {
	            if (dto.getId() != null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
	                criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
	            }
	            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
	                criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
	            }
	            if (dto.getDuration() != null && dto.getDuration().length() > 0) {
	                criteria.add(Restrictions.like("duration", dto.getDuration() + "%"));
	            }
	        }
	        if (pageSize > 0) {
	            criteria.setFirstResult((pageNo - 1) * pageSize);
	            criteria.setMaxResults(pageSize);
	        }
	        list = criteria.list();
	    } catch (HibernateException e) {
	        throw new DatabaseException("Exception in course search");
	    } finally {
	        session.close();
	    }
	    return list;
	}

}