package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("content")
public class ContentController extends LogController{
	static Logger logger = Logger.getLogger(ContentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private ContentService conService ;
	
	@Autowired
	private ContentcategoryMapper contentcategoryMapper;
	
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Contentcategory> selectByExample = contentcategoryMapper.selectByExample(null);
		request.setAttribute("selectByExample", selectByExample);
		return "content";
	}
	
	
	@RequestMapping(value="contentList",method=RequestMethod.POST)
	public void contentList(Content content,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Content> info = conService.findconPage(content, pageNum, pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",info.getTotal() );
			jsonObj.put("rows", info.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Content content,HttpServletResponse response){
		Integer userId = content.getContentid();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
//				User userName = userService.existUserWithUserName(user.getUsername());
//				if(userName != null && userName.getUserid().compareTo(userId)==0){
//					user.setUserid(userId);
//					userService.updateUser(user);
//					result.put("success", true);
//				}else{
//					result.put("success", true);
//					result.put("errorMsg", "该用户名被使用");
//				}e[[
				
			}else {   // 添加
				List<Content> list =  conService.existUserWithContentName(content.getContenttitle());
				result.put("success", true);
				if(list!=null && list.size()>0){  // 没有重复可以添加
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				} else {
					conService.addContent(content);
					result.put("success", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteContent")
	public void delContent(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				conService.deleteContent(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//导入
			@RequestMapping("importContent")
			public void importContent(MultipartFile impFile,HttpServletRequest request,HttpServletResponse response){
				JSONObject result=new JSONObject();
				try {
					//获取excel文件
					HSSFWorkbook wb = new HSSFWorkbook(impFile.getInputStream());
					//获取文件中的sheet页
					HSSFSheet sheetAt = wb.getSheetAt(0);
					//获取最后一行行数
					int rowNum = sheetAt.getLastRowNum();
					//循环行数获取每一行对象
					for (int i = 1; i <= rowNum; i++) {
						//获取每一行单元格
						HSSFRow row = sheetAt.getRow(i);
						
						Double numericCellValue = row.getCell(0).getNumericCellValue();
						int contentid =numericCellValue.intValue();
						
						String contenttitle = row.getCell(1).getStringCellValue();
						
						String picpath = row.getCell(2).getStringCellValue();
						
						String contenturl = row.getCell(3).getStringCellValue();
						
						Double price1 = row.getCell(4).getNumericCellValue();
						int price = price1.intValue();
						
						String categoryname = row.getCell(5).getStringCellValue();
						
						Double numericCellValue1 = row.getCell(6).getNumericCellValue();
						int status =numericCellValue1.intValue();
						
						Date createtime = row.getCell(7).getDateCellValue();
						//根据部门名查部门id
						Integer contentcategoryid =  findCategoryIdByAname(categoryname);
						
						
						//封装emp对象
						Content content = new Content();
						content.setContentid(contentid);
						content.setContenttitle(contenttitle);
						content.setContentcategoryid(contentcategoryid);
						content.setPicpath(picpath);
						content.setCantenturl(contenturl);
						content.setPrice(price);
						content.setStatus(status);
						content.setCreatetime(createtime);
						//调用service保存方法和数据
						conService.addContent(content);
						
					}
					
					result.put("success", true);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("删除用户信息错误",e);
					result.put("errorMsg", "对不起，删除失败");
				}
				WriterUtil.write(response, result.toString());
			}


			private Integer findCategoryIdByAname(String categoryname) {
				Contentcategory contentcategory = new Contentcategory(); 
				contentcategory.setCategoryname(categoryname);
				//Contentcategory one = contentcategoryMapper.selectOne(contentcategory);
				Contentcategory one = contentcategoryMapper.fandcategory(contentcategory);
				
				
				if(one==null){
					contentcategoryMapper.insert(contentcategory);
					one=contentcategory;
				}
				
				return one.getContentcategoryid();
			}

	
}
