package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		try {
			List<CourseDTO> courseList = model.list();
			request.setAttribute("courseList", courseList);
			List<SubjectDTO> subjectList = subModel.list();
			request.setAttribute("subjectList", subjectList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		SubjectDTO dto = (SubjectDTO) populateDTO(req);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		try {
			List<SubjectDTO> list = model.search(dto, pageNo, pageSize);
			List<SubjectDTO> next = model.search(dto, pageNo + 1, pageSize);
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
			ServletUtility.handleException(e, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<SubjectDTO> list = null;
		List<SubjectDTO> next = null;

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? (DataUtility.getInt(PropertyReader.getValue("page.size"))) : pageSize;
		SubjectDTO dto = (SubjectDTO) populateDTO(req);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();

		String op = DataUtility.getString(req.getParameter("operation"));

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					dto = (SubjectDTO) populateDTO(req);

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, req, resp);
				return;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				String[] ids = req.getParameterValues("ids");
				SubjectDTO delete = new SubjectDTO();
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						delete.setId(DataUtility.getLong(id));
						model.delete(delete);
						ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
					}
				} else {
					ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
				}

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, req, resp);
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
			ServletUtility.handleException(e, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}