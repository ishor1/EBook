package com.EBook.ServiceImp;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.EBook.Exception.ResourceNotFoundException;
import com.EBook.Model.Book;
import com.EBook.Model.Category;
import com.EBook.Repository.BookRepository;
import com.EBook.Repository.CategoryRepository;
import com.EBook.Service.BookService;
import com.EBook.Service.CategoryService;
import com.EBook.helper.PageableResponse;


@Service
public class BookServiceImp implements BookService {
	@Autowired
private	BookRepository bookRepository;
	@Autowired
private CategoryRepository categoryRepository;

	@Override
	public Book createBook(Book book) {
		book.setAddedDate(new Date());
		if(book.getBookImg()==null) {
			book.setBookImg("defaultBookImg.png");
		}
		Book book1=this.bookRepository.save(book);
		return book1;
	}

	@Override
	public Book updateBook(Book book, int bookId) {
		Book book1 = this.bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("BookId not Match"));
		book1.setTitle(book.getTitle());
		book1.setDescription(book.getDescription());
		book1.setAuthor(book.getAuthor());
		book1.setLive(book.isLive());
		book1.setAddedDate(new Date());
		book1.setBookImg(book.getBookImg());
		Book saveBook=this.bookRepository.save(book1);
		
		return saveBook ;
	}

	@Override
	public void deleteBook(int bookId) {
	Book book=	this.bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("BookId not Match! please enter correct BookId"));
		this.bookRepository.delete(book);
	}

	@Override
	public Book getBookById(int bookId) {
		Book book=this.bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Sorry Book not Found of that Id"));
		return book;
	}

	@Override
	public PageableResponse<Book> getAllBooks(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize,sort);
		Page<Book> page=this.bookRepository.findAll(pageable);
	  List<Book> books = page.getContent();
      PageableResponse<Book> pageableResponse = new PageableResponse<>();
      pageableResponse.setContent(books);
      pageableResponse.setPageNumber(page.getNumber());
      pageableResponse.setPageSize(page.getSize());
      pageableResponse.setTotalPages(page.getTotalPages());
      pageableResponse.setTotalElements(page.getTotalElements());
      pageableResponse.setLastPage(page.isLast());
	    
		return pageableResponse;
	}

	@Override
	public PageableResponse<Book> searchByTitle(String query,int pageNumber, int pageSize, String sortBy, String sortDir) {
		  Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
	        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
	        
	        Page<Book> page = bookRepository.findByTitleContaining(query,pageable);
	        List<Book> books = page.getContent();
	        PageableResponse<Book> pageableResponse = new PageableResponse<>();
	        pageableResponse.setContent(books);
	        pageableResponse.setPageNumber(page.getNumber());
	        pageableResponse.setPageSize(page.getSize());
	        pageableResponse.setTotalPages(page.getTotalPages());
	        pageableResponse.setTotalElements(page.getTotalElements());
	        pageableResponse.setLastPage(page.isLast());
	  	    
	  		return pageableResponse;
	}
	
	@Override
	public Book creteBookWithCategory(Book book, int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not foud"));
		book.setCategory(category);
		book.setAddedDate(new Date());
		if (book.getBookImg() == null) {
			book.setBookImg("defaultBookImg.png");
		}
		return this.bookRepository.save(book);
	}
	
	

	@Override
	public Book updateBookCategory(int bookId, int categoryId) {
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		Book book = this.bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("book not found"));
		book.setCategory(cat);

		return this.bookRepository.save(book);
	}

	
	@Override
	public PageableResponse<Book> getAllBookByCategory(int categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category not found"));
		Page<Book> page=this.bookRepository.findByCategory(category, pageable);
		 List<Book> books = page.getContent();
	      PageableResponse<Book> pageableResponse = new PageableResponse<>();
	      pageableResponse.setContent(books);
	      pageableResponse.setPageNumber(page.getNumber());
	      pageableResponse.setPageSize(page.getSize());
	      pageableResponse.setTotalPages(page.getTotalPages());
	      pageableResponse.setTotalElements(page.getTotalElements());
	      pageableResponse.setLastPage(page.isLast());
	      
		return pageableResponse;
	}
	

}
