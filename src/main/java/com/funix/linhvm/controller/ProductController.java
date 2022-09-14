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
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.FileUploadUtil;
import com.funix.linhvm.model.Product;
import com.funix.linhvm.services.ProductService;


@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/product")
	public ModelAndView getFirstProduct() {
		Map<String, Object> model = new HashMap<>();
		Page<ProductEntity> listP = productService.getAllProduct();
		List<TypeEntity> listT = productService.getAllType();
		int pageNumber = 0;
		model.put("pageNumber", pageNumber);
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
	
	@PostMapping("/page")
	public ModelAndView getPage(int page) {
		Map<String, Object> model = new HashMap<>();
		Page<ProductEntity> listP = productService.getNextProduct(page);
		List<TypeEntity> listT = productService.getAllType();
		model.put("pageNumber", page);
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
	
	
	@PostMapping("/addproduct") 
	public String addProduct(@RequestParam("image[]") MultipartFile[] multiPartFiles,
							 @RequestParam("mainImage") MultipartFile multiPartFile,
							 Product product) throws IOException {
		ProductEntity productEntity = new ProductEntity(product.getName(),product.getType(),product.getPrice(),product.getDescription(),2);
		String uploadDir = "E:\\image";
		String file = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
		productEntity.setMainImage(file);
		productService.saveProduct(productEntity);
		FileUploadUtil.saveFile(uploadDir, file, multiPartFile);
		for (MultipartFile multipartFile : multiPartFiles) {
			 String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			 FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			 productService.saveImage(productEntity.getId(), fileName);
		}
		return "success";
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
		Product product = productService.getProductByID(pid);
		List<TypeEntity> listT = productService.getAllType();
		model.put("id", pid);
		model.put("product", product);
		model.put("listT", listT);
		return new ModelAndView("edit",model);
	}
	
	@PostMapping("/edit") 
	public String editProduct(Product product, int id) {
		String result = productService.editProductByID(id, product);
		return result;
	}
	
	@PostMapping("/viewimage")
	public String viewImage(int id) {
		List<ImageEntity> list = productService.findImagesByID(id);
		String imageOne = "<img src='" + list.get(0).getImage() + "'>";
		String imageTwo = "<img src='" + list.get(1).getImage() + "'>";
		String imageThree = "<img src='" + list.get(2).getImage() + "'>";
		String imageFour = "<img src='" + list.get(3).getImage() + "'>";
		return imageOne + imageTwo + imageThree + imageFour;
	}
	
	@PostMapping("/searchproduct")
	public ModelAndView searchProduct(String keyword) {
		System.out.println(keyword);
		Map<String, Object> model = new HashMap<>();
		List<ProductEntity> listP = productService.searchProduct(keyword);
		List<TypeEntity> listT = productService.getAllType();
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("product",model);
	}
}
