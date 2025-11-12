package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.MarksheetModelInt;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "MarksheetCtl",urlPatterns = {"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		try {
			List<StudentDTO> studentList =  model.list();
			request.setAttribute("studentList",studentList);
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

	        if(DataValidator.isNull(request.getParameter("rollNo"))) {
	            request.setAttribute("rollNo", PropertyReader.getValue("error.require","Roll No"));
	            pass = false;
	        } else if(!DataValidator.isRollNo(request.getParameter("rollNo"))) {
	            request.setAttribute("rollNo", "Invalid RollNo Name");
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("studentId"))) {
	            request.setAttribute("studentId", PropertyReader.getValue("error.require","Name "));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("physics"))) {
				request.setAttribute("physics", PropertyReader.getValue("error.require", "Marks"));
				pass = false;
			} else if (DataValidator.isNotNull(request.getParameter("physics"))
					&& !DataValidator.isInteger(request.getParameter("physics"))) {
				request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			} else if (DataUtility.getInt(request.getParameter("physics")) > 100
					|| DataUtility.getInt(request.getParameter("physics")) < 0) {
				request.setAttribute("physics", "Marks should be in 0 to 100");
				pass = false;
			}

	        if (DataValidator.isNull(request.getParameter("chemistry"))) {
				request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Marks"));
				pass = false;
			} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
					&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
				request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100
					|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {
				request.setAttribute("chemistry", "Marks should be in 0 to 100");
				pass = false;
			}
	        
	        if (DataValidator.isNull(request.getParameter("maths"))) {
				request.setAttribute("maths", PropertyReader.getValue("error.require", "Marks"));
				pass = false;
			} else if (DataValidator.isNotNull(request.getParameter("maths"))
					&& !DataValidator.isInteger(request.getParameter("maths"))) {
				request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			} else if (DataUtility.getInt(request.getParameter("maths")) > 100
					|| DataUtility.getInt(request.getParameter("maths")) < 0) {
				request.setAttribute("maths", "Marks should be in 0 to 100");
				pass = false;
			}

	        return pass;
	    }

	    @Override
	    protected BaseDTO populateDTO(HttpServletRequest request) {
	        MarksheetDTO dto = new MarksheetDTO();
	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
	        dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
	        if (request.getParameter("physics") != null && request.getParameter("physics").length() != 0) {
				dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
			}
			if (request.getParameter("chemistry") != null && request.getParameter("chemistry").length() != 0) {
				dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
			}
			if (request.getParameter("maths") != null && request.getParameter("maths").length() != 0) {
				dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
			}
	        return dto;
	    }

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	Long id = DataUtility.getLong(req.getParameter("id"));
	    	MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
	    	if(id>0) {
	    		try {
	    			MarksheetDTO dto = model.findByPK(id);
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
	        MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
	        if(UserCtl.OP_SAVE.equalsIgnoreCase(op)) {
	            MarksheetDTO dto = (MarksheetDTO) populateDTO(req);
	            try {
	                model.add(dto);
	                ServletUtility.setDto(dto, req);
	                ServletUtility.setSuccessMessage("Marksheet Added SuccessFully !!!", req);
	            }catch(DuplicateRecordException dre) {
	                ServletUtility.setDto(dto, req);
	                ServletUtility.setErrorMessage("Marksheet Already Exist !!!", req);
	            }catch(ApplicationException ae) {
	                ae.printStackTrace();
	                ServletUtility.handleException(ae, req, resp);
	                return;
	            }
	        }else if(OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.MARKSHEET_CTL, req, resp);
	            return;
	        }else if(OP_UPDATE.equalsIgnoreCase(op)) {
	        	MarksheetDTO dto = (MarksheetDTO) populateDTO(req);
		       	try {
		               model.update(dto);
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setSuccessMessage("Marksheet Updated SuccessFully !!!", req);
		           }catch(DuplicateRecordException dre) {
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setErrorMessage("Marksheet Already Exist !!!", req);
		           }catch(ApplicationException ae) {
		               ae.printStackTrace();
		               ServletUtility.handleException(ae, req, resp);
		               return;
		           }
		       }
		       else if(OP_CANCEL.equalsIgnoreCase(op)) {
		       	 ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, req, resp);
		       	 return;
		       }
		       ServletUtility.forward(getView(), req, resp);
	    }
	
	
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}