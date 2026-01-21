package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

/**
 * UserListCtl is a servlet controller class to manage the display and
 * operations on the list of users in the system.
 *
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(UserListCtl.class);

	/**
	 * Preloads data for the view such as role list.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("Inside preload method of UserListCtl");
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			List<RoleDTO> roleList = model.list();
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates a {@link UserDTO} from request parameters for search/filter
	 * operations.
	 * 
	 * @param request HttpServletRequest object
	 * @return UserDTO populated with filter values
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("Inside populateDTO method of UserListCtl");
		UserDTO dto = new UserDTO();
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setRoleId(DataUtility.getInt(request.getParameter("roleId")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		return dto;
	}

	/**
	 * Handles GET request to display the initial user list.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Inside doGet method of UserListCtl");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserDTO dto = (UserDTO) populateDTO(req);
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		try {
			List<UserDTO> list = model.search(dto, pageNo, pageSize);
			List<UserDTO> next = model.search(dto, pageNo + 1, pageSize);
			if (list.isEmpty() || list == null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
            e.printStackTrace();
            if(e.getClass().toString().equals(e.toString())) {
            	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
            }else {
            	ServletUtility.handleException(e, req, resp);
            }
            return;
        }
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Handles POST requests to process search, pagination, deletion, and
	 * navigation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Inside doPost method of UserListCtl");
		List<UserDTO> list = null;
		List<UserDTO> next = null;

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? (DataUtility.getInt(PropertyReader.getValue("page.size"))) : pageSize;

		UserDTO dto = new UserDTO();
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		String op = DataUtility.getString(req.getParameter("operation"));

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					dto = (UserDTO) populateDTO(req);
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, req, resp);
				return;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, req, resp);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				String[] ids = req.getParameterValues("ids");
				if (ids != null && ids.length > 0) {
					pageNo = 1;
					UserDTO deleteDTO = new UserDTO();
					for (String id : ids) {
						deleteDTO.setId(DataUtility.getLong(id));
						model.delete(deleteDTO);
						ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
					}
				} else {
					ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
				}
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, req, resp);
				return;
			}

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);
			if (list.isEmpty() || list == null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		}catch (ApplicationException e) {
            e.printStackTrace();
            if(e.getClass().toString().equals(e.toString())) {
            	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
            }else {
            	ServletUtility.handleException(e, req, resp);
            }
            return;
        }

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Returns the view page for displaying the user list.
	 * 
	 * @return JSP page path for the user list
	 */
	@Override
	protected String getView() {
		log.debug("Inside getView method of UserListCtl");
		return ORSView.USER_LIST_VIEW;
	}
}