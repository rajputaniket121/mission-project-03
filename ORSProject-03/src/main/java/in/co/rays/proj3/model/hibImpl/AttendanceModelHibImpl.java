package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.AttendanceDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.AttendanceModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class AttendanceModelHibImpl implements AttendanceModelInt {

    @Override
    public long add(AttendanceDTO dto) throws DatabaseException, DuplicateRecordException {

        AttendanceDTO existDto = findByPersonName(dto.getPersonName());
        if (existDto != null) {
            throw new DuplicateRecordException("Person Name already exists");
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
            throw new DatabaseException("Exception in Attendance add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AttendanceDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Attendance delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(AttendanceDTO dto) throws DatabaseException, DuplicateRecordException {

        AttendanceDTO existDto = findByPersonName(dto.getPersonName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Person Name already exists");
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
            throw new DatabaseException("Exception in Attendance update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public AttendanceDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        AttendanceDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AttendanceDTO) session.get(AttendanceDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Attendance by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AttendanceDTO findByPersonName(String personName) throws DatabaseException {

        Session session = null;
        AttendanceDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AttendanceDTO.class);
            criteria.add(Restrictions.eq("personName", personName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (AttendanceDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByPersonName Attendance " + e.getMessage());
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
            Criteria criteria = session.createCriteria(AttendanceDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Attendance list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AttendanceDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AttendanceDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getPersonName() != null && dto.getPersonName().length() > 0) {
                    criteria.add(Restrictions.like("personName", dto.getPersonName() + "%"));
                }

                if (dto.getAttendanceDate() != null) {
                    criteria.add(Restrictions.eq("attendanceDate", dto.getAttendanceDate()));
                }

                if (dto.getAttendanceStatus() != null && dto.getAttendanceStatus().length() > 0) {
                    criteria.add(Restrictions.like("attendanceStatus", dto.getAttendanceStatus() + "%"));
                }
                
                if (dto.getRemarks() != null && dto.getRemarks().length() > 0) {
                    criteria.add(Restrictions.like("remarks", dto.getRemarks() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Attendance search");
        } finally {
            session.close();
        }

        return list;
    }
}