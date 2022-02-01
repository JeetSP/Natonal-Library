package com.nationalLibrary.library;

import org.springframework.data.repository.CrudRepository;

public interface bookRepo extends CrudRepository<Books,Integer> {

}
