package in.co.rays.proj3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

import in.co.rays.proj3.model.hibImpl.AlertModelHibImpl;
import in.co.rays.proj3.model.hibImpl.AttendanceModelHibImpl;
import in.co.rays.proj3.model.hibImpl.CollegeModelHibImpl;
import in.co.rays.proj3.model.hibImpl.ContactModelHibImpl;
import in.co.rays.proj3.model.hibImpl.CourseModelHibImpl;
import in.co.rays.proj3.model.hibImpl.DoctorModelHibImpl;
import in.co.rays.proj3.model.hibImpl.FacultyModelHibImpl;
import in.co.rays.proj3.model.hibImpl.MarksheetModelHibImpl;
import in.co.rays.proj3.model.hibImpl.ProfileModelHibImpl;
import in.co.rays.proj3.model.hibImpl.RoleModelHibImpl;
import in.co.rays.proj3.model.hibImpl.StudentModelHibImpl;
import in.co.rays.proj3.model.hibImpl.SubjectModelHibImpl;
import in.co.rays.proj3.model.hibImpl.SupportModelHibImpl;
import in.co.rays.proj3.model.hibImpl.TimetableModelHibImpl;
import in.co.rays.proj3.model.hibImpl.UserModelHibImpl;
import in.co.rays.proj3.model.jdbcImpl.CollegeModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.CourseModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.DoctorModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.FacultyModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.MarksheetModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.RoleModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.StudentModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.SubjectModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.TimetableModelJDBCImpl;
import in.co.rays.proj3.model.jdbcImpl.UserModelJDBCImpl;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Aniket Rajput
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImpl();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImpl();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}
	
	public DoctorModelInt getDoctorModel() {
		DoctorModelInt doctorModel = (DoctorModelInt) modelCache.get("doctorModel");
		if (doctorModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				doctorModel = new DoctorModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				doctorModel = new DoctorModelJDBCImpl();
			}
			modelCache.put("doctorModel", doctorModel);
		}

		return doctorModel;
	}
	public ProfileModelInt getProfileModel() {
		ProfileModelInt profileModel = (ProfileModelInt) modelCache.get("profileModel");
		if (profileModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				profileModel = new ProfileModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				profileModel = new ProfileModelHibImpl();
			}
			modelCache.put("profileModel", profileModel);
		}

		return profileModel;
	}

	public ContactModelInt getContactModel() {
		ContactModelInt contactModel = (ContactModelInt) modelCache.get("contactModel");
		if (contactModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				contactModel = new ContactModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				contactModel = new ContactModelHibImpl();
			}
			modelCache.put("contactModel", contactModel);
		}

		return contactModel;
	}
	
	public SupportModelInt getSupportModel() {
		
		SupportModelInt supportModel = (SupportModelInt) modelCache.get("supportModel");
		if (supportModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				supportModel = new SupportModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				supportModel = new SupportModelHibImpl();
			}
			modelCache.put("supportModel", supportModel);
		}

		return supportModel;
	}
	
	public AttendanceModelInt getAttendanceModel() {
		AttendanceModelInt attendanceModel = (AttendanceModelInt) modelCache.get("attendanceModel");
		if (attendanceModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				attendanceModel = new AttendanceModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				attendanceModel = new AttendanceModelHibImpl();
			}
			modelCache.put("attendanceModel", attendanceModel);
		}

		return attendanceModel;
	}
	
	public AlertModelInt getAlertModel() {
		AlertModelInt alertModel = (AlertModelInt) modelCache.get("alertModel");
		if (alertModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				alertModel = new AlertModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				alertModel = new AlertModelHibImpl();
			}
			modelCache.put("alertModel", alertModel);
		}

		return alertModel;
	}
}
