package com.college.yi.bookmanager.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.college.yi.bookmanager.entity.BookEntity;


@Mapper
public interface BookRepository{
	
	    List<BookEntity> findAll();

	 
	
	    BookEntity findById(Long id);
	    
	    void insert(BookEntity bookEntity);

	   
	    void update(BookEntity bookEntity);

	  
	    void delete(Long id);
}


