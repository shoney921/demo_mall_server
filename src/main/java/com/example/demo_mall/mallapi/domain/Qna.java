package com.example.demo_mall.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_qna")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qna {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long qno;

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
