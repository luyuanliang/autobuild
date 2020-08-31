package org.web.autobuild.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
import org.web.autobuild.dao.CodeAttributeDAO;
import org.web.autobuild.domain.code.BuildCodeRequestDO;
import org.web.autobuild.domain.code.CodeAttributeDO;
import org.web.autobuild.domain.code.EntityNameDO;
import org.web.autobuild.domain.code.PackageInfoDO;
import org.web.autobuild.query.QueryCodeAttributeDO;
import org.web.autobuild.service.CodeAttributeService;
import org.web.autobuild.tool.BuildCodeTool;
import org.web.autobuild.tool.ZipHelper;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.web.helper.PropertiesHelper;
import org.web.helper.ServiceExceptionHelper;

@Scope("prototype")
@Controller
@RequestMapping("code")
@SuppressWarnings("restriction")
public class CodeView {

	private static Logger logger = Logger.getLogger(CodeView.class);

	public static final Boolean IS_WEB = Boolean.valueOf(ResourceBundle.getBundle("config/datasource", Locale.getDefault()).getString("IS_WEB"));

	@Resource
	private CodeAttributeService codeAttributeService;

	@Resource
	private CodeAttributeDAO codeAttributeDAO;

	@RequestMapping(value = "buildCodeView", method = { RequestMethod.GET, RequestMethod.POST })
	public String buildCodeView(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "code/buildCodeView";
	}

