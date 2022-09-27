package com.funix.linhvm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.SizeEntity;
import com.funix.linhvm.entity.SizeNameEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.FileUploadUtil;
import com.funix.linhvm.model.Product;
import com.funix.linhvm.services.ProductService;



@RestController
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/product")
	public ModelAndView getFirstProduct() {
		Map<String, Object> model = new HashMap<>();
		Page<ProductEntity> listP = productService.getAllProduct();
		List<TypeEntity> listT = productService.getAllType();
		List<SizeNameEntity> listSize = productService.getAllSize();
		int pageNumber = 0;
		model.put("listSize",listSize);
		model.put("pageNumber", pageNumber);
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
	
	@PostMapping("/pageproduct")
	public ModelAndView getPage(int page) {
		Map<String, Object> model = new HashMap<>();
		Page<ProductEntity> listP = productService.getNextProduct(page);
		List<TypeEntity> listT = productService.getAllType();
		List<SizeNameEntity> listSize = productService.getAllSize();
		model.put("listSize",listSize);
		model.put("pageNumber", page);
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
	
	
	@PostMapping("/addproduct") 
	public String addProduct(@RequestParam("image[]") MultipartFile[] multiPartFiles,
							 @RequestParam("mainImage") MultipartFile multiPartFile,
							 @RequestParam("sizenames") String[] sizeNames,
							 Product product) throws IOException {
		String uploadDir = "C:\\Users\\VU LINH\\eclipse-workspace\\SWP490x\\src\\main\\resources\\static\\image";
		String file = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
		ProductEntity productEntity = new ProductEntity(product.getName(),product.getType(),product.getPrice(),product.getLastPrice(),file,product.getDescription());
		productService.saveProduct(productEntity);
		productService.saveSizes(productEntity.getId(),sizeNames);
		FileUploadUtil.saveFile(uploadDir, file, multiPartFile);
		for (MultipartFile multipartFile : multiPartFiles) {
			 String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			 FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			 productService.saveImage(productEntity.getId(), fileName);
		}
		return "successfully";
	}
	
	@PostMapping("/deleteproduct")
	public String deleteProduct(String ids) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Integer> list = mapper.readValue(ids,  new TypeReference<List<Integer>>(){});
		productService.deleteProducts(list);
		return "success";
	}
	
	@GetMapping("/edit")
	public ModelAndView getEditPage(int pid) {
		Map<String, Object> model = new HashMap<>();
		ProductEntity product = productService.getProductByID(pid);
		List<TypeEntity> listT = productService.getAllType();
		model.put("id", pid);
		model.put("product", product);
		model.put("listT", listT);
		return new ModelAndView("edit",model);
	}
	
	@PostMapping("/editproduct") 
	public String editProduct(Product product, int id, @RequestParam(value = "mainImage", required = false) MultipartFile multiPartFile) throws IOException {
		String uploadDir = "C:\\Users\\VU LINH\\eclipse-workspace\\SWP490x\\src\\main\\resources\\static\\image";
		String file = "";
		if (multiPartFile != null) {
		 file = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
		FileUploadUtil.saveFile(uploadDir, file, multiPartFile);
		}
		String result = productService.editProductByID(id, product, file);	
		return result;
	}
	
	@PostMapping("/viewimages")
	public String viewImage(int id) {
		String result = "";
		List<ImageEntity> list = productService.findImagesByID(id);
		for (ImageEntity imageEntity : list) {
			result += "<tr><td>" + imageEntity.getId() + "</td>"
					+ "<td><img src='http://localhost:8088/image/" + imageEntity.getImage() + "' width='150px'></td>"
					+  "<td><a onclick='deleteImages("
					+ imageEntity.getId() + ")'><i class='material-icons' data-toggle='tooltip' title='Delete'>delete</i></a></td></tr>"; 
		}
		return result;
	}
	
	@PostMapping("/deleteimages")
	public String deleteImages(int id) {
		productService.deleteImage(id);
		return "successfully";
	}
	
	@PostMapping("/addimages")
	public String addImages(int id, @RequestParam("newimg[]") MultipartFile[] multiPartFiles) throws IOException {
		String uploadDir = "C:\\Users\\VU LINH\\eclipse-workspace\\SWP490x\\src\\main\\resources\\static\\image";
		for (MultipartFile multipartFile : multiPartFiles) {
			 String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			 FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			 productService.saveImage(id, fileName);
		}
		return "successfully";
	}
	
	@PostMapping("/viewsizes")
	public String viewSizes(int id) {
		String result = "";
		List<SizeEntity> list = productService.findListSizeEntity(id);
		for (SizeEntity sizeEntity : list) {
			result += "<tr><td>" + sizeEntity.getId() + "</td>"
					+ "<td>" + sizeEntity.getSize() + "</td>"
					+  "<td><a onclick='deleteSizes("
					+ sizeEntity.getId() + ")'><i class='material-icons' data-toggle='tooltip' title='Delete'>delete</i></a></td></tr>"; 
		}
		return result;
	}
	
	@PostMapping("/deletesizes")
	public String deleteSizes(int id) {
		productService.deleteSizes(id);
		return "successfully";
	}
	
	@PostMapping("/addsizes")
	public String addSizes(int id, String[] sizes) {
		productService.saveSizes(id, sizes);
		return "successfully";
	}
	
	@PostMapping("/viewdes")
	public String viewDes(int id) {
		String result = "";
		ProductEntity pro = productService.getProductByID(id);
			result += pro.getDescription();
		return result;
	}
	
	@PostMapping("/searchproduct")
	public ModelAndView searchProduct(String keyword) {
		Map<String, Object> model = new HashMap<>();
		List<ProductEntity> listP = productService.searchProduct(keyword);
		List<TypeEntity> listT = productService.getAllType();
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
	
	@PostMapping("/addsize")
	public String addSize(String size) {
		SizeNameEntity name = new SizeNameEntity(size);
		productService.saveSize(name);
		return size;
	}
	
	@PostMapping("/deletesize")
	public String deleteSize(int id) {
		productService.deleteSizeName(id);
		return "successfully";
	}
	
	@PostMapping("/addtype")
	public String addType(String type) {
		TypeEntity name = new TypeEntity(type);
		productService.saveTypeProduct(name);
		return type;
	}
	
	@PostMapping("/deletetype")
	public String deleteType(int id) {
		productService.deleteType(id);
		return "successfully";
	}
}
