package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "FacultyCtl", urlPatterns = {"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl{
	
	 @Override
	    protected void preload(HttpServletRequest request) {
		 	CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
	        CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
	        SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
	        try {
	            List<CourseDTO> courseList =  courseModel.list();
	            List<SubjectDTO> subjectList = subjectModel.list();
	            List<CollegeDTO> collegeList = collegeModel.list();
	            request.setAttribute("courseList", courseList);
	            request.setAttribute("subjectList", subjectList);
	            request.setAttribute("collegeList", collegeList);
	        } catch (ApplicationException e) {
	           e.printStackTrace();
	        }
	    }

	    @Override
	    protected boolean validate(HttpServletRequest request) {
	        boolean pass = true;
	        String op = request.getParameter("operation");

	        if(OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
	            return pass;
	        }
	        
	        if(DataValidator.isNull(request.getParameter("firstName"))) {
	            request.setAttribute("firstName", PropertyReader.getValue("error.require","First Name"));
	            pass = false;
	        } else if(!DataValidator.isName(request.getParameter("firstName"))) {
	            request.setAttribute("firstName", "Invalid First Name");
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("lastName"))) {
	            request.setAttribute("lastName", PropertyReader.getValue("error.require","Last Name"));
	            pass = false;
	        }
	        else if(!DataValidator.isName(request.getParameter("lastName"))) {
	            request.setAttribute("lastName", "Invalid Last Name");
	            pass = false;
	        }
	        if(DataValidator.isNull(request.getParameter("email"))) {
	            request.setAttribute("email", PropertyReader.getValue("error.require","Email Id"));
	            pass = false;
	        } else if(!DataValidator.isEmail(request.getParameter("email"))) {
	            request.setAttribute("email",PropertyReader.getValue("error.email","Email"));
	            pass = false;
	        }
	        if(DataValidator.isNull(request.getParameter("dob"))) {
	            request.setAttribute("dob", PropertyReader.getValue("error.require","Date Of Birth"));
	            pass = false;
	        } else if(!DataValidator.isDate(request.getParameter("dob"))) {
	            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
	            pass = false;
	        }
	        if(DataValidator.isNull(request.getParameter("gender"))){
	            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender "));
	            pass = false;
	        }
	        
	        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
	            request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No "));
	            pass = false;
	        } else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
	            request.setAttribute("mobileNo", "Mobile No must have 10 digits");
	            pass = false;
	        } else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
	            request.setAttribute("mobileNo", "Invalid Mobile No");
	            pass = false;
	        }
	        
	        if(DataValidator.isNull(request.getParameter("collegeId"))) {
	            request.setAttribute("collegeId", PropertyReader.getValue("error.require","College Name"));
	            pass = false;
	        } else if(!DataValidator.isLong(request.getParameter("collegeId"))) {
	            request.setAttribute("collegeId","Invalid College Name");
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("courseId"))) {
	            request.setAttribute("courseId", PropertyReader.getValue("error.require","Course Name"));
	            pass = false;
	        }
	        else if(!DataValidator.isLong(request.getParameter("courseId"))) {
	            request.setAttribute("courseId", "Invalid Course Name");
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("subjectId"))) {
	            request.setAttribute("subjectId", PropertyReader.getValue("error.require","Subject Name"));
	            pass = false;
	        } else if(!DataValidator.isLong(request.getParameter("subjectId"))) {
	            request.setAttribute("subjectId","Invalid Subject Name");
	            pass = false;
	        }

	        return pass;
	    }

	    @Override
	    protected BaseDTO populateDTO(HttpServletRequest request) {
	        FacultyDTO dto = new FacultyDTO();
	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
	        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
	        dto.setEmailId(DataUtility.getString(request.getParameter("email")));
	        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
	        dto.setGender(DataUtility.getString(request.getParameter("gender")));
	        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
	        dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
	        dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
	        dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
	       
	        return dto;
	    }

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	Long id = DataUtility.getLong(req.getParameter("id"));
	    	  FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
	    	if(id>0) {
	    		try {
	    			FacultyDTO dto = model.findByPK(id);
					ServletUtility.setDto(dto, req);
				} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, req, resp);
					return;
				}
	    	}
	        ServletUtility.forward(getView(), req, resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String op = DataUtility.getString(req.getParameter("operation"));
	        FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
	        if(FacultyCtl.OP_SAVE.equalsIgnoreCase(op)) {
	            FacultyDTO dto = (FacultyDTO) populateDTO(req);
	            try {
	                model.add(dto);
	                ServletUtility.setDto(dto, req);
	                ServletUtility.setSuccessMessage("Faculty Added SuccessFully !!!", req);
	            }catch(DuplicateRecordException dre) {
	                ServletUtility.setDto(dto, req);
	                ServletUtility.setErrorMessage("Faculty Already Exist !!!", req);
	            }catch(ApplicationException ae) {
	                ae.printStackTrace();
	                ServletUtility.handleException(ae, req, resp);
	            }
	            ServletUtility.forward(getView(), req, resp);
	        }else if(OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.FACULTY_CTL, req, resp);
	            return;
	        }else if(OP_UPDATE.equalsIgnoreCase(op)) {
	        	FacultyDTO dto = (FacultyDTO) populateDTO(req);
		       	try {
		               model.update(dto);
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setSuccessMessage("Faculty Updated SuccessFully !!!", req);
		           }catch(DuplicateRecordException dre) {
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setErrorMessage("Faculty Already Exist !!!", req);
		           }catch(ApplicationException ae) {
		               ae.printStackTrace();
		               ServletUtility.handleException(ae, req, resp);
		               return;
		           } catch (DatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       }
		       else if(OP_CANCEL.equalsIgnoreCase(op)) {
		       	 ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, req, resp);
		       	 return;
		       }
		       ServletUtility.forward(getView(), req, resp);
	    }


	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}