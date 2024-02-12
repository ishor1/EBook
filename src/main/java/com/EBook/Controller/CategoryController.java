package com.EBook.Controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.EBook.Model.Book;
import com.EBook.Model.Category;
import com.EBook.Service.BookService;
import com.EBook.Service.CategoryService;
import com.EBook.Service.FileService;
import com.EBook.helper.PageableResponse;
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Value("${category.image.path}")
	private String imagePath;

	@Autowired
	FileService fileService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookService bookService;
//	create
	@PostMapping
public	ResponseEntity<Category> createCategory(@Validated @RequestBody Category category) {
	
	Category cat=categoryService.createCategory(category);
	return new ResponseEntity<>(cat,HttpStatus.CREATED);
	}
//	update
	@PutMapping("/{catId}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category,@PathVariable int catId) {
Category cat= this.categoryService.updateCategory(category, catId);
return new ResponseEntity<>(cat,HttpStatus.OK);
	}
//	delete
	@DeleteMapping("/{catid}")
public ResponseEntity<String> deleteCategory(@PathVariable int catid ){
		this.categoryService.deleteCategory(catid);
		return new ResponseEntity<>("Category Delete Successfully",HttpStatus.OK);
	}
	
//	get
	@GetMapping("/{catId}")
public	ResponseEntity<Category> getCategory(@PathVariable int catId){
	Category cat=this.categoryService.getCategoryById(catId);
	return new ResponseEntity<>(cat,HttpStatus.OK);
}
	
	@GetMapping
public ResponseEntity<PageableResponse<Category>> getAllCategory(
			@RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
			){
		PageableResponse<Category> pageResponse=this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageResponse,HttpStatus.OK);
	}
	
	
// Book with Category
	@PostMapping("/{catId}/book")
	public ResponseEntity<Book>  createBookWithCategory(@PathVariable int catId,@Validated @RequestBody Book book){
		
		
	Book book1=	this.bookService.creteBookWithCategory(book, catId);
	return new ResponseEntity<>(book1,HttpStatus.CREATED);
	}
	
//Book update with category

	@PutMapping("/{catId}/book/{bookId}")
	public ResponseEntity<Book> updateBookwithCategory(@PathVariable int catId,@PathVariable int bookId){
	Book book=	this.bookService.updateBookCategory(bookId, catId);
	return new ResponseEntity<>(book,HttpStatus.OK);
	}

	
    //get Book by category
    @GetMapping("/{categoryId}/book")
    public ResponseEntity<PageableResponse<Book>> getProductOfCategory(
            @PathVariable int categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
    	
   PageableResponse<Book> allBoook= this.bookService.getAllBookByCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
    	return new ResponseEntity<>(allBoook,HttpStatus.OK);
    }
    
    
	@PutMapping("/img/{categoryId}")
	public ResponseEntity<Category> productImgUpload(@PathVariable int categoryId,
			@RequestParam("categoryImage") MultipartFile file) throws IOException {
		Category category=null;
		if(categoryId!=0) {
			category=this.categoryService.getCategoryById(categoryId);
		}
	
		Boolean check=false;
		if(!file.isEmpty()) {
			String categoryImgName = this.fileService.uploadFile(file, imagePath);
			category.setCategoryImage(categoryImgName);
			this.updateCategory(category, categoryId);
			check=true;
		}
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	
}
