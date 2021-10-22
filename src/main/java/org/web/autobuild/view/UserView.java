package org.web.autobuild.view;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.base.domain.view.ViewResultDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Scope("prototype")
@Controller
@RequestMapping("member")
public class UserView {
	
	// private static final Logger logger = LoggerFactory.getLogger(UserView2.class);

	@RequestMapping(value = "insertUser", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		ViewResultDO result = new ViewResultDO();
		result.setMsg("添加成功");
		result.setResult(true);
		result.setType("warning");
	    return gson.toJson(result);
	}

	@RequestMapping(value = "deleteUser", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		ViewResultDO result = new ViewResultDO();
		result.setMsg("删除成功");
		result.setResult(true);
		result.setType("error");
		return gson.toJson(result);
	}

	@RequestMapping(value = "updateUser", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Gson gson = new Gson();
		ViewResultDO result = new ViewResultDO();
		result.setMsg("修改成功");
		result.setResult(true);
		result.setType("warning");
	    return gson.toJson(result);
	}

	@RequestMapping(value = "selectUserDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String selectUserDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "member/selectUserDetail";
	}
	
	@RequestMapping(value = "selectUserList", method = { RequestMethod.GET, RequestMethod.POST })
	public String selectUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/selectUserList";
	}
	
	@RequestMapping(value = "user", method = { RequestMethod.GET, RequestMethod.POST })
	public String user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/user";
	}
	
}


