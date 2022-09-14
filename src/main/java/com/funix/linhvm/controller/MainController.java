package com.funix.linhvm.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.FileUploadUtil;
import com.funix.linhvm.services.ProductService;

@RestController
public class MainController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin")
	public ModelAndView getAdmin() {
		return new ModelAndView("admin");
	}
	
	@GetMapping("/home")
	public ModelAndView getIndex() {
		return new ModelAndView("index");
	}
	
	@PostMapping("/type")
	public TypeEntity saveType(@RequestBody TypeEntity type) {
		productService.saveTypeProduct(type);
		return type;
	}
	
	@PostMapping("/image")
	public String saveImages(@RequestParam("image[]") MultipartFile[] multiPartFiles,
							@RequestParam("mainImage") MultipartFile multiPartFile,
							@RequestParam("name") String name,
							@RequestParam("price") int price,
							@RequestParam("description") String description,
							@RequestParam("type") String type) throws IOException {
		String uploadDir = "E:\\image";
		String file = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
		FileUploadUtil.saveFile(uploadDir, file, multiPartFile);
		for (MultipartFile multipartFile : multiPartFiles) {
			 String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			 FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		
		return name;
	}
}
