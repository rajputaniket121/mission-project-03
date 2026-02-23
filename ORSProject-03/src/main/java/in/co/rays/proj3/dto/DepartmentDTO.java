package in.co.rays.proj3.dto;

public class DepartmentDTO extends BaseDTO {
    private String departmentCode;
    private String departmentName;
    private String departmentHead;
    private String location;
    
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getDepartmentHead() {
        return departmentHead;
    }
    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return departmentName;
    }
    
    @Override
    public String toString() {
        return "DepartmentDTO [departmentCode=" + departmentCode + ", departmentName=" + departmentName
                + ", departmentHead=" + departmentHead + ", location=" + location + "]";
    }
}