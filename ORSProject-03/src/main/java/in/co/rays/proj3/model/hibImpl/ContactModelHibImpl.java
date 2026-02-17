package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.ContactDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ContactModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class ContactModelHibImpl implements ContactModelInt {

    @Override
    public long add(ContactDTO dto) throws DatabaseException, DuplicateRecordException {

        ContactDTO existDto = findByEmail(dto.getEmail());
        if (existDto != null) {
            throw new DuplicateRecordException("Email already exists");
        }
        
        existDto = findByMobileNo(dto.getMobileNo());
        if (existDto != null) {
            throw new DuplicateRecordException("Mobile Number already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Contact add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ContactDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Contact delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ContactDTO dto) throws DatabaseException, DuplicateRecordException {

        ContactDTO existDto = findByEmail(dto.getEmail());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Email already exists");
        }
        
        existDto = findByMobileNo(dto.getMobileNo());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Mobile Number already exists");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Contact update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public ContactDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        ContactDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ContactDTO) session.get(ContactDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Contact by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ContactDTO findByEmail(String email) throws DatabaseException {

        Session session = null;
        ContactDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ContactDTO.class);
            criteria.add(Restrictions.eq("email", email));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (ContactDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByEmail Contact " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ContactDTO findByMobileNo(String mobileNo) throws DatabaseException {

        Session session = null;
        ContactDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ContactDTO.class);
            criteria.add(Restrictions.eq("mobileNo", mobileNo));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (ContactDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByMobileNo Contact " + e.getMessage());
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

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ContactDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Contact list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ContactDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ContactDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getName() != null && dto.getName().length() > 0) {
                    criteria.add(Restrictions.like("name", dto.getName() + "%"));
                }

                if (dto.getEmail() != null && dto.getEmail().length() > 0) {
                    criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
                }

                if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
                    criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
                }
                
                if (dto.getMessage() != null && dto.getMessage().length() > 0) {
                    criteria.add(Restrictions.like("message", dto.getMessage() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Contact search");
        } finally {
            session.close();
        }

        return list;
    }
}