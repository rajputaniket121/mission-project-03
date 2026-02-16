package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.ProfileDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.ProfileModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "ProfileListCtl", urlPatterns = { "/ctl/ProfileListCtl" })
public class ProfileListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		ProfileModelInt model = ModelFactory.getInstance().getProfileModel();
		try {
			List<ProfileDTO> profileStatusList = model.list();
			HashMap<String, String> profileStatusMap = new HashMap<>();
			Iterator<ProfileDTO> it = profileStatusList.iterator();

			while (it.hasNext()) {
				ProfileDTO dto = it.next();
				profileStatusMap.put(dto.getProfileStatus(), dto.getProfileStatus());
			}
			request.setAttribute("profileStatusMap", profileStatusMap);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		ProfileDTO dto = new ProfileDTO();
		dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
		dto.setProfileStatus(DataUtility.getString(request.getParameter("profileStatus")));
		return dto;
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		ProfileDTO dto = (ProfileDTO) populateDTO(req);
		ProfileModelInt model = ModelFactory.getInstance().getProfileModel();

		try {
			List<ProfileDTO> list = model.search(dto, pageNo, pageSize);
			List<ProfileDTO> next = model.search(dto, pageNo + 1, pageSize);

			if (list.isEmpty() || list == null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
			ServletUtility.handleExceptionDBDown(e, req, resp, getView());
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<ProfileDTO> list = null;
		List<ProfileDTO> next = null;

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		ProfileDTO dto = (ProfileDTO) populateDTO(req);
		ProfileModelInt model = ModelFactory.getInstance().getProfileModel();

		String op = DataUtility.getString(req.getParameter("operation"));

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.PROFILE_LIST_CTL, req, resp);
				return;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.PROFILE_CTL, req, resp);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				String[] ids = req.getParameterValues("ids");
				if (ids != null && ids.length > 0) {
					ProfileDTO deleteDTO = new ProfileDTO();
					for (String id : ids) {
						deleteDTO.setId(DataUtility.getLong(id));
						model.delete(deleteDTO);
						ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
					}
				} else {
					ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
				}
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.PROFILE_LIST_CTL, req, resp);
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
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleExceptionDBDown(e, req, resp, getView());
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected String getView() {
		return ORSView.PROFILE_LIST_VIEW;
	}
}