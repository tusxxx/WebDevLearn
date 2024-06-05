package com.example.web_dev_learn

import jakarta.persistence.*
import java.util.Date

@Entity
data class TodoItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val title: String,
    val desc: String,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val subtodos: List<TodoItem>,
    val status: Status,
    val date: Date
)

enum class Status {
    TODO,
    DONE
}
