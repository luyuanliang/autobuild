package org.web.autobuild.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.autobuild.service.CodeAttributeService;
import org.web.base.helper.PropertiesHelper;

@Scope("prototype")
@Controller
@SuppressWarnings("restriction")
public class HelpView {

	public static final String password = PropertiesHelper.getString("config/datasource", "jdbc.password");
	public static final String uri = PropertiesHelper.getString("config/datasource", "jdbc.url");
	public static final String username = PropertiesHelper.getString("config/datasource", "jdbc.username");
	
	private static Logger logger = Logger.getLogger(HelpView.class);
	
	@RequestMapping(value = "help", method = { RequestMethod.GET, RequestMethod.POST })
	public String help(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setAttribute("password", password);
		request.setAttribute("uri", uri);
		request.setAttribute("username", username);
		return "help";
	}

	
	@RequestMapping(value = "download.zip", method = { RequestMethod.GET, RequestMethod.POST })
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String download = request.getSession().getServletContext().getRealPath("/") + "zip" + File.separator +"download_common.zip";
		File file = new File(download);
		if (!file.exists()) {
			response.getWriter().write("文件不存在");
		} else {
			FileInputStream fin = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			while (fin.read(bytes) != -1) {
				response.getOutputStream().write(bytes);
			}
			fin.close();
		}
		response.flushBuffer();
	}
}
