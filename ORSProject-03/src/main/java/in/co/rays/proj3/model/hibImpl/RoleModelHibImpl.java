package in.co.rays.proj3.model.hibImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class RoleModelHibImpl implements RoleModelInt{

	public long add(RoleDTO dto) throws DatabaseException, DuplicateRecordException {
		RoleDTO existDto = null;
		existDto = findByName(dto.getName());
		if (existDto != null) {
			throw new DuplicateRecordException("Role name already exist");
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
			throw new DatabaseException("Exception in Role Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(RoleDTO dto) throws DatabaseException {
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
			throw new DatabaseException("Exception in Role Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(RoleDTO dto) throws DatabaseException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		RoleDTO existDto = findByName(dto.getName());
		// Check if updated Role name already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("Role name is already exist");
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
			throw new DatabaseException("Exception in Role update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public RoleDTO findByPK(long pk) throws DatabaseException {
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (RoleDTO) session.get(RoleDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in getting Role by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public RoleDTO findByName(String name) throws DatabaseException {
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (RoleDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in getting Role by Name " + e.getMessage());

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
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in  Roles list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(RoleDTO dto) throws DatabaseException {
		return search(dto, 0, 0);
	}

	public List search(RoleDTO dto, int pageNo, int pageSize) throws DatabaseException {
	    Session session = null;
	    List list = null;
	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(RoleDTO.class);
	        if (dto != null) {
	            if (dto.getId() != null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getName() != null && dto.getName().length() > 0) {
	                criteria.add(Restrictions.like("name", dto.getName() + "%"));
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
	        throw new DatabaseException("Exception in role search");
	    } finally {
	        session.close();
	    }
	    return list;
	}

}