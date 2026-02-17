package in.co.rays.proj3.dto;

public class ContactDTO extends BaseDTO {
    private String name;
    private String email;
    private String mobileNo;
    private String message;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return name;
    }
    
    @Override
    public String toString() {
        return "ContactDTO [name=" + name + ", email=" + email + ", mobileNo=" + mobileNo + ", message=" + message + "]";
    }
}