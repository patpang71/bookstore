package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;

public interface BookService {
	
	public List<Book> findAll();
	
	public Book findOne(Long id);
	
	public Book save(Book book);
	
	public List<Book> blurrySearch(String title);
	
	public void removeOne(Long id);

}
