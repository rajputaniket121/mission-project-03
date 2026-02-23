package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.DepartmentDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.DepartmentModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class DepartmentModelHibImpl implements DepartmentModelInt {

    @Override
    public long add(DepartmentDTO dto) throws DatabaseException, DuplicateRecordException {

        DepartmentDTO existDto = findByDepartmentCode(dto.getDepartmentCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Department Code already exists");
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
            throw new DatabaseException("Exception in Department add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(DepartmentDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Department delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DepartmentDTO dto) throws DatabaseException, DuplicateRecordException {

        DepartmentDTO existDto = findByDepartmentCode(dto.getDepartmentCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Department Code already exists");
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
            throw new DatabaseException("Exception in Department update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public DepartmentDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        DepartmentDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (DepartmentDTO) session.get(DepartmentDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Department by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public DepartmentDTO findByDepartmentCode(String departmentCode) throws DatabaseException {

        Session session = null;
        DepartmentDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DepartmentDTO.class);
            criteria.add(Restrictions.eq("departmentCode", departmentCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (DepartmentDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByDepartmentCode Department " + e.getMessage());
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
            Criteria criteria = session.createCriteria(DepartmentDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Department list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(DepartmentDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DepartmentDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getDepartmentCode() != null && dto.getDepartmentCode().length() > 0) {
                    criteria.add(Restrictions.like("departmentCode", dto.getDepartmentCode() + "%"));
                }

                if (dto.getDepartmentName() != null && dto.getDepartmentName().length() > 0) {
                    criteria.add(Restrictions.like("departmentName", dto.getDepartmentName() + "%"));
                }

                if (dto.getDepartmentHead() != null && dto.getDepartmentHead().length() > 0) {
                    criteria.add(Restrictions.like("departmentHead", dto.getDepartmentHead() + "%"));
                }
                
                if (dto.getLocation() != null && dto.getLocation().length() > 0) {
                    criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Department search");
        } finally {
            session.close();
        }

        return list;
    }
}