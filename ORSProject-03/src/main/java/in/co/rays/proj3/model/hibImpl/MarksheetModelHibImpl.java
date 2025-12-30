package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StudentModelInt;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.MarksheetModelInt;
import in.co.rays.proj3.utill.HibDataSource;

/**
 * Hibernate implements of marksheet model
 * 
 * @author Aniket Rajput
 *
 */
public class MarksheetModelHibImpl implements MarksheetModelInt {

	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {

		// get Student Name
		StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());

		MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());
		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll No already exist");
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
			throw new ApplicationException("Exception in Marksheet Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(MarksheetDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Marksheet Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());

		MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());
		// Check if updated Roll No already exist
		if (duplicateMarksheet != null && duplicateMarksheet.getId() != dto.getId()) {
			 throw new DuplicateRecordException("Roll No is already exist");
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
			throw new ApplicationException("Exception in Marksheet update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public MarksheetDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (MarksheetDTO) session.get(MarksheetDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Marksheet by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (MarksheetDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Marksheet by Roll No " + e.getMessage());

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
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Marksheet list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(MarksheetDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(MarksheetDTO.class);
	        
	        if (dto != null) {
	            if (dto.getId() != null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
	                criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
	            }
	            if (dto.getName() != null && dto.getName().length() > 0) {
	                criteria.add(Restrictions.like("name", dto.getName() + "%"));
	            }
	            if (dto.getPhysics() != null && dto.getPhysics() > 0) {
	                criteria.add(Restrictions.eq("physics", dto.getPhysics()));
	            }
	            if (dto.getChemistry() != null && dto.getChemistry() > 0) {
	                criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
	            }
	            if (dto.getMaths() != null && dto.getMaths() > 0) {
	                criteria.add(Restrictions.eq("maths", dto.getMaths()));
	            }
	        }
	        
	        if (pageSize > 0) {
	            criteria.setFirstResult((pageNo - 1) * pageSize);
	            criteria.setMaxResults(pageSize);
	        }
	        list = criteria.list();
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        throw new ApplicationException("Exception in Marksheet Search " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return list;
	}

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Query q = session.createQuery("from MarksheetDTO where (physics + chemistry + maths) >= 180 order by (physics + chemistry + maths) desc");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				q.setFirstResult(pageNo);
				q.setMaxResults(pageSize);
			}
			list = q.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Merit List " + e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

}