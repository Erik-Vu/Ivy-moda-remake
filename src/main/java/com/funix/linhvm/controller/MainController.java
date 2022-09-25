package com.funix.linhvm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.SizeEntity;
import com.funix.linhvm.entity.SizeNameEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.services.ProductService;

@RestController
public class MainController {
	@Autowired
	private ProductService productService;
		
	@GetMapping("/home")
	public ModelAndView getIndex() {
		Map<String, Object> model = new HashMap<>();
		Page<ProductEntity> listP = productService.getLastProduct();
		List<TypeEntity> listT = productService.getAllType();
		List<SizeNameEntity> listSize = productService.getAllSize();
		int pageNumber = 0;
		model.put("listSize",listSize);
		model.put("pageNumber", pageNumber);
		model.put("listP", listP);
		model.put("listT", listT);
		return new ModelAndView("index",model);
	}
	
	@GetMapping("/detail")
	public ModelAndView getDetail(int pid) {
		Map<String, Object> model = new HashMap<>();
		ProductEntity product = productService.getProductByID(pid);
		List<ImageEntity> listI = productService.findImagesByID(pid);
		List<SizeEntity> listS = productService.findListSizeEntity(pid);
		model.put("listSize", listS);
		model.put("product", product);
		model.put("listI", listI);
		return new ModelAndView("detail",model);
	}
	
	@GetMapping("/shop")
	public ModelAndView getShop(String type) {
		Map<String, Object> model = new HashMap<>();
		List<ProductEntity> listP = productService.getProductByType(type);
		model.put("listP", listP);
		model.put("type", type);
		return new ModelAndView("shop",model);
	}
	
	@GetMapping("/cart")
	public ModelAndView getCart() {
		return new ModelAndView("cart");
	}
	
}
