package in.co.rays.proj3.controller;

public interface ORSView {
	public String APP_CONTEXT = "/ORSProject-03";

	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	


	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	public String JASPER_CTL = APP_CONTEXT +  "/ctl/JasperCtl";
	
	public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";
	public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";
	public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";
	public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";
	
	public String PROFILE_VIEW = PAGE_FOLDER + "/ProfileView.jsp";
	public String PROFILE_LIST_VIEW = PAGE_FOLDER + "/ProfileListView.jsp";
	public String PROFILE_CTL = APP_CONTEXT + "/ctl/ProfileCtl";
	public String PROFILE_LIST_CTL = APP_CONTEXT + "/ctl/ProfileListCtl";
	
	public static final String CONTACT_VIEW =PAGE_FOLDER+ "/ContactView.jsp";
	public static final String CONTACT_LIST_VIEW = PAGE_FOLDER + "/ContactListView.jsp";
	public static final String CONTACT_CTL = APP_CONTEXT + "/ctl/ContactCtl";
	public static final String CONTACT_LIST_CTL = APP_CONTEXT + "/ctl/ContactListCtl";
	
	public static final String SUPPORT_VIEW = PAGE_FOLDER + "/SupportView.jsp";
	public static final String SUPPORT_LIST_VIEW = PAGE_FOLDER + "/SupportListView.jsp";
	public static final String SUPPORT_CTL = APP_CONTEXT + "/ctl/SupportCtl";
	public static final String SUPPORT_LIST_CTL = APP_CONTEXT + "/ctl/SupportListCtl";
	
	public static final String ATTENDANCE_VIEW = PAGE_FOLDER + "/AttendanceView.jsp";
	public static final String ATTENDANCE_LIST_VIEW = PAGE_FOLDER + "/AttendanceListView.jsp";
	public static final String ATTENDANCE_CTL = APP_CONTEXT + "/ctl/AttendanceCtl";
	public static final String ATTENDANCE_LIST_CTL = APP_CONTEXT + "/ctl/AttendanceListCtl";
	
	public static final String ALERT_VIEW = PAGE_FOLDER + "/AlertView.jsp";
	public static final String ALERT_LIST_VIEW = PAGE_FOLDER + "/AlertListView.jsp";
	public static final String ALERT_CTL = APP_CONTEXT + "/ctl/AlertCtl";
	public static final String ALERT_LIST_CTL = APP_CONTEXT + "/ctl/AlertListCtl";
	
	public static final String FEEDBACK_VIEW = PAGE_FOLDER + "/FeedbackView.jsp";
	public static final String FEEDBACK_LIST_VIEW = PAGE_FOLDER + "/FeedbackListView.jsp";
	public static final String FEEDBACK_CTL = APP_CONTEXT + "/ctl/FeedbackCtl";
	public static final String FEEDBACK_LIST_CTL = APP_CONTEXT + "/ctl/FeedbackListCtl";
	
	public static final String SHIFT_VIEW = PAGE_FOLDER + "/ShiftView.jsp";
	public static final String SHIFT_LIST_VIEW = PAGE_FOLDER + "/ShiftListView.jsp";
	public static final String SHIFT_CTL = APP_CONTEXT + "/ctl/ShiftCtl";
	public static final String SHIFT_LIST_CTL = APP_CONTEXT + "/ctl/ShiftListCtl";
	
	public static final String DEPARTMENT_VIEW = PAGE_FOLDER + "/DepartmentView.jsp";
	public static final String DEPARTMENT_LIST_VIEW = PAGE_FOLDER + "/DepartmentListView.jsp";
	public static final String DEPARTMENT_CTL = APP_CONTEXT + "/ctl/DepartmentCtl";
	public static final String DEPARTMENT_LIST_CTL = APP_CONTEXT + "/ctl/DepartmentListCtl";
	
	public static final String ANNOUNCEMENT_VIEW = PAGE_FOLDER + "/AnnouncementView.jsp";
	public static final String ANNOUNCEMENT_LIST_VIEW = PAGE_FOLDER + "/AnnouncementListView.jsp";
	public static final String ANNOUNCEMENT_CTL = APP_CONTEXT + "/ctl/AnnouncementCtl";
	public static final String ANNOUNCEMENT_LIST_CTL = APP_CONTEXT + "/ctl/AnnouncementListCtl";

}
