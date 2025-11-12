package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "RoleListCtl",urlPatterns = {"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			List<RoleDTO> roleList = model.list();
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		RoleDTO dto = new RoleDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		RoleDTO dto = (RoleDTO) populateDTO(req);
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			List<RoleDTO> list= model.search(dto, pageNo, pageSize);
			List<RoleDTO> next= model.search(dto, pageNo+1, pageSize);
			if(list.isEmpty() || list==null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleDTO> list = null;
		List<RoleDTO> next = null;
		
		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));
		
		pageNo = (pageNo==0)? 1 : pageNo;
		pageSize = (pageSize==0)? (DataUtility.getInt(PropertyReader.getValue("page.size")))  : pageSize;
		RoleDTO dto = new RoleDTO();
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		
		String op = DataUtility.getString(req.getParameter("operation"));
		
		try {
			
		if(OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
			
			if(OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo=1;
				dto = (RoleDTO) populateDTO(req);
				
			}else if(OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
				
			}else if(OP_PREVIOUS.equalsIgnoreCase(op)) {
				pageNo--;
			}
			
			
		} else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, req, resp);
			return;
		}else if(OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, req, resp);
			return;
			
		}else if(OP_DELETE.equalsIgnoreCase(op)) {
			pageNo=1;
			String[] ids = req.getParameterValues("ids");	
			if(ids!=null && ids.length >0) {
				RoleDTO deleteDTO = new RoleDTO();
				for(String id  : ids) {
					deleteDTO.setId(DataUtility.getLong(id));
					model.delete(deleteDTO);
					ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
				}
			}else {
				ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
			}
			
		}else if(OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, req, resp);
			return;
		}
		
		list= model.search(dto, pageNo, pageSize);
		next = model.search(dto, pageNo+1, pageSize);
			if(list.isEmpty() || list==null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}
	
	
	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}

}