package com.EBook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EBook.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
