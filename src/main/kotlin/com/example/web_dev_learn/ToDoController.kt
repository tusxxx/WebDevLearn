package com.example.web_dev_learn

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class ToDoController(
    private val todoRepo: ToDoRepository
) {
    init {
        todoRepo.save(
            TodoItem(
                title = "Learn Spring Boot",
                description = "Learn how to use Spring Boot",
                status = Status.TODO,
                date = Date(),
                subtodos = emptyList()
            )
        )
    }

    @GetMapping("/todo")
    fun getTodos(): List<TodoItem> {
        return todoRepo.findAll()
    }

    @GetMapping("/todo/{id}")
    fun getTodo(@PathVariable id: Long): TodoItem {
        return todoRepo.findById(id).get()
    }

    @PostMapping("/todo")
    fun createTodo(
        @RequestBody httpEntity: TodoItemRequestBody
    ): TodoItem {
        val subtodos = httpEntity.subtodosJson?.map {
            TodoItem(
                title = it.title.orEmpty(),
                description = it.desc,
                status = Status.TODO,
                date = Date(),
                subtodos = emptyList()
            )
        }
        return todoRepo.save(
            TodoItem(
                title = httpEntity.title.orEmpty(),
                description = httpEntity.desc,
                status = Status.TODO,
                date = Date(),
                subtodos = subtodos.orEmpty()
            )
        )
    }

    @PutMapping("/todo/{id}")
    fun editTodo(
        @RequestBody httpEntity: TodoItemRequestBody,
        @PathVariable id: Long
    ): TodoItem {
        val subtodos = httpEntity.subtodosJson?.map {
            TodoItem(
                title = it.title.orEmpty(),
                description = it.desc,
                status = Status.TODO,
                date = Date(),
                subtodos = emptyList()
            )
        }

        val todo = todoRepo.findById(id).get()
        val newTodo = todo.copy(
            title = httpEntity.title.orEmpty(),
            description = httpEntity.desc,
            status = Status.TODO,
            date = Date(),
            subtodos = subtodos.orEmpty()
        )
        todoRepo.deleteById(id)

        return todoRepo.save(newTodo)
    }

    @DeleteMapping("todo/{id}")
    fun deleteTodo(
        @PathVariable id: Long
    ) {
        todoRepo.deleteById(id)
    }
}