package com.EBook.Controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import com.EBook.Service.BookService;
import com.EBook.Service.FileService;
import com.EBook.helper.PageableResponse;

@RestController
@RequestMapping("/book")

public class BookController {
	@Value("${book.image.path}")
	private String imagePath;

	@Autowired
	FileService fileService;
	@Autowired
	private BookService bookService;

	// post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Book> saveBook(@Validated @RequestBody Book book) {
		Book createBook = this.bookService.createBook(book);
		return new ResponseEntity<>(createBook, HttpStatus.CREATED);
	}

	// put
	@PutMapping("/{bookId}")
	public ResponseEntity<Book> updateBook(@Validated @RequestBody Book book, @PathVariable int bookId) {
		Book book1 = this.bookService.updateBook(book, bookId);
		return new ResponseEntity<>(book1, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
		this.bookService.deleteBook(bookId);
		return new ResponseEntity<>("BookDelete successfully", HttpStatus.OK);
	}

	// getBookById
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
		Book book = this.bookService.getBookById(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	// getAllBooks
	@GetMapping
	public ResponseEntity<PageableResponse<Book>> getAllBook(
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<>(this.bookService.getAllBooks(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	//search book
	  @GetMapping("/search/{query}")
	    public ResponseEntity<PageableResponse<Book>> searchProduct(
	            @PathVariable String query,
	            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
	            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
	            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
	            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
	    ){
	        PageableResponse<Book> pageableResponse = bookService.searchByTitle(query,pageNumber,pageSize,sortBy,sortDir);
	        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	    }
	
	@PutMapping("/img/{bookId}")
	public ResponseEntity<Book> productImgUpload(@PathVariable int bookId,
			@RequestParam("bookImage") MultipartFile file) throws IOException {
		Book book=null;
		if(bookId!=0) {
			book = this.bookService.getBookById(bookId);
		}
	
		Boolean check=false;
		if(!file.isEmpty()) {
			String bookImgName = this.fileService.uploadFile(file, imagePath);
			book.setBookImg(bookImgName);
			this.bookService.updateBook(book, bookId);
			check=true;
		}
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

}
