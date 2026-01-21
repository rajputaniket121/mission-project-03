package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;


@WebServlet(name = "RoleCtl", urlPatterns = {"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl{
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require","Role Name"));
			pass = false;
		}else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		RoleDTO dto = new RoleDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = DataUtility.getLong(req.getParameter("id"));
    	RoleModelInt model = ModelFactory.getInstance().getRoleModel();
    	if(id>0) {
    		try {
				RoleDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);
			} catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                }else {
                	ServletUtility.handleException(e, req, resp);
                }
                return;
            }
    	}
        ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = DataUtility.getString(req.getParameter("operation"));
		
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		if(RoleCtl.OP_SAVE.equalsIgnoreCase(op)) {
			RoleDTO dto = (RoleDTO) populateDTO(req);
			try {
				model.add(dto);
				ServletUtility.setSuccessMessage("Role Added Successfully !!!", req);
				ServletUtility.setDto(dto, req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Role Already exist !!!", req);
			}catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
			}
		}else if(RoleCtl.OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.ROLE_CTL,req, resp);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			RoleDTO dto = (RoleDTO) populateDTO(req);
       	try {
               model.update(dto);
               ServletUtility.setDto(dto, req);
               ServletUtility.setSuccessMessage("Role Updated SuccessFully !!!", req);
           }catch(DuplicateRecordException dre) {
               ServletUtility.setDto(dto, req);
               ServletUtility.setErrorMessage("Role Already Exist !!!", req);
           }catch (ApplicationException e) {
               e.printStackTrace();
               if(e.getClass().toString().equals(e.toString())) {
               	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
               }else {
               	ServletUtility.handleException(e, req, resp);
               }
               return;
           }
       }
       else if(OP_CANCEL.equalsIgnoreCase(op)) {
       	 ServletUtility.redirect(ORSView.ROLE_LIST_CTL, req, resp);
       	 return;
       }
       ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}

}