	@RequestMapping(value = "download.zip", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void downloadZip(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String loadPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "zip" + File.separator;
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

	@RequestMapping(value = "queryTableInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryTableInfo(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String tableName = request.getParameter("tableName");
		String mark = request.getParameter("mark");
		if (StringUtils.isEmpty(mark)) {
			mark = tableName;
		}

		BuildCodeRequestDO buildCodeRequestDO = new BuildCodeRequestDO();
		try {
			String driver = PropertiesHelper.getString("config/datasource","jdbc.driverClassName");
			Class.forName(driver).newInstance();
			Connection connection = DriverManager.getConnection(url, user, password);
			BuildCodeTool.buildBuildCodeRequest(buildCodeRequestDO, tableName, connection);

			QueryCodeAttributeDO queryCodeAttributeDO = new QueryCodeAttributeDO();
			queryCodeAttributeDO.setMark(mark);
			Map<String, CodeAttributeDO> map = codeAttributeService.queryCodeAttributeMap(queryCodeAttributeDO);

			if (map != null && map.size() > 0) {
				for (String key : buildCodeRequestDO.getMap().keySet()) {
					try {
						CodeAttributeDO codeAttributeDO1 = buildCodeRequestDO.getMap().get(key);
						CodeAttributeDO codeAttributeDO2 = map.get(key);
						if (codeAttributeDO2 != null) {
							codeAttributeDO1.setEqualSupport(codeAttributeDO2.getEqualSupport());
							codeAttributeDO1.setIndistinctSupport(codeAttributeDO2.getIndistinctSupport());
							codeAttributeDO1.setCompareSupport(codeAttributeDO2.getCompareSupport());
							codeAttributeDO1.setInSupport(codeAttributeDO2.getInSupport());
							codeAttributeDO1.setDescription(codeAttributeDO2.getDescription());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			request.setAttribute("list", buildCodeRequestDO.getList());
			request.setAttribute("map", buildCodeRequestDO.getMap());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ServiceExceptionHelper.getExceptionInfo(e));
		}
		return "code/queryTableInfo";
	}

	@RequestMapping(value = "buildCode", method = { RequestMethod.GET, RequestMethod.POST })
	public String buildCode(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String tableName = request.getParameter("tableName");

		BuildCodeRequestDO buildCodeRequestDO = new BuildCodeRequestDO();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url, user, password);
			BuildCodeTool.buildBuildCodeRequest(buildCodeRequestDO, tableName, connection);

			request.setAttribute("list", buildCodeRequestDO.getList());
			request.setAttribute("map", buildCodeRequestDO.getMap());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		String updateListInfo = request.getParameter("updateList");
		Gson gson = new Gson();
		List<LinkedTreeMap> updateList = gson.fromJson(updateListInfo, List.class);
		for (LinkedTreeMap map : updateList) {
			String key = (String) map.get("column");
			CodeAttributeDO updateEntity = buildCodeRequestDO.getMap().get(key);
			updateEntity.setIndistinctSupport((String) map.get("indistinctQuery"));
			updateEntity.setCompareSupport((String) map.get("compareQuery"));
			updateEntity.setInSupport((String) map.get("inQuery"));
			updateEntity.setDescription((String) map.get("commonts"));
			updateEntity.setEqualSupport((String) map.get("equalQuery"));
			updateEntity.setDescription((String) map.get("commonts"));
		}

		buildRequestFromView(buildCodeRequestDO, request);
		execute(buildCodeRequestDO, request);

		List<CodeAttributeDO> list = new ArrayList<CodeAttributeDO>();
		for (String key : buildCodeRequestDO.getList()) {
			list.add(buildCodeRequestDO.getMap().get(key));
		}
		save(list, buildCodeRequestDO.getMark());
		return "code/queryTableInfo";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void execute(BuildCodeRequestDO buildCodeRequestDO, HttpServletRequest request) {
		Map map = new HashMap();
		map.put("buildCodeRequest", buildCodeRequestDO);
		map.put("dot", ".");
		map.put("SUPPORT", BuildCodeTool.SUPPORT);
		map.put("tool", new BuildCodeTool());

		String baseModule = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator + "views" + File.separator
				+ "module" + File.separator;
		String genPath = buildCodeRequestDO.getPath();

		// 创建DAO
		String daoJava = buildCodeRequestDO.getEntityNameDO().getUpperDaoEntityName() + ".java";
		// 创建Domain
		String entityJava = buildCodeRequestDO.getEntityNameDO().getUpperDomainEntityName() + ".java";
		// 创建Domain
		String queryJava = buildCodeRequestDO.getEntityNameDO().getUpperQueryEntityName() + ".java";
		// 创建Service
		String serviceJava = buildCodeRequestDO.getEntityNameDO().getUpperServiceEntityName() + ".java";
		// 创建Service
		String serviceJava2 = buildCodeRequestDO.getEntityNameDO().getUpperServiceEntityName() + "2.java";
		// 创建mybatise
		String mybatiseXml = "Mapper_" + buildCodeRequestDO.getEntityNameDO().getTableName() + ".xml";

		try {
			if (IS_WEB) {
				String loadPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "zip" + File.separator;
				List<String> fileList = new ArrayList<String>();
				// 创建DAO
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "dao.txt", map), loadPath + daoJava);
				// 创建Domain
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "domain.txt", map), loadPath + entityJava);
				// 创建Query
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "query.txt", map), loadPath + queryJava);
				// 创建Service实现类
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "service.txt", map), loadPath + serviceJava);
				// 创建Service接口
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "service2.txt", map), loadPath + serviceJava2);
				// 创建mybatise
				BuildCodeTool.generateFile(fileList, BuildCodeTool.generateContext(baseModule + "mybatise.txt", map), loadPath + mybatiseXml);
				genZip(fileList, loadPath + buildCodeRequestDO.getMark());
			} else {
				// 创建DAO
				BuildCodeTool.generateFile(baseModule + "dao.txt", genPath, daoJava, map);
				// 创建Domain
				BuildCodeTool.generateFile(baseModule + "domain.txt", genPath, entityJava, map);
				// 创建Query
				BuildCodeTool.generateFile(baseModule + "query.txt", genPath, queryJava, map);
				// 创建Service实现类
				BuildCodeTool.generateFile(baseModule + "service.txt", genPath, serviceJava, map);
				// 创建Service接口
				BuildCodeTool.generateFile(baseModule + "service2.txt", genPath, serviceJava2, map);
				// 创建mybatise
				BuildCodeTool.generateFile(baseModule + "mybatise.txt", genPath,  mybatiseXml, map);
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void save(List<CodeAttributeDO> list, String mark) {
		codeAttributeDAO.deleteRecordByMark(mark);
		Date date = new Date();
		int i = 0;
		for (CodeAttributeDO codeAttributeDO : list) {
			codeAttributeDO.setInputTime(date);
			codeAttributeDO.setSortOrder(i++);
			codeAttributeDO.setMark(mark);
			codeAttributeService.insertCodeAttribute(codeAttributeDO);
		}
	}

	private void buildRequestFromView(BuildCodeRequestDO buildCodeRequestDO, HttpServletRequest request) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		buildCodeRequestDO.setTimeValue(dateFormat.format(new Date()));

		String basePackage = request.getParameter("basePackage");
		String daoPackage = request.getParameter("daoPackage");
		String queryPackage = request.getParameter("queryPackage");
		String entityPackage = request.getParameter("entityPackage");
		String servicePackage = basePackage + "." + "service";

		String entitySuffix = request.getParameter("entitySuffix");
		String queryPrefix = request.getParameter("queryPrefix");
		String daoSuffix = request.getParameter("daoSuffix");
		String createUser = request.getParameter("createUser");
		String outPut = request.getParameter("outPut");
		String tableName = request.getParameter("tableName");

		String mark = request.getParameter("mark");
		if (StringUtils.isEmpty(mark)) {
			mark = tableName;
		}
		buildCodeRequestDO.setMark(mark);

		// String tableName = "user";
		if (StringUtils.isEmpty(createUser)) {
			createUser = "LUYL";
		}
		buildCodeRequestDO.setOwner(createUser);

		if (daoPackage != null && !daoPackage.contains(".")) {
			daoPackage = basePackage + "." + daoPackage;
		}
		if (queryPackage != null && !queryPackage.contains(".")) {
			queryPackage = basePackage + "." + queryPackage;
		}
		if (entityPackage != null && !entityPackage.contains(".")) {
			entityPackage = basePackage + "." + entityPackage;
		}
		if (servicePackage != null && !servicePackage.contains(".")) {
			servicePackage = basePackage + "." + servicePackage;
		}

		PackageInfoDO packageInfoDO = new PackageInfoDO();
		buildCodeRequestDO.setPackageInfoDO(packageInfoDO);
		packageInfoDO.setDaoPackage(daoPackage);
		packageInfoDO.setDomainPackage(entityPackage);
		packageInfoDO.setQueryPackage(queryPackage);
		packageInfoDO.setServicePackage(servicePackage);

		EntityNameDO entityNameDO = new EntityNameDO();
		buildCodeRequestDO.setEntityNameDO(entityNameDO);
		entityNameDO.setTableName(tableName);
		entityNameDO.setEntityName(BuildCodeTool.generateJavaPatternName(tableName));
		entityNameDO.setDaoEntityName(entityNameDO.getEntityName() + daoSuffix);
		entityNameDO.setDomainEntityName(entityNameDO.getEntityName() + entitySuffix);
		entityNameDO.setQueryEntityName(BuildCodeTool.initLower(queryPrefix + entityNameDO.getUpperEntityName()));
		entityNameDO.setServiceEntityName(entityNameDO.getEntityName() + "Service");

		entityNameDO.setUpdateMethod("update" + entityNameDO.getUpperEntityName() + "By"
				+ BuildCodeTool.initUpper(buildCodeRequestDO.getPrimaryCodeAttributeDO().getAttributeName()));
		entityNameDO.setInsertMethod("insert" + entityNameDO.getUpperEntityName());
		entityNameDO.setQueryListMethod("select" + entityNameDO.getUpperEntityName() + "List");
		entityNameDO.setQueryByIdMethod("select" + entityNameDO.getUpperEntityName() + "By"
				+ BuildCodeTool.initUpper(buildCodeRequestDO.getPrimaryCodeAttributeDO().getAttributeName()));
		entityNameDO.setCountMethod("count" + entityNameDO.getUpperEntityName() + "List");

		buildCodeRequestDO.setPath(outPut + File.separator);
	}
}
