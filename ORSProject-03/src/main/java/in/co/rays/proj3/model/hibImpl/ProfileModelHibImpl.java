package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.ProfileDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ProfileModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class ProfileModelHibImpl implements ProfileModelInt{
	/**
	 * Hibernate implements of Profile model
	 * @author Aniket Rajput
	 *
	 */

	private static Logger log = Logger.getLogger(ProfileModelHibImpl.class.getName());

	@Override
	public long add(ProfileDTO dto) throws DatabaseException, DuplicateRecordException {
		log.info("ProfileModelHibImpl add started");

		ProfileDTO existDto = findByName(dto.getFullName());
		if (existDto != null) {
			throw new DuplicateRecordException("Profile name already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			log.info("Profile added successfully");

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.severe("Exception in Profile add");
			throw new DatabaseException("Exception in Profile add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ProfileDTO dto) throws DatabaseException {
		log.info("ProfileModelHibImpl delete started");

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.info("Profile deleted successfully");

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.severe("Exception in Profile delete");
			throw new DatabaseException("Exception in Profile delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ProfileDTO dto) throws DatabaseException, DuplicateRecordException {
		log.info("ProfileModelHibImpl update started");

		ProfileDTO existDto = findByName(dto.getFullName());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Profile name already exists");
		}

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
			log.info("Profile updated successfully");

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.severe("Exception in Profile update");
			throw new DatabaseException("Exception in Profile update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ProfileDTO findByPK(long pk) throws DatabaseException {
		log.info("ProfileModelHibImpl findByPK started");

		Session session = null;
		ProfileDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ProfileDTO) session.get(ProfileDTO.class, pk);

		} catch (HibernateException e) {
			log.severe("Exception in findByPK Profile");
			throw new DatabaseException("Exception in getting Profile by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public ProfileDTO findByName(String name) throws DatabaseException {
		log.info("ProfileModelHibImpl findByName started");

		Session session = null;
		ProfileDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProfileDTO.class);
			criteria.add(Restrictions.eq("fullName", name));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ProfileDTO) list.get(0);
			}

		} catch (HibernateException e) {
			log.severe("Exception in findByFullName Profile");
			e.printStackTrace();
			throw new DatabaseException("Exception in findByFullName Profile " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws DatabaseException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws DatabaseException {
		log.info("ProfileModelHibImpl list started");

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProfileDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			log.severe("Exception in Profile list");
			throw new DatabaseException("Exception in Profile list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ProfileDTO dto, int pageNo, int pageSize) throws DatabaseException {
		log.info("ProfileModelHibImpl search started");

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProfileDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getFullName() != null && dto.getFullName().length() > 0) {
					criteria.add(Restrictions.like("fullName", dto.getFullName() + "%"));
				}

				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender()+"%"));
				}

				if (dto.getProfileStatus() != null && dto.getProfileStatus().length() > 0) {
					criteria.add(Restrictions.like("profileStatus", dto.getProfileStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			log.severe("Exception in Profile search");
			throw new DatabaseException("Exception in Profile search");
		} finally {
			session.close();
		}

		return list;
	}

}
