package com.EBook.Service;




import com.EBook.Model.Book;
import com.EBook.Model.Category;
import com.EBook.helper.PageableResponse;


public interface CategoryService {
//create
	Category createCategory(Category category);
// update
	Category updateCategory(Category category,int categoryId);
// delete
	void deleteCategory(int categoryId);
// get
	Category getCategoryById(int categoryId);
	PageableResponse<Category> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);


	
	

}
