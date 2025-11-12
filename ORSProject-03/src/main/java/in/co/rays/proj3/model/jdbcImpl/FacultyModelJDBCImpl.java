package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of Faculty model
 * @author Aniket Rajput
 *
 */
public class FacultyModelJDBCImpl implements FacultyModelInt {

    private static Logger log = Logger.getLogger(FacultyModelJDBCImpl.class);

    @Override
    public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        
        FacultyDTO exist = findByEmailId(dto.getEmailId());
        if(exist != null) {
            log.error("Email Id already exists");
            throw new DuplicateRecordException("Email Id already Exist");
        }
        
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getFirstName());
            pstmt.setString(3, dto.getLastName());
            pstmt.setDate(4, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(5, dto.getGender());
            pstmt.setString(6, dto.getMobileNo());
            pstmt.setString(7, dto.getEmailId());
            pstmt.setLong(8, dto.getCollegeId());
            pstmt.setString(9, dto.getCollegeName());
            pstmt.setLong(10, dto.getCourseId());
            pstmt.setString(11, dto.getCourseName());
            pstmt.setLong(12, dto.getSubjectId());
            pstmt.setString(13, dto.getSubjectName());
            pstmt.setString(14, dto.getCreatedBy());
            pstmt.setString(15, dto.getModifiedBy());
            pstmt.setTimestamp(16, dto.getCreatedDateTime());
            pstmt.setTimestamp(17, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Faculty Added " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in add Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(FacultyDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Faculty deleted " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Delete Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(FacultyDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        FacultyDTO exist = findByEmailId(dto.getEmailId());
        if(exist != null && exist.getId() != dto.getId()) {
            log.error("Email Id already exists");
            throw new RuntimeException("Email Id already Exist");
        }
        
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_faculty set first_name = ?, last_name = ? , dob = ? , gender = ? , mobile_no = ?, email = ?, college_id = ? , college_name = ? , course_id = ?, course_name = ? , subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getFirstName());
            pstmt.setString(2, dto.getLastName());
            pstmt.setDate(3, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(4, dto.getGender());
            pstmt.setString(5, dto.getMobileNo());
            pstmt.setString(6, dto.getEmailId());
            pstmt.setLong(7, dto.getCollegeId());
            pstmt.setString(8, dto.getCollegeName());
            pstmt.setLong(9, dto.getCourseId());
            pstmt.setString(10, dto.getCourseName());
            pstmt.setLong(11, dto.getSubjectId());
            pstmt.setString(12, dto.getSubjectName());
            pstmt.setString(13, dto.getCreatedBy());
            pstmt.setString(14, dto.getModifiedBy());
            pstmt.setTimestamp(15, dto.getCreatedDateTime());
            pstmt.setTimestamp(16, dto.getModifiedDateTime());
            pstmt.setLong(17, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Faculty updated " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Update Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<FacultyDTO> list() throws ApplicationException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<FacultyDTO> list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<FacultyDTO> search(FacultyDTO dto) throws ApplicationException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<FacultyDTO> search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_faculty where 1 = 1");
        List<FacultyDTO> facultyList = new ArrayList<FacultyDTO>();

        if (dto != null) {
            if (dto.getId()!=null && dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
                sql.append(" and first_name like '" + dto.getFirstName() + "%'");
            }
            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
                sql.append(" and last_name like '" + dto.getLastName() + "%'");
            }
            if (dto.getDob() != null) {
                sql.append(" and dob like '" + new java.sql.Date(dto.getDob().getTime()) + "%'");
            }
            if (dto.getGender() != null && dto.getGender().length() > 0) {
                sql.append(" and gender like '" + dto.getGender() + "%'");
            }
            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
                sql.append(" and mobile_no like '" + dto.getMobileNo() + "%'");
            }
            if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
                sql.append(" and email like '" + dto.getEmailId() + "%'");
            }
            if (dto.getCollegeId() > 0) {
                sql.append(" and college_id = " + dto.getCollegeId());
            }
            if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
                sql.append(" and college_name like '" + dto.getCollegeName() + "%'");
            }
            if (dto.getCourseId() > 0) {
                sql.append(" and course_id = " + dto.getCourseId());
            }
            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + dto.getCourseName() + "%'");
            }
            if (dto.getSubjectId() > 0) {
                sql.append(" and subject_id = " + dto.getSubjectId());
            }
            if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
                sql.append(" and subject_name like '" + dto.getSubjectName() + "%'");
            }
        }
        
        if(pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dto = new FacultyDTO();
                dto.setId(rs.getLong(1));
                dto.setFirstName(rs.getString(2));
                dto.setLastName(rs.getString(3));
                dto.setDob(rs.getDate(4));
                dto.setGender(rs.getString(5));
                dto.setMobileNo(rs.getString(6));
                dto.setEmailId(rs.getString(7));
                dto.setCollegeId(rs.getLong(8));
                dto.setCollegeName(rs.getString(9));
                dto.setCourseId(rs.getLong(10));
                dto.setCourseName(rs.getString(11));
                dto.setSubjectId(rs.getLong(12));
                dto.setSubjectName(rs.getString(13));
                dto.setCreatedBy(rs.getString(14));
                dto.setModifiedBy(rs.getString(15));
                dto.setCreatedDateTime(rs.getTimestamp(16));
                dto.setModifiedDateTime(rs.getTimestamp(17));
                facultyList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return facultyList;
    }

    @Override
    public FacultyDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        Connection conn = null;
        FacultyDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new FacultyDTO();
                dto.setId(rs.getLong(1));
                dto.setFirstName(rs.getString(2));
                dto.setLastName(rs.getString(3));
                dto.setDob(rs.getDate(4));
                dto.setGender(rs.getString(5));
                dto.setMobileNo(rs.getString(6));
                dto.setEmailId(rs.getString(7));
                dto.setCollegeId(rs.getLong(8));
                dto.setCollegeName(rs.getString(9));
                dto.setCourseId(rs.getLong(10));
                dto.setCourseName(rs.getString(11));
                dto.setSubjectId(rs.getLong(12));
                dto.setSubjectName(rs.getString(13));
                dto.setCreatedBy(rs.getString(14));
                dto.setModifiedBy(rs.getString(15));
                dto.setCreatedDateTime(rs.getTimestamp(16));
                dto.setModifiedDateTime(rs.getTimestamp(17));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByPk Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    @Override
    public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
        log.debug("Model findByEmailId Started");
        Connection conn = null;
        FacultyDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_faculty where email = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, emailId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new FacultyDTO();
                dto.setId(rs.getLong(1));
                dto.setFirstName(rs.getString(2));
                dto.setLastName(rs.getString(3));
                dto.setDob(rs.getDate(4));
                dto.setGender(rs.getString(5));
                dto.setMobileNo(rs.getString(6));
                dto.setEmailId(rs.getString(7));
                dto.setCollegeId(rs.getLong(8));
                dto.setCollegeName(rs.getString(9));
                dto.setCourseId(rs.getLong(10));
                dto.setCourseName(rs.getString(11));
                dto.setSubjectId(rs.getLong(12));
                dto.setSubjectName(rs.getString(13));
                dto.setCreatedBy(rs.getString(14));
                dto.setModifiedBy(rs.getString(15));
                dto.setCreatedDateTime(rs.getTimestamp(16));
                dto.setModifiedDateTime(rs.getTimestamp(17));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByEmailId Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByEmailId End");
        return dto;
    }

    private long getNextPk() throws DatabaseException {
        log.debug("Model getNextPk Started");
        Connection conn = null;
        long pk = 0L;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getLong(1) + 1L;
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception in Faculty getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model getNextPk End");
        return pk;
    }
}