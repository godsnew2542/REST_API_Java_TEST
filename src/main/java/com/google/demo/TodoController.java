package com.google.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private List<Todo> todos = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong();

	public TodoController() {

	}

	// GET
	@GetMapping("/say")
	public String say() {
		return "NNewnew";
	}

	@GetMapping("/todo")
	public List<Todo> getTodos() {
		return todos;
	}

	@GetMapping("/todo/data")
	public Todo getTodosID() {
		final long id = 1;
		return todos.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
	}

//	@PatVaiable long id => id ต้องตรงกับ {id}
	@GetMapping("/todo/{id}")
	public Todo getTodosById(@PathVariable() long id) {
		return todos.stream().filter(result -> result.getId() == id).findFirst()
				.orElseThrow(() -> new TodoNotFoundException(id));
	}

//	@ /todo/search?name=nnewnew
	@GetMapping("/todo/search")
	public String getTodosName(@RequestParam(defaultValue = "ไม่มีค่าที่คุณต้องการ") String name) {
		return "search:  " + name;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/todo")
	public void addTodos(@RequestBody Todo todo) {
		todos.add(new Todo(counter.getAndIncrement(), todo.getName()));
	}

	@PutMapping("/todo/{id}")
	public void editTodos(@RequestBody Todo todo, @PathVariable long id) {

		todos.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			result.setName(todo.getName());
		}, () -> {
			throw new TodoNotFoundException(id);
		});
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/todo/{id}")
	public void deleteTodos(@PathVariable long id) {

		todos.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			todos.remove(result);
		}, () -> {
			throw new TodoNotFoundException(id);
		});
	}

}
