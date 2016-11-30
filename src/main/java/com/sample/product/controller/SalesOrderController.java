package com.sample.product.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sample.product.dao.SalesOrderDAO;
import com.sample.product.dao.PurchaseOrderDAO;
import com.sample.product.dao.ProductDAO;
import com.sample.product.entity.Product;
import com.sample.product.entity.ShoppingCart;


@Controller
public class SalesOrderController {
	ApplicationContext context =  new ClassPathXmlApplicationContext("beans.xml");
	//private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	//configuration for session, please refer to: http://tuhrig.de/making-a-spring-bean-session-scoped/
	
	@RequestMapping(value = "/addShoppingCart", method = RequestMethod.GET)
	public ModelAndView addShoppingCart(@ModelAttribute Product product){
		ModelAndView model = new ModelAndView("redirect:/product");
		//only id is passed
		long pid = product.getId();
		System.out.println("pid="+pid);
		ProductDAO dao = (ProductDAO)context.getBean("productDAO");
		product = dao.get(product);//retrieve all information with id
		ShoppingCart shoppingCart = (ShoppingCart)context.getBean("shoppingCart"); 
		int i =0;
		shoppingCart.add(product);
		/*do{
			shoppingCart.add(product);
			i++;
		}while(pid != (shoppingCart.getCart()).get(i).getId());*/
		
		
		/*if(shoppingCart.count()== 0){
			shoppingCart.add(product);
		}
		else{
			for(int i=0;i<shoppingCart.count();i++){
				System.out.println("pid: " + pid + " test: "+ (shoppingCart.getCart()).get(i).getId()+ " count: " + shoppingCart.count());
				if(pid == (shoppingCart.getCart()).get(i).getId()){
					System.out.println("FAIL");
					
					break;
				}else{
					shoppingCart.add(product);
					break;
				}
			}
		}*/
		//System.out.println(shoppingCart.count());
		return model;
	}
	
	@RequestMapping(value = "/deleteShopping", method = RequestMethod.GET)
	public ModelAndView deleteShopping(@ModelAttribute Product product, long id){
		ModelAndView model = new ModelAndView("shoppingcart");	
		ShoppingCart dao = (ShoppingCart) context.getBean("shoppingCart");
		product.setId(dao.count());
		dao.delete(id);
		return model;
	}
	
	@RequestMapping(value = "/showCart", method = RequestMethod.GET)
	public ModelAndView showShoppingCart(){
		ModelAndView model = new ModelAndView("shoppingcart");
		ShoppingCart shoppingCart = (ShoppingCart)context.getBean("shoppingCart");
		List<Product> content =  shoppingCart.getCart();
		System.out.println("products in cart:"+content.size());
		model.addObject("shoppingCart",content);
		return model;
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	 public ModelAndView checkout(){
	  
	  ModelAndView model = new ModelAndView("purchaseOrder");
	  ShoppingCart shoppingCart = (ShoppingCart)context.getBean("shoppingCart");
	  SalesOrderDAO salesOrderDAO = (SalesOrderDAO)context.getBean("salesOrderDAO");
	  List<Product> pList =  shoppingCart.getCart();
	  System.out.println("plist:"+pList.size());
	  List<Long> pList2 = new ArrayList<Long>();
	  for (int i=0; i<pList.size();i++){
	   pList2.add(pList.get(i).getId());
	   System.out.println("id:"+pList.get(i).getId());
	   model.addObject("purchaseOrder",pList);
	  }
	  int result = 0;
	  try {
	   result = salesOrderDAO.sellProduct(pList2);
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  System.out.println("result="+result);
	  if (result != 0){ //successfully updated, clean up shopping cart
	   shoppingCart.cleanup();
	  }
	  return model;
	 }
	
	
}
