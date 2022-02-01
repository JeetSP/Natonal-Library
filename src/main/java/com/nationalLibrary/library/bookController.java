package com.nationalLibrary.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class bookController {
    @Autowired
    bookRepo repo;

    @PostMapping
    @ResponseBody
    public String createBooks(@RequestBody Books books){
        repo.save(books);
        return "Book are Stored";
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBooks () {
        Iterable<Books> books = repo.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("Book_List", books);
        response.put("serviceResponse", "Books fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooks (@PathVariable(value = "id") Integer bookId) {
        repo.deleteById(bookId);
        Map<String, Object> response = new HashMap<>();
        response.put("serviceResponse", "Book Id deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateBooks (@RequestBody Books books) {
        Optional<Books> booksOptional = repo.findById(books.getBookId());
        if (booksOptional.isEmpty()) {
            throw new ResourceAccessException("resource to be updated does not exist in database!!");
        }
        else {
            repo.save(books);
            Map<String, Object> response = new HashMap<>();
            response.put("Books", books);
            response.put("serviceResponse", "Books are updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
