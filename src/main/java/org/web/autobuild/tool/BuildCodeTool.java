package org.web.autobuild.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.web.autobuild.domain.code.BuildCodeRequestDO;
import org.web.autobuild.domain.code.CodeAttributeDO;

public class BuildCodeTool {

	private static Logger logger = Logger.getLogger(BuildCodeTool.class);
	
	public static final String UN_SUPPORT = "NO";
	public static final String SUPPORT = "YES";

	/**
	 * 根据DB信息，生成java格式的名称。
	 * */
	public static String generateJavaPatternName(String value) {
		String[] array = value.split("_");
		String attrsName = "";
		if (array.length == 1) {
			if (array[0].toUpperCase().equals(array[0])) {
				return array[0].toLowerCase();
			}
			return BuildCodeTool.initLower(array[0]);
		} else {
			for (int i = 0; i < array.length; i++) {
				if (i == 0) {
					attrsName = attrsName + array[i].toLowerCase();
				} else {
					attrsName = attrsName + initUpper(array[i].toLowerCase());
				}
			}
		}
		return attrsName;
	}

	/**
	 * Change the first letter with upper.
	 * 
	 * @param str
	 * @return
	 */
	public static String initLower(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	/**
	 * Change the first letter with upper.
	 * 
	 * @param str
	 * @return
	 */
	public static String initUpper(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static void buildBuildCodeRequest(BuildCodeRequestDO buildCodeRequestDO, String tableName, Connection connection) {

		try {
			List<String> list = new ArrayList<String>();
			Map<String, CodeAttributeDO> map = new HashMap<String, CodeAttributeDO>();
			buildCodeRequestDO.setList(list);
			buildCodeRequestDO.setMap(map);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			// 获取基本信息
			PreparedStatement ps = connection.prepareStatement("select * from " + tableName);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsme = rs.getMetaData();
			int columnCount = rsme.getColumnCount();
			System.out.println("ResultSet对象中的列数" + columnCount);
			for (int i = 1; i <= columnCount; i++) {
				CodeAttributeDO codeAttributeDO = new CodeAttributeDO();
				codeAttributeDO.setColumnName(rsme.getColumnName(i));
				if ("update_version".equals(codeAttributeDO.getColumnName().toLowerCase())) {
					buildCodeRequestDO.setHasUpdateVersion(true);
				}
				codeAttributeDO.setColumnSize(String.valueOf(rsme.getPrecision(i)));
				if (1 == rsme.isNullable(i)) {
					codeAttributeDO.setAllowNull("YES");
				} else {
					codeAttributeDO.setAllowNull("NO");
				}
				if (codeAttributeDO.getColumnName().toLowerCase().endsWith("id") || codeAttributeDO.getColumnName().toLowerCase().contains("status")) {
					codeAttributeDO.setInSupport(SUPPORT);
				} else {
					codeAttributeDO.setInSupport(UN_SUPPORT);
				}
				codeAttributeDO.setEqualSupport(SUPPORT);
				codeAttributeDO.setIsPrimary(UN_SUPPORT);
				if ("INT".equals(rsme.getColumnTypeName(i))) {
					codeAttributeDO.setColumnType("BIGINT");
				} else {
					codeAttributeDO.setColumnType(rsme.getColumnTypeName(i));
				}
				
				codeAttributeDO.setAttributeName(BuildCodeTool.generateJavaPatternName(rsme.getColumnName(i)));
				codeAttributeDO.setAttributeType(BuildCodeTool.sqlType2JavaType(rsme.getColumnTypeName(i), codeAttributeDO.getColumnName()));
				codeAttributeDO.setIndistinctSupport(BuildCodeTool.UN_SUPPORT);
				if (codeAttributeDO.getColumnType().toLowerCase().contains("date") || codeAttributeDO.getColumnType().toLowerCase().contains("time")
						|| codeAttributeDO.getColumnName().toLowerCase().contains("amount") || codeAttributeDO.getColumnName().toLowerCase().contains("time")) {
					codeAttributeDO.setCompareSupport(SUPPORT);
				} else {
					codeAttributeDO.setCompareSupport(UN_SUPPORT);
				}

				list.add(codeAttributeDO.getColumnName());
				map.put(codeAttributeDO.getColumnName(), codeAttributeDO);
			}

			// 获取字段描述
			ResultSet rs2 = databaseMetaData.getColumns(connection.getCatalog(), "%", tableName, "%");
			while (rs2.next()) {
				CodeAttributeDO entity = map.get(rs2.getString("COLUMN_NAME"));
				if (entity == null) {
					System.out.println();
				}
				String comments = rs2.getString("REMARKS");
				if (StringUtils.isEmpty(comments)) {
					entity.setDescription("This field corresponds to the database column " + tableName + "." + entity.getColumnName());
				} else {
					entity.setDescription(comments);
				}
			}

			// 获取主键
			ResultSet rs3 = databaseMetaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
			while (rs3.next()) {
				CodeAttributeDO primary = map.get(rs3.getString(4));
				primary.setIsPrimary(SUPPORT);
				buildCodeRequestDO.setPrimaryCodeAttributeDO(primary);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String sqlType2JavaType(String sqlType, String colname) {

		if (colname.toLowerCase().contains("amount")) {
			return "Long";
		}

		if (sqlType.equalsIgnoreCase("bit")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("double") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar2") || sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		} else if (sqlType.equalsIgnoreCase("TIMESTAMP")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("NUMBER")) {
			return "Long";
		} else if (sqlType.equals("INT UNSIGNED")) {
			return "Long";
		}
		return "null";
	}

	public static String formatXml(String xml) {
		xml = xml.replace("order or query info. -->", "order or query info. -->\n\t\t").replace("]]></select>", "]]>\n\t</select>")
				.replace("</resultMap>", "</resultMap>\n").replaceAll("</sql>", "</sql>\n").replaceAll("</insert>", "</insert>\n")
				.replaceAll("</select>", "</select>\n");
		xml = xml.replace("]]><if test=", "]]>\n\t\t<if test=").replace("</set><![CDATA", "</set>\n\t\t<![CDATA").replace("<resultMap", "\n\t<resultMap")
				.replace("</update>", "</update>\n");
		xml = xml.replaceAll("</dynamic>", "</dynamic>\n\t\t");
		return xml;
	}

	public static void generateFile(String serviceModule, String path, String fileName, Map map) {
		try {
			String serviceContent = VelocityHelper.evaluateToWriter(map, new InputStreamReader(new FileInputStream(serviceModule), "UTF-8")).toString();
			File file = new File(path + fileName);
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(serviceContent);
			pw.write("\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String generateContext(String serviceModule, Map map) {
		try {
			String serviceContent = VelocityHelper.evaluateToWriter(map, new InputStreamReader(new FileInputStream(serviceModule), "UTF-8")).toString();
			return serviceContent;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static void generateFile(List <String> list, String context, String fileName) {
		try {
			list.add(fileName);
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(context);
			pw.write("\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
