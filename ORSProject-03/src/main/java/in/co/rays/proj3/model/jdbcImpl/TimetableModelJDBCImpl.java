package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.TimetableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.TimetableModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of TimeTable model
 * @author	Aniket rajput
 *
 */
public class TimetableModelJDBCImpl implements TimetableModelInt {

    private static Logger log = Logger.getLogger(TimetableModelJDBCImpl.class);

    @Override
    public long add(TimetableDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getSemester());
            pstmt.setString(3, dto.getDescription());
            pstmt.setDate(4, new java.sql.Date(dto.getExamDate().getTime()));
            pstmt.setString(5, dto.getExamTime());
            pstmt.setLong(6, dto.getCourseId());
            pstmt.setString(7, dto.getCourseName());
            pstmt.setLong(8, dto.getSubjectId());
            pstmt.setString(9, dto.getSubjectName());
            pstmt.setString(10, dto.getCreatedBy());
            pstmt.setString(11, dto.getModifiedBy());
            pstmt.setTimestamp(12, dto.getCreatedDateTime());
            pstmt.setTimestamp(13, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Timetable Added " + i);
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
            throw new ApplicationException("Exception : Exception in add Timetable");    
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(TimetableDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_timetable where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Timetable deleted " + i);
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
            throw new ApplicationException("Exception : Exception in Delete Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(TimetableDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_timetable set semester = ?, description = ? , exam_date = ?, exam_time = ?, course_id = ?, course_name = ? , subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getSemester());
            pstmt.setString(2, dto.getDescription());
            pstmt.setDate(3, new java.sql.Date(dto.getExamDate().getTime()));
            pstmt.setString(4, dto.getExamTime());
            pstmt.setLong(5, dto.getCourseId());
            pstmt.setString(6, dto.getCourseName());
            pstmt.setLong(7, dto.getSubjectId());
            pstmt.setString(8, dto.getSubjectName());
            pstmt.setString(9, dto.getCreatedBy());
            pstmt.setString(10, dto.getModifiedBy());
            pstmt.setTimestamp(11, dto.getCreatedDateTime());
            pstmt.setTimestamp(12, dto.getModifiedDateTime());
            pstmt.setLong(13, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Timetable updated " + i);
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
            throw new ApplicationException("Exception : Exception in Update TimeTable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<TimetableDTO> list() throws ApplicationException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<TimetableDTO> list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<TimetableDTO> search(TimetableDTO dto) throws ApplicationException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<TimetableDTO> search(TimetableDTO dto, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_timetable where 1 = 1");
        List<TimetableDTO> timetableList = new ArrayList<TimetableDTO>();

        if (dto != null) {
            if (dto.getId()!=null && dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getSemester() != null && dto.getSemester().length() > 0) {
                sql.append(" and semester like '" + dto.getSemester() + "%'");
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                sql.append(" and description like '" + dto.getDescription() + "%'");
            }
            if (dto.getExamDate() != null) {
                sql.append(" and exam_date like '" + new java.sql.Date(dto.getExamDate().getTime()) + "%'");
            }
            if (dto.getExamTime() != null && dto.getExamTime().length() > 0) {
                sql.append(" and exam_time like '" + dto.getExamTime() + "%'");
            }
            if (dto.getCourseId() != null && dto.getCourseId() > 0) {
                sql.append(" and course_id = " + dto.getCourseId());
            }
            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + dto.getCourseName() + "%'");
            }
            if (dto.getSubjectId() != null && dto.getSubjectId() > 0) {
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
                dto = new TimetableDTO();
                dto.setId(rs.getLong(1));
                dto.setSemester(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setExamDate(rs.getDate(4));
                dto.setExamTime(rs.getString(5));
                dto.setCourseId(rs.getLong(6));
                dto.setCourseName(rs.getString(7));
                dto.setSubjectId(rs.getLong(8));
                dto.setSubjectName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
                timetableList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return timetableList;
    }

    @Override
    public TimetableDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        Connection conn = null;
        TimetableDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_timetable where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new TimetableDTO();
                dto.setId(rs.getLong(1));
                dto.setSemester(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setExamDate(rs.getDate(4));
                dto.setExamTime(rs.getString(5));
                dto.setCourseId(rs.getLong(6));
                dto.setCourseName(rs.getString(7));
                dto.setSubjectId(rs.getLong(8));
                dto.setSubjectName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByPK Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    @Override
    public TimetableDTO checkByCourseName(long courseId, Date examDate) throws ApplicationException, DuplicateRecordException {
        log.debug("Model checkByCourseName Started");
        StringBuffer sql = new StringBuffer("select * from st_timetable where course_id = ? and exam_date = ?");
        TimetableDTO dto = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setDate(2, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new TimetableDTO();
                dto.setId(rs.getLong(1));
                dto.setSemester(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setExamDate(rs.getDate(4));
                dto.setExamTime(rs.getString(5));
                dto.setCourseId(rs.getLong(6));
                dto.setCourseName(rs.getString(7));
                dto.setSubjectId(rs.getLong(8));
                dto.setSubjectName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in get Timetable");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model checkByCourseName End");
        return dto;
    }

    @Override
    public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate) throws ApplicationException, DuplicateRecordException {
        log.debug("Model checkBySubjectName Started");
        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and exam_date = ?");
        TimetableDTO dto = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setDate(3, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new TimetableDTO();
                dto.setId(rs.getLong(1));
                dto.setSemester(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setExamDate(rs.getDate(4));
                dto.setExamTime(rs.getString(5));
                dto.setCourseId(rs.getLong(6));
                dto.setCourseName(rs.getString(7));
                dto.setSubjectId(rs.getLong(8));
                dto.setSubjectName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in get Timetable");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model checkBySubjectName End");
        return dto;
    }

    @Override
    public TimetableDTO checkBySemester(long courseId, long subjectId, String semester, Date examDate) throws ApplicationException, DuplicateRecordException {
        log.debug("Model checkBysemester Started");
        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ?");
        TimetableDTO dto = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setString(3, semester);
            pstmt.setDate(4, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new TimetableDTO();
                dto.setId(rs.getLong(1));
                dto.setSemester(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setExamDate(rs.getDate(4));
                dto.setExamTime(rs.getString(5));
                dto.setCourseId(rs.getLong(6));
                dto.setCourseName(rs.getString(7));
                dto.setSubjectId(rs.getLong(8));
                dto.setSubjectName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model checkBysemester End");
        return dto;
    }

    private long getNextPk() throws DatabaseException {
        log.debug("Model getNextPk Started");
        Connection conn = null;
        long pk = 0L;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_timetable");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getLong(1) + 1L;
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception in Marksheet getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model getNextPk End");
        return pk;
    }
}