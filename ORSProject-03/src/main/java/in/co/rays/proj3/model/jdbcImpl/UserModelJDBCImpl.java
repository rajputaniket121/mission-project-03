package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.utill.EmailBuilder;
import in.co.rays.proj3.utill.EmailMessage;
import in.co.rays.proj3.utill.EmailUtility;
import in.co.rays.proj3.utill.JDBCDataSource;

public class UserModelJDBCImpl implements UserModelInt {

	@Override
	public long add(UserDTO dto) throws DatabaseException, DuplicateRecordException {
		Connection conn = null;
		Long pk = 0l;
		UserDTO exist = findByLogin(dto.getLogin());
		if(exist!=null) {
			throw new DuplicateRecordException("Login Id already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = getNextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getFirstName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getLogin());
			pstmt.setString(5, dto.getPassword());
			pstmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(7, dto.getMobileNo());
			pstmt.setLong(8, dto.getRoleId());
			pstmt.setString(9, dto.getGender());
			pstmt.setString(10, dto.getCreatedBy());
			pstmt.setString(11, dto.getModifiedBy());
			pstmt.setTimestamp(12, dto.getCreatedDateTime());
			pstmt.setTimestamp(13, dto.getModifiedDateTime());
			int i = pstmt.executeUpdate();
			System.out.println("New User Added " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new DatabaseException("Exception : Add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	@Override
	public void delete(UserDTO dto) throws DatabaseException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
			pstmt.setLong(1, dto.getId());
			int i = pstmt.executeUpdate();
			System.out.println("User deleted " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new DatabaseException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}

	@Override
	public void update(UserDTO dto) throws DatabaseException, DuplicateRecordException {
		Connection conn = null;
		UserDTO exist = findByLogin(dto.getLogin());
		if(exist!=null && exist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Login Id already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_user set first_name = ?, last_name = ? , login = ? , password = ? , dob = ?, mobile_no = ?, role_id = ?, gender = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, dto.getFirstName());
			pstmt.setString(2, dto.getLastName());
			pstmt.setString(3, dto.getLogin());
			pstmt.setString(4, dto.getPassword());
			pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(6, dto.getMobileNo());
			pstmt.setLong(7, dto.getRoleId());
			pstmt.setString(8, dto.getGender());
			pstmt.setString(9, dto.getCreatedBy());
			pstmt.setString(10, dto.getModifiedBy());
			pstmt.setTimestamp(11, dto.getCreatedDateTime());
			pstmt.setTimestamp(12, dto.getModifiedDateTime());
			pstmt.setLong(13, dto.getId());
			int i = pstmt.executeUpdate();
			System.out.println("User updated " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new DatabaseException("Exception : Update rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in Update User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}

	@Override
	public UserDTO findByPK(long pk) throws DatabaseException {
		Connection conn = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from st_user where id = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getInt(8));
				dto.setGender(rs.getString(9));;
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDateTime(rs.getTimestamp(12));
				dto.setModifiedDateTime(rs.getTimestamp(13));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in FindByPk User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public UserDTO findByLogin(String login) throws DatabaseException {
		Connection conn = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from st_user where login = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getInt(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDateTime(rs.getTimestamp(12));
				dto.setModifiedDateTime(rs.getTimestamp(13));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in FindByLogin User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public List list() throws DatabaseException {
		return search(null, 0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws DatabaseException {
		return search(null, pageNo, pageSize);
	}

	@Override
	public List search(UserDTO dto, int pageNo, int pageSize) throws DatabaseException {
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_user where 1 = 1");
		List<UserDTO> userList = new ArrayList<UserDTO>();
		if (dto != null) {
			if (dto.getId() != null && dto.getId() > 0) {
				sql.append(" and id = "+dto.getId());
			}
			if (dto.getFirstName()!= null && dto.getFirstName().length() > 0) {
				sql.append(" and first_name like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName()!= null && dto.getLastName().length() > 0) {
				sql.append(" and last_name like '" + dto.getLastName() + "%'");
			}
			if (dto.getLogin()!= null && dto.getLogin().length() > 0) {
				sql.append(" and login like '" + dto.getLogin() + "%'");
			}
			if (dto.getPassword()!= null && dto.getPassword().length() > 0) {
				sql.append(" and password like '" + dto.getPassword() + "%'");
			}
			if (dto.getDob()!= null && dto.getDob().getTime()>0) {
				sql.append(" and dob like '" + new java.sql.Date(dto.getDob().getTime()) + "%'");
			}
			if (dto.getMobileNo()!= null && dto.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '" + dto.getMobileNo() + "%'");
			}
			if (dto.getRoleId() > 0) {
				sql.append(" and role_id like '" + dto.getRoleId() + "%'");
			}
			if (dto.getGender()!= null && dto.getGender().length() > 0) {
				sql.append(" and gender like '" + dto.getGender() + "%'");
			}
		}
		
		if(pageSize>0) {
			pageNo = (pageNo-1) * pageSize;
			sql.append(" limit "+pageNo+", "+pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getInt(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDateTime(rs.getTimestamp(12));
				dto.setModifiedDateTime(rs.getTimestamp(13));
				userList.add(dto);
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return userList;
	}

	@Override
	public List search(UserDTO dto) throws DatabaseException {
		return search(dto, 0, 0);
	}

	@Override
	public boolean changePassword(long id, String newPassword, String oldPassword) throws ApplicationException, RecordNotFoundException {
	
	boolean flag = false;

	UserDTO beanExist = findByPK(id);

	if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
		beanExist.setPassword(newPassword);
		try {
			update(beanExist);
			flag = true;
		} catch (DuplicateRecordException e) {
			throw new DatabaseException("Issue in change password");
		}
	} else {
		throw new RecordNotFoundException("Old Password is Invalid");
	}

	HashMap<String, String> map = new HashMap<String, String>();
	map.put("login", beanExist.getLogin());
	map.put("password", beanExist.getPassword());
	map.put("firstName", beanExist.getFirstName());
	map.put("lastName", beanExist.getLastName());

	String message = EmailBuilder.getChangePasswordMessage(map);

	EmailMessage msg = new EmailMessage();
	msg.setTo(beanExist.getLogin());
	msg.setSubject("ORSProject-04 Password has been changed Successfully.");
	msg.setMessage(message);
	msg.setMessageType(EmailMessage.HTML_MSG);

	EmailUtility.sendMail(msg);

	return flag;
}

@Override
public boolean forgetPassword(String login) throws RecordNotFoundException, DatabaseException {
	UserDTO userData = findByLogin(login);
	boolean flag = false;

	if (userData == null) {
		throw new RecordNotFoundException("Email ID does not exists..!!");
	}

	try {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("ORSProject-03 Password Reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		flag = true;
	} catch (Exception e) {
		throw new DatabaseException("Please check your internet connection..!!");
	}
	return flag;
	}

	@Override
	public UserDTO authenticate(String login, String password) throws DatabaseException {
		Connection conn = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from st_user where login = ? and password = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getInt(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDateTime(rs.getTimestamp(12));
				dto.setModifiedDateTime(rs.getTimestamp(13));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in Authenticate User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}



	@Override
	public boolean resetPassword(UserDTO dto) throws ApplicationException, RecordNotFoundException {
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	@Override
	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORSProject-04");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	@Override
	public List getRoles(UserDTO dto) throws DatabaseException {
		return null;
	}
	
	public Long getNextPk() throws DatabaseException {
		Connection conn = null;
		Long pk = 0l;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_user");
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			throw new DatabaseException("Exception : Exception In Getting pk");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}

}
