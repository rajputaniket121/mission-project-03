package in.co.rays.proj3.dto;

import java.util.Date;

public class ProfileDTO extends BaseDTO{
	private String fullName;
	private String gender;
	private Date dob;
	private String profileStatus;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getProfileStatus() {
		return profileStatus;
	}
	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}
	@Override
	public String getKey() {
		return id+"";
	}
	@Override
	public String getValue() {
		return profileStatus;
	}
	
	@Override
	public String toString() {
		return "ProfileDTO [fullName=" + fullName + ", gender=" + gender + ", dob=" + dob + ", profileStatus="
				+ profileStatus + "]";
	}

}
