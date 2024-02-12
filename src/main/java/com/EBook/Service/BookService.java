package com.EBook.Service;

import com.EBook.Model.Book;

import com.EBook.helper.PageableResponse;

public interface BookService {
//create
	public Book createBook(Book book);

//update
	public Book updateBook(Book book, int bookId);

//delete
	public void deleteBook(int bookId);

//get
	public Book getBookById(int bookId);

	// get all Book
	public PageableResponse<Book> getAllBooks(int pageNumber, int pageSize, String sortBy, String sortDir);

	// search by Book title
	public PageableResponse<Book> searchByTitle(String query, int pageNumber, int pageSize, String sortBy,
			String sortDir);

	
	//create category with Book
	Book creteBookWithCategory(Book book,int categoryId);

	//update category with book
	Book updateBookCategory(int bookId, int categoryId);

	//get category with book
	PageableResponse<Book> getAllBookByCategory(int categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);

	
}
