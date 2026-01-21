package in.co.rays.proj3.model.hibImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class CollegeModelHibImpl implements CollegeModelInt{

	public long add(CollegeDTO dto) throws DatabaseException, DuplicateRecordException {
		CollegeDTO existDto = null;
		existDto = fingByName(dto.getName());
		if (existDto != null) {
			throw new DuplicateRecordException("College name already exist");
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
			throw new DatabaseException("Exception in College Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	public void delete(CollegeDTO dto) throws DatabaseException {
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
			throw new DatabaseException("Exception in College Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(CollegeDTO dto) throws DatabaseException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		CollegeDTO existDto = fingByName(dto.getName());
		// Check if updated College name already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("College name is already exist");
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
			throw new DatabaseException("Exception in College update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public CollegeDTO findByPK(long pk) throws DatabaseException {
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CollegeDTO) session.get(CollegeDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in getting College by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public CollegeDTO fingByName(String name) throws DatabaseException {
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CollegeDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in getting College by Name " + e.getMessage());

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
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new DatabaseException("Exception : Exception in  Colleges list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(CollegeDTO dto) throws DatabaseException {
		return search(dto, 0, 0);
	}

	public List search(CollegeDTO dto, int pageNo, int pageSize) throws DatabaseException {
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CollegeDTO.class);
			if(dto.getId()>0){
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null&& dto.getName().length()>0){
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getAddress()!=null&& dto.getAddress().length()>0){
				criteria.add(Restrictions.like("address", dto.getAddress()+"%"));
			}
			if(dto.getCity()!=null&& dto.getCity().length()>0){
				criteria.add(Restrictions.like("city", dto.getCity()+"%"));
			}
			if(dto.getState()!=null&& dto.getState().length()>0){
				criteria.add(Restrictions.like("state", dto.getState()+"%"));
			}
			if(dto.getPhoneNo()!=null&& dto.getPhoneNo().length()>0){
				criteria.add(Restrictions.like("phoneNo", dto.getPhoneNo()+"%"));
			}
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
            
            throw new DatabaseException("Exception in college search");
        } finally {
            session.close();
        }
		return list;
	}

}