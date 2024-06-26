package com.nt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nt.Service.IJobSeekerMgmtService;
import com.nt.entity.JobSeekerData;
import com.nt.entity.JobSeekerInfo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class JobSeekerMgmtService {
	@Autowired
	private IJobSeekerMgmtService service;
	//@Autowired
	private  Environment env;
	
	@GetMapping("/")
	public String showHomePage() { 
		return "welcome";
		
	}
	@GetMapping("/register")
	public String showjsRegisterForm(@ModelAttribute("js") JobSeekerData jsData)throws Exception {
		// return lvn
		return "jobseeker_register";
	}
	@PostMapping("/register")
	public String registerJsByUploadingFiles(@ModelAttribute("js") JobSeekerData jsData,Map<String, Object> map)throws Exception{
		// get uploade folder location from properties file
		String storeLocation = env.getProperties().getProperty("upload.store", "uploads");
		// if the not available then create it
		File file = new File(storeLocation);
		if(!file.exists())
			file.mkdir();
		// get InputStream representing the upload file content
		MultipartFile resumeFile =jsData.getResume();
	    MultipartFile photoFile =jsData.getPhoto();
	    InputStream isResume = resumeFile.getInputStream();
	    InputStream isPhoto = photoFile.getInputStream();
	    // get the names of the uploade files
	    String resumeFileName = resumeFile.getOriginalFilename();
	    String photoFileName = photoFile.getOriginalFilename();
	    // create outputstream representing empty destination files
	    OutputStream osResume = new FileOutputStream(file.getAbsolutePath()+"\\"+resumeFileName);
	    OutputStream osPhoto = new FileOutputStream(file.getAbsolutePath()+"\\"+photoFileName);
	    // perform file copy operation
	    IOUtils.copy(isResume,osResume);
	    IOUtils.copy(isPhoto, osPhoto);
	    // close stream 
	    isResume.close();
	    osResume.close();
	    isPhoto.close();
	    osPhoto.close();
	    //prepare Entity class obj from model class obj 
	    JobSeekerInfo jsinfo = new JobSeekerInfo();
	    jsinfo.setJsName(jsData.getJsName());
	    jsinfo.setJsAddrs(jsData.getJsAddrs());
	    jsinfo.setResumePath(file.getAbsolutePath()+"/"+resumeFileName);
	    jsinfo.setPhotoPath(file.getAbsolutePath()+"/"+photoFileName);
	    //use service
	    String msg = service.registerJobSeeker(jsinfo);
	    // keep the uploading file name and location in model attributes
	    map.put("file1",resumeFileName);
	    map.put("file2",photoFileName);
	    map.put("resultmsg", msg);
	    
		// return lvn
		return "show_result";
	}
	@GetMapping("/list_js")
	public String showReport(Map<String,Object> map) {
		System.out.println("JobSeekerMgmtService.showReport()");
		// use service 
		List<JobSeekerInfo> list = service.fetchAllJobSeekers();
		System.out.println(list.size());
		// add result to model attributes
		map.put("jsList", list);
		// return lvn
		return "show_report";
	}
	@Autowired
	private ServletContext sc;
	@GetMapping("/download")
	public String fileDownload(HttpServletResponse res,@RequestParam("jsId") Integer id,@RequestParam("type") String type)throws Exception {
		// get path of the file to be downloaded
		String filePath = null;
		if(type.equalsIgnoreCase("resume"))
			filePath = service.fecthResumePathById(id);
		else
			filePath = service.fecthPhotoPathById(id);
		System.out.println(filePath);
		// create File object representing file to be downloaded
		File file = new File(filePath);
		// get the length of the file and make it as the response content length
		res.setContentLengthLong(file.length());
		// get MIME of the file and make it as the response content type
		String mimeType = sc.getMimeType(filePath);
		mimeType=mimeType==null?"application/octet-stream":mimeType;
		res.setContentType(mimeType);
		// create inputStram pointing to the file
		InputStream is = new FileInputStream(file);
		//create OutputStream pointing to the response obj
		OutputStream os = res.getOutputStream();
		// instruct the browser to give file content as downloadble file
		res.setHeader("Content-Disposition","attachment;fileName="+file.getName());
		// write file content to response obj
		IOUtils.copy(is, os);
		//close streams
			is.close();
			os.close();
			return null; // makes the handler method to send respones directly to browser
	}
}
