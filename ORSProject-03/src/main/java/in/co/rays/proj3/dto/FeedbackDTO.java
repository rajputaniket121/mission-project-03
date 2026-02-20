package in.co.rays.proj3.dto;

import java.util.Date;

public class FeedbackDTO extends BaseDTO {
    private String feedbackCode;
    private String customerName;
    private String rating;
    private String comments;
    private Date feedbackDate;
    
    public String getFeedbackCode() {
        return feedbackCode;
    }
    public void setFeedbackCode(String feedbackCode) {
        this.feedbackCode = feedbackCode;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public Date getFeedbackDate() {
        return feedbackDate;
    }
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    
    @Override
    public String getKey() {
        return id + "";
    }
    
    @Override
    public String getValue() {
        return feedbackCode;
    }
    
    @Override
    public String toString() {
        return "FeedbackDTO [feedbackCode=" + feedbackCode + ", customerName=" + customerName + ", rating=" + rating
                + ", comments=" + comments + ", feedbackDate=" + feedbackDate + "]";
    }
}