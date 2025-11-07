package in.co.rays.proj3.dto;


import java.sql.Timestamp;
import java.util.Date;
/**
 * user JavaDto encapsulates user attributes
 * @author Aniket Rajput
 *
 */

public class UserDTO extends BaseDTO {
	private String firstName;
	 private String lastName;
	 private String login;
	 private String password;
	 private String confirmPassword;
	 private Date dob;
	 private String mobileNo;
	 private int roleId;
	 private String gender;
	 
	 
	 public String getFirstName() {
		return firstName;
	 }
	 public void setFirstName(String firstName) {
		this.firstName = firstName;
	 }
	 public String getLastName() {
		return lastName;
	 }
	 public void setLastName(String lastName) {
		this.lastName = lastName;
	 }
	 public String getLogin() {
		return login;
	 }
	 public void setLogin(String login) {
		this.login = login;
	 }
	 public String getPassword() {
		return password;
	 }
	 public void setPassword(String password) {
		this.password = password;
	 }
	 public String getConfirmPassword() {
		return confirmPassword;
	 }
	 public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	 }
	 public Date getDob() {
		return dob;
	 }
	 public void setDob(Date dob) {
		this.dob = dob;
	 }
	 public String getMobileNo() {
		return mobileNo;
	 }
	 public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	 }
	 public int getRoleId() {
		return roleId;
	 }
	 public void setRoleId(int roleId) {
		this.roleId = roleId;
	 }
	 public String getGender() {
		return gender;
	 }
	 public void setGender(String gender) {
		this.gender = gender;
	 }
	 
	 
	 @Override
	 public String toString() {
		return "UserBean [firstName=" + firstName + ", lastName=" + lastName + ", login=" + login + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", dob=" + dob + ", mobileNo=" + mobileNo + ", roleId=" + roleId
				+ ", gender=" + gender + "]";
	 }
	 @Override
	 public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	 }
	 @Override
	 public String getValue() {
		// TODO Auto-generated method stub
		return firstName + " " + lastName;
	 }

}
