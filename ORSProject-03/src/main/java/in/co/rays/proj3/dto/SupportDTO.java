package in.co.rays.proj3.dto;

public class SupportDTO extends BaseDTO {
    private String userName;
    private String issueType;
    private String issueDescription;
    private String ticketStatus;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getIssueType() {
        return issueType;
    }
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    public String getIssueDescription() {
        return issueDescription;
    }
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
    public String getTicketStatus() {
        return ticketStatus;
    }
    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return userName;
    }
    
    @Override
    public String toString() {
        return "SupportDTO [userName=" + userName + ", issueType=" + issueType + ", issueDescription="
                + issueDescription + ", ticketStatus=" + ticketStatus + "]";
    }
}