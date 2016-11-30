package com.sample.product.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sample.product.dao.PurchaseOrderDAO;
import com.sample.product.dao.ProductDAO;
import com.sample.product.entity.Product;
import com.sample.product.entity.PurchaseOrder;



/**
 * Handles requests for the application home page.
 */
@Controller
public class PurchaseOrderController {
	ApplicationContext context =  new ClassPathXmlApplicationContext("beans.xml");
	
	//private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	

	@RequestMapping(value = "/reorderProduct", method = RequestMethod.GET)
	public ModelAndView listReorderProduct(){
	
		ModelAndView model = new ModelAndView("reorderProduct");
		//logger.info("controller");
		ProductDAO dao = (ProductDAO)context.getBean("productDAO");
		List<Product> productList = new ArrayList<Product>();
		productList = dao.getReorderList();
		//logger.info(""+productList.size());
		model.addObject("productList", productList);
		
		return model;
	}

	@RequestMapping(value = "/createPO", method = RequestMethod.GET)
	public ModelAndView reorderProduct(@ModelAttribute Product product){
		ModelAndView model = new ModelAndView("redirect:/productcon");
		PurchaseOrderDAO purchaseOrderDAO = (PurchaseOrderDAO)context.getBean("purchaseOrderDAO");
		ProductDAO dao = (ProductDAO) context.getBean("productDAO");
		product = dao.get(product.getId());
		int qty = product.getInventory();
		purchaseOrderDAO.create(product, (product.getReorderPoint())-qty);
		product.setInventory(qty + (product.getReorderPoint()-qty));
		dao.update(product);
		model.addObject("productList", product);
		System.out.println("safe: " + product.getReorderPoint() + " inventory: " + product.getInventory());//qty is fixed for now
		//System.out.println("id ="+product.getId());
		return model;
	}
	
	@RequestMapping(value = "/po", method = RequestMethod.GET)
	public ModelAndView listPO(@ModelAttribute Product product){
		ModelAndView model = new ModelAndView("purchaseOrder");
		PurchaseOrderDAO purchaseOrderDAO = (PurchaseOrderDAO)context.getBean("purchaseOrderDAO");
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		poList=purchaseOrderDAO.getList();
		//System.out.println("#ofPO ="+poList.size());
		model.addObject("poList",poList);
		return model;
	}

	@RequestMapping(value = "/stock", method = RequestMethod.GET)
	public ModelAndView stockProduct(@ModelAttribute PurchaseOrder po){
		ModelAndView model = new ModelAndView("redirect:/po");
		PurchaseOrderDAO purchaseOrderDAO = (PurchaseOrderDAO)context.getBean("purchaseOrderDAO");
		
		try {
			int result=purchaseOrderDAO.stockProduct(po);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("#ofPO ="+poList.size());
		//model.addObject("poList",poList);
		return model;
	}
	
	
}
