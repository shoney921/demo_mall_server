package com.example.demo_mall.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_todo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long tno;

  private String title;
  private String writer;
  private Boolean complete;
  private LocalDate dueDate;

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateComplete(Boolean complete) {
    this.complete = complete;
  }

  public void updateDueDate(LocalDate dueDate){
    this.dueDate = dueDate;
  }
}
