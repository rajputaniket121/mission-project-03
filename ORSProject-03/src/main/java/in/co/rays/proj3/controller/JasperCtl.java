package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.impl.SessionImpl;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.utill.HibDataSource;
import in.co.rays.proj3.utill.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Jasper Report Functionality Controller. Performs operation for Print pdf of
 * Marksheet Merit List
 *
 * @author Aniket Rajput
 */
@WebServlet(name = "JasperCtl", urlPatterns = { "/ctl/JasperCtl" })
public class JasperCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			/* Compilation of jrxml file */
			JasperReport jasperReport = JasperCompileManager
					.compileReport("E:\\Rays Techno\\Eclipse Workspace\\project\\project 3\\ORSProject03_Report.jrxml");

			HttpSession session = request.getSession(true);
			UserDTO dto = (UserDTO) session.getAttribute("user");
			dto.getFirstName();
			dto.getLastName();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", 1l);
			java.sql.Connection conn = null;

			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj3.bundle.system");


			String Database = rb.getString("DATABASE");
			
			if ("Hibernate".equalsIgnoreCase(Database)) {
				conn = ((SessionImpl) HibDataSource.getSession()).connection();
			}

			if ("JDBC".equalsIgnoreCase(Database)) {
				conn = JDBCDataSource.getConnection();
			}

			/* Filling data into the report */
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);

			/* Export Jasper report */
			byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

			response.setContentType("application/pdf");
			response.getOutputStream().write(pdf);
			response.getOutputStream().flush();
		} catch (Exception e) {

		}
	}

	@Override
	protected String getView() {
		return null;
	}

}
