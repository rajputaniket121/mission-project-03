package in.co.rays.proj3.dto;

import java.util.Date;

public class AttendanceDTO extends BaseDTO {
    private String personName;
    private Date attendanceDate;
    private String attendanceStatus;
    private String remarks;
    
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public Date getAttendanceDate() {
        return attendanceDate;
    }
    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return personName;
    }
    
    @Override
    public String toString() {
        return "AttendanceDTO [personName=" + personName + ", attendanceDate=" + attendanceDate + ", attendanceStatus="
                + attendanceStatus + ", remarks=" + remarks + "]";
    }
}