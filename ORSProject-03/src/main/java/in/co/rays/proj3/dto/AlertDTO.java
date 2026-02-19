package in.co.rays.proj3.dto;

import java.util.Date;

public class AlertDTO extends BaseDTO {
    private String alertCode;
    private String alertType;
    private Date alertTime;
    private String alertStatus;
   
    public String getAlertCode() {
        return alertCode;
    }
    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }
    public String getAlertType() {
        return alertType;
    }
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
    public Date getAlertTime() {
        return alertTime;
    }
    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }
    public String getAlertStatus() {
        return alertStatus;
    }
    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return alertCode;
    }
    
    @Override
    public String toString() {
        return "AlertDTO [alertCode=" + alertCode + ", alertType=" + alertType
                + ", alertTime=" + alertTime + ", alertStatus=" + alertStatus + "]";
    }
}