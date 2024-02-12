package com.EBook.ServiceImp;

import java.util.Date;
import java.util.List;

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
import com.EBook.Service.CategoryService;
import com.EBook.helper.PageableResponse;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Category createCategory(Category category) {
		if(category.getCategoryImage()==null) {
			category.setCategoryImage("defaultCategoryImg.png");
		}
		return this.categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Category category, int categoryId) {
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found."));
		cat.setTitle(category.getTitle());
		return this.categoryRepository.save(cat);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		this.categoryRepository.delete(cat);
	}

	@Override
	public Category getCategoryById(int categoryId) {

		return this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	public PageableResponse<Category> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("decs")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		Page<Category> page = categoryRepository.findAll(pageable);
		List<Category> category = page.getContent();
		PageableResponse<Category> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(category);
		pageableResponse.setPageNumber(page.getNumber());
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setTotalElements(page.getTotalElements());
		pageableResponse.setLastPage(page.isLast());
		return pageableResponse;
	}
	
	

}
