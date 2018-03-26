package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.domain.GalleryVO;
import com.dgit.service.GalleryService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private String innerUploadPath = "resources/upload";

	@Resource(name = "uploadPath")
	private String outUploadPath;

	@Autowired
	private GalleryService service;
	
	@RequestMapping(value = "/uploadDrag", method = RequestMethod.GET)
	public String uploadDragForm() {
		logger.info("[uploadDrag] FORM GET");
		return "uploadDragForm";
	}

	@ResponseBody
	@RequestMapping(value = "/uploadDrag", method = RequestMethod.POST)
	public ResponseEntity<List<HashMap<String, Object>>> uploadDragResult(String test, List<MultipartFile> files,String id) {
		logger.info("[uploadDrag] Result POST");
		logger.info(test);

		ResponseEntity<List<HashMap<String, Object>>> entity = null;
		List<HashMap<String, Object>> list = new ArrayList();
		try {
			for (MultipartFile file : files) {
				File dirPath = new File(outUploadPath);
				if (!dirPath.exists()) {
					dirPath.mkdirs();
				}

				/*
				 * UUID uid = UUID.randomUUID(); String saveName =
				 * uid.toString() + "_" + file.getOriginalFilename(); File
				 * target = new File(outUploadPath, saveName);
				 * FileCopyUtils.copy(file.getBytes(), target);
				 */
				
				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(),
						file.getBytes());
				HashMap<String, Object> map = new HashMap<String, Object>();
				
				GalleryVO vo = new GalleryVO();
				vo.setUserId(id);
				
				vo.setGpath(savedName);
				service.insertImg(vo);
				GalleryVO invo = service.selectOneByGno(vo.getGno());
				
				map.put("savedName",file.getOriginalFilename());
				map.put("vo", invo);
				list.add(map);
			}
			entity = new ResponseEntity<List<HashMap<String, Object>>>(list, HttpStatus.OK);
		} catch (IOException e) {
			entity = new ResponseEntity<List<HashMap<String, Object>>>(list, HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return entity;
	}

	// deleteFile
	@ResponseBody
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename, int gno) {
		ResponseEntity<String> entity = null;
		try {
			System.gc();
			File file = new File(outUploadPath + filename);
			File file2 = new File(outUploadPath + filename.replaceFirst("s_", ""));
			file2.delete();
			file.delete();
			logger.info("[deleteFile] - original : " + filename.replaceFirst("s_", ""));
			
			service.deleteImg(gno);
			
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}

		return entity;
	}

	@RequestMapping(value = "/uploadPreview", method = RequestMethod.GET)
	public String uploadPreviewForm() {
		return "uploadPreviewForm";
	}

	@RequestMapping(value = "/uploadPreview", method = RequestMethod.POST)
	public String uploadPreviewResult(String writer, MultipartFile file, Model model) {
		logger.info("[uploadPreview] POST");
		logger.info("writer --> " + writer);
		logger.info("file --> " + file.getOriginalFilename());

		ResponseEntity<String> entity = null;

		try {
			File dirPath = new File(outUploadPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(), file.getBytes());

			model.addAttribute("writer", writer);
			model.addAttribute("path", savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "uploadPreviewResult";
	}

	@RequestMapping(value = "/mutiplePreview", method = RequestMethod.GET)
	public String uploadMutiplePreviewForm() {
		return "uploadMutiplePreviewForm";
	}

	@RequestMapping(value = "/mutiplePreview", method = RequestMethod.POST)
	public String uploadmutiplePreviewResult(String writer, List<MultipartFile> files, Model model) {
		logger.info("[uploadPreview] POST");
		logger.info("writer --> " + writer);
		logger.info("files --> " + files.toString());
		 
		List<String> savedNames = new ArrayList<String>();
		for (MultipartFile file : files) { 
			System.out.println(file.getOriginalFilename());  
			try {
				File dirPath = new File(outUploadPath);
				if (!dirPath.exists()) {
					dirPath.mkdirs(); 
				}  

				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(),
						file.getBytes());
				savedNames.add(savedName);
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		}    
		System.out.println(savedNames.toString());
		model.addAttribute("writer", writer); 
		model.addAttribute("paths", savedNames);
		
		return "uploadMultiplePreviewResult";
	}
	
	@RequestMapping(value = "displayFile", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> displayFile(String filename) {
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;
		logger.info("[displayFile]:" + filename);

		try {
			String formatName = filename.substring(filename.lastIndexOf(".") + 1);
			MediaType type = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);

			in = new FileInputStream(outUploadPath + "/" + filename);

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
}
