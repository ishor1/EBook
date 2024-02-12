package com.EBook.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.EBook.Model.Book;
import com.EBook.Model.Category;



public interface BookRepository extends JpaRepository<Book, Integer>{
	 //search
    Page<Book> findByTitleContaining(String subTitle,Pageable pageable);
    
    Page<Book> findByCategory(Category category,Pageable pageable);
}
