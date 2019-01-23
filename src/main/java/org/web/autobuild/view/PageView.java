package org.web.autobuild.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.autobuild.domain.BuildPageRequestDO;
import org.web.autobuild.domain.PageAttributeDO;
import org.web.autobuild.query.QueryPageAttribute;
import org.web.autobuild.service.PageAttributeService;
import org.web.autobuild.tool.BuildCodeTool;
import org.web.autobuild.tool.ZipHelper;

@Scope("prototype")
@Controller
@RequestMapping({ "page" })
public class PageView {
	private static Logger logger = Logger.getLogger(PageView.class);
	@Resource
	private PageAttributeService pageAttributeService;

	@RequestMapping(value = "download.zip", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void downloadZip(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String loadPath = request.getSession().getServletContext().getRealPath("/") + "zip" + File.separator + "page" + File.separator;
		String tableName = request.getParameter("tableName");
		String name = request.getParameter("mark");
		if (StringUtils.isEmpty(name)) {
			name = tableName;
		}
		File file = new File(loadPath + name + ".zip");
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
	
	@RequestMapping(value = { "buildPageView" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET,
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String buildPageView(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "code/buildPageView";
	}

	@RequestMapping(value = { "queryPageInfo" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET,
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String queryPageInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mark = request.getParameter("mark");

		QueryPageAttribute query = new QueryPageAttribute();
		query.setViewMark(mark);
		List<PageAttributeDO> list = this.pageAttributeService.selectPageAttributeList(query);
		request.setAttribute("list", buildList(list));
		request.setAttribute("map", buildMap(list));
		return "code/queryPageInfo";
	}

	@RequestMapping(value = { "buildPage" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET,
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String buildPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		buildBuildPageRequestDO(request);
		return "code/queryPageInfo";
	}

	@SuppressWarnings("unchecked")
	private BuildPageRequestDO buildBuildPageRequestDO(HttpServletRequest request) {
		BuildPageRequestDO buildPageRequestDO = new BuildPageRequestDO();
		String webName = request.getParameter("webName");
		String moduleName = request.getParameter("moduleName");
		String actionName = request.getParameter("actionName");
		String mark = request.getParameter("mark");
		String primaryKey = request.getParameter("primaryKey");
		String output = request.getParameter("output");

		actionName = BuildCodeTool.initUpper(actionName);
		buildPageRequestDO.setWebName(webName);
		buildPageRequestDO.setModuleName(moduleName);
		buildPageRequestDO.setActionName(actionName);
		buildPageRequestDO.setPrimaryKey(primaryKey);
		QueryPageAttribute query = new QueryPageAttribute();
		query.setViewMark(mark);
		List<PageAttributeDO> list = this.pageAttributeService.selectPageAttributeList(query);
		buildPageRequestDO.setList(buildList(list));
		buildPageRequestDO.setMap(buildMap(list));
		request.setAttribute("list", buildPageRequestDO.getList());
		request.setAttribute("map", buildPageRequestDO.getMap());
		request.setAttribute("buildPageRequest", buildPageRequestDO);

		String moduleHtml = BuildCodeTool.initLower(actionName) + ".html";
		String detailHtml = "select" + actionName + "Detail.html";
		String listHtml = "select" + actionName + "List.html";
		String controllerJava = actionName + "Controller.java";
		Map map = new HashMap();
		map.put("buildPageRequest", buildPageRequestDO);
		map.put("lowerActionName", BuildCodeTool.initLower(buildPageRequestDO.getActionName()));

		String baseModule = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator + "views" + File.separator
				+ "module" + File.separator;
		if (!CodeView.IS_WEB.booleanValue()) {
			BuildCodeTool.generateFile(baseModule + "pagemodule.txt", output, moduleHtml, map);

			BuildCodeTool.generateFile(baseModule + "pagedetail.txt", output, detailHtml, map);

			BuildCodeTool.generateFile(baseModule + "pagelist.txt", output, listHtml, map);

			BuildCodeTool.generateFile(baseModule + "pagecontroller.txt", output, controllerJava, map);
		} else {
			String loadPath = request.getSession().getServletContext().getRealPath("/") + "zip" + File.separator + "page" + File.separator;
			List<String> fileList = new ArrayList<String>();
			BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "pagemodule.txt", map), loadPath + moduleHtml);
			BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "pagedetail.txt", map), loadPath + detailHtml);
			BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "pagelist.txt", map), loadPath + listHtml);
			BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "pagecontroller.txt", map), loadPath + controllerJava);
			genZip(fileList, loadPath +mark);
		}
		return buildPageRequestDO;
	}

	private Map<String, PageAttributeDO> buildMap(List<PageAttributeDO> list) {
		Map<String, PageAttributeDO> map = new HashMap<String, PageAttributeDO>();
		for (PageAttributeDO pageAttributeDO : list) {
			map.put(pageAttributeDO.getAttribute(), pageAttributeDO);
		}
		return map;
	}

	private void genZip(List<String> list, String mark) {
		String[] srcFiles = new String[list.size()];
		int i = 0;
		for (String file : list) {
			srcFiles[i] = file;
			i++;
		}
		try {
			ZipHelper.zipCompress(srcFiles, mark + ".zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String fileName : list) {
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}
	
	private List<String> buildList(List<PageAttributeDO> pageAttributeDOList) {
		List<String> list = new ArrayList();
		for (PageAttributeDO pageAttributeDO : pageAttributeDOList) {
			list.add(pageAttributeDO.getAttribute());
		}
		return list;
	}
}
