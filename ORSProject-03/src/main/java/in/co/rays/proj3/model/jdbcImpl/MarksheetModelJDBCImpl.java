package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.MarksheetModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of Marksheet model
 * @author Aniket Rajput
 *
 */
public class MarksheetModelJDBCImpl implements MarksheetModelInt {

    private static Logger log = Logger.getLogger(MarksheetModelJDBCImpl.class);

    @Override
    public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        MarksheetDTO exist = findByRollNo(dto.getRollNo());
        if(exist != null) {
            log.error("Marksheet already exists");
            throw new DuplicateRecordException("Marksheet already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getRollNo());
            pstmt.setLong(3, dto.getStudentId());
            pstmt.setString(4, dto.getName());
            pstmt.setInt(5, dto.getPhysics());
            pstmt.setInt(6, dto.getChemistry());
            pstmt.setInt(7, dto.getMaths());
            pstmt.setString(8, dto.getCreatedBy());
            pstmt.setString(9, dto.getModifiedBy());
            pstmt.setTimestamp(10, dto.getCreatedDateTime());
            pstmt.setTimestamp(11, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Marksheet Added " + i);
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
            throw new ApplicationException("Exception : Exception in add Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(MarksheetDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Marksheet deleted " + i);
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
            throw new ApplicationException("Exception : Exception in Delete Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        MarksheetDTO exist = findByRollNo(dto.getRollNo());
        if(exist != null && exist.getId() != dto.getId()) {
            log.error("Marksheet already exists");
            throw new RuntimeException("Marksheet already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_marksheet set roll_no = ?, student_id = ? , name = ?, physics = ?, chemistry = ?, maths = ? , created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getRollNo());
            pstmt.setLong(2, dto.getStudentId());
            pstmt.setString(3, dto.getName());
            pstmt.setInt(4, dto.getPhysics());
            pstmt.setInt(5, dto.getChemistry());
            pstmt.setInt(6, dto.getMaths());
            pstmt.setString(7, dto.getCreatedBy());
            pstmt.setString(8, dto.getModifiedBy());
            pstmt.setTimestamp(9, dto.getCreatedDateTime());
            pstmt.setTimestamp(10, dto.getModifiedDateTime());
            pstmt.setLong(11, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Marksheet updated " + i);
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
            throw new ApplicationException("Exception : Exception in Update Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<MarksheetDTO> list() throws ApplicationException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<MarksheetDTO> list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<MarksheetDTO> search(MarksheetDTO dto) throws ApplicationException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<MarksheetDTO> search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_marksheet where 1 = 1");
        List<MarksheetDTO> marksheetList = new ArrayList<MarksheetDTO>();

        if (dto != null) {
            if (dto.getId()!= null && dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
                sql.append(" and roll_no like '" + dto.getRollNo() + "%'");
            }
            if (dto.getStudentId() > 0) {
                sql.append(" and student_id = " + dto.getStudentId());
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                sql.append(" and name like '" + dto.getName() + "%'");
            }
            if (dto.getPhysics() != null && dto.getPhysics() > 0) {
                sql.append(" and physics = " + dto.getPhysics());
            }
            if (dto.getChemistry() != null && dto.getChemistry() > 0) {
                sql.append(" and chemistry = " + dto.getChemistry());
            }
            if (dto.getMaths() != null && dto.getMaths() > 0) {
                sql.append(" and maths = " + dto.getMaths());
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
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setRollNo(rs.getString(2));
                dto.setStudentId(rs.getLong(3));
                dto.setName(rs.getString(4));
                dto.setPhysics(rs.getInt(5));
                dto.setChemistry(rs.getInt(6));
                dto.setMaths(rs.getInt(7));
                dto.setCreatedBy(rs.getString(8));
                dto.setModifiedBy(rs.getString(9));
                dto.setCreatedDateTime(rs.getTimestamp(10));
                dto.setModifiedDateTime(rs.getTimestamp(11));
                marksheetList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return marksheetList;
    }

    @Override
    public MarksheetDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model fingByPK Started");
        Connection conn = null;
        MarksheetDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setRollNo(rs.getString(2));
                dto.setStudentId(rs.getLong(3));
                dto.setName(rs.getString(4));
                dto.setPhysics(rs.getInt(5));
                dto.setChemistry(rs.getInt(6));
                dto.setMaths(rs.getInt(7));
                dto.setCreatedBy(rs.getString(8));
                dto.setModifiedBy(rs.getString(9));
                dto.setCreatedDateTime(rs.getTimestamp(10));
                dto.setModifiedDateTime(rs.getTimestamp(11));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in fingByPK Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model fingByPK End");
        return dto;
    }

    @Override
    public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
        log.debug("Model findByRollNo Started");
        Connection conn = null;
        MarksheetDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setRollNo(rs.getString(2));
                dto.setStudentId(rs.getLong(3));
                dto.setName(rs.getString(4));
                dto.setPhysics(rs.getInt(5));
                dto.setChemistry(rs.getInt(6));
                dto.setMaths(rs.getInt(7));
                dto.setCreatedBy(rs.getString(8));
                dto.setModifiedBy(rs.getString(9));
                dto.setCreatedDateTime(rs.getTimestamp(10));
                dto.setModifiedDateTime(rs.getTimestamp(11));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in findByRollNo Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByRollNo End");
        return dto;
    }

    @Override
    public List<MarksheetDTO> getMeritList(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model getMeritList Started");
        ArrayList<MarksheetDTO> list = new ArrayList<MarksheetDTO>();
        StringBuffer sql = new StringBuffer(
                "select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_marksheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MarksheetDTO dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setRollNo(rs.getString(2));
                dto.setName(rs.getString(3));
                dto.setPhysics(rs.getInt(4));
                dto.setChemistry(rs.getInt(5));
                dto.setMaths(rs.getInt(6));
                list.add(dto);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception in getting merit list of Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model getMeritList End");
        return list;
    }

    private long getNextPk() throws DatabaseException {
        log.debug("Model getNextPk Started");
        Connection conn = null;
        long pk = 0L;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getLong(1);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception in Marksheet getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1L;
    }
    
    
    public HashMap<String, Object> findMarksheetReportByRollNo(String rollNo) throws ApplicationException {
        log.debug("Model findMarksheetReportByRollNo Started");
        Connection conn = null;
        HashMap<String, Object> map = null;

        String sql = "SELECT m.roll_no AS roll_no, m.name AS student_name, s.first_name, s.last_name, " +
                     "CONCAT(s.first_name, ' ', s.last_name) AS full_name, s.gender, s.college_name AS college_name, " +
                     "s.email AS student_email, s.mobile_no AS student_mobile, f.course_name AS course_name, " +
                     "f.subject_name AS subject_name, f.college_name AS faculty_college, " +
                     "CONCAT(f.first_name, ' ', f.last_name) AS faculty_name, m.physics AS physics_marks, " +
                     "m.chemistry AS chemistry_marks, m.maths AS maths_marks, " +
                     "(m.physics + m.chemistry + m.maths) AS total_marks, " +
                     "ROUND((m.physics + m.chemistry + m.maths)/3, 2) AS percentage, " +
                     "CASE WHEN (m.physics + m.chemistry + m.maths)/3 >= 60 THEN 'First Division' " +
                     "WHEN (m.physics + m.chemistry + m.maths)/3 >= 45 THEN 'Second Division' " +
                     "WHEN (m.physics + m.chemistry + m.maths)/3 >= 33 THEN 'Third Division' ELSE 'Fail' END AS division, " +
                     "CASE WHEN (m.physics + m.chemistry + m.maths)/3 >= 33 THEN 'Pass' ELSE 'Fail' END AS result " +
                     "FROM st_marksheet m " +
                     "LEFT JOIN st_student s ON s.id = m.student_id " +
                     "LEFT JOIN st_faculty f ON f.college_id = s.college_id " +
                     "WHERE m.roll_no = ?";

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                map = new HashMap<>();
                map.put("rollNo", rs.getString("roll_no"));
                map.put("studentName", rs.getString("student_name"));
                map.put("firstName", rs.getString("first_name"));
                map.put("lastName", rs.getString("last_name"));
                map.put("fullName", rs.getString("full_name"));
                map.put("gender", rs.getString("gender"));
                map.put("collegeName", rs.getString("college_name"));
                map.put("email", rs.getString("student_email"));
                map.put("mobile", rs.getString("student_mobile"));
                map.put("courseName", rs.getString("course_name"));
                map.put("subjectName", rs.getString("subject_name"));
                map.put("facultyCollege", rs.getString("faculty_college"));
                map.put("facultyName", rs.getString("faculty_name"));
                map.put("physics", rs.getInt("physics_marks"));
                map.put("chemistry", rs.getInt("chemistry_marks"));
                map.put("maths", rs.getInt("maths_marks"));
                map.put("totalMarks", rs.getInt("total_marks"));
                map.put("percentage", rs.getFloat("percentage"));
                map.put("division", rs.getString("division"));
                map.put("result", rs.getString("result"));
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception in findMarksheetReportByRollNo");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model findMarksheetReportByRollNo End");
        return map;
    }

}