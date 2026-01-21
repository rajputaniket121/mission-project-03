package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of Subject model
 * 
 * @author Aniket Rajput
 *
 */
public class SubjectModelHibImpl implements SubjectModelInt {

	public long add(SubjectDTO dto) throws DatabaseException, DuplicateRecordException {
		SubjectDTO existDto = null;
		existDto = findByName(dto.getName());
		if (existDto != null) {
			throw new DuplicateRecordException("Subject name already exist");
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
			throw new DatabaseException("Exception in Subject Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(SubjectDTO dto) throws DatabaseException {
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
			throw new DatabaseException("Exception in Subject Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(SubjectDTO dto) throws DatabaseException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		SubjectDTO existDto = findByName(dto.getName());
		// Check if updated Subject name already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Subject name is already exist");
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
			throw new DatabaseException("Exception in Subject update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public SubjectDTO findByPK(long pk) throws DatabaseException {
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (SubjectDTO) session.get(SubjectDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in getting Subject by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public SubjectDTO findByName(String name) throws DatabaseException {
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (SubjectDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in getting Subject by Name " + e.getMessage());

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
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in  Subjects list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(SubjectDTO dto) throws DatabaseException {
		return search(dto, 0, 0);
	}

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws DatabaseException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getCourseId() != null && dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
					criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
				}
				if (dto.getDescription() != null && dto.getDescription().length() > 0) {
					criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
				}
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new DatabaseException("Exception in subject search");
		} finally {
			session.close();
		}
		return list;
	}

}