package in.co.rays.proj3.dto;

import java.util.Date;

public class ShiftDTO extends BaseDTO {
    private String shiftCode;
    private String shiftName;
    private Date startTime;
    private Date endTime;
    private String shiftStatus;
    
    public String getShiftCode() {
        return shiftCode;
    }
    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }
    public String getShiftName() {
        return shiftName;
    }
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getShiftStatus() {
        return shiftStatus;
    }
    public void setShiftStatus(String shiftStatus) {
        this.shiftStatus = shiftStatus;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return shiftName;
    }
    
    @Override
    public String toString() {
        return "ShiftDTO [shiftCode=" + shiftCode + ", shiftName=" + shiftName + ", startTime=" + startTime
                + ", endTime=" + endTime + ", shiftStatus=" + shiftStatus + "]";
    }
}