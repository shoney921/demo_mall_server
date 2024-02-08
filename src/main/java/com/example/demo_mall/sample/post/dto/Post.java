package com.example.demo_mall.sample.post.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long postId;
  private String memberId;
  private String title;
  private String content;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  // 생성 시간과 업데이트 시간을 자동으로 처리하는 메소드
  @PrePersist
  protected void onCreate() {
    this.createDate = LocalDateTime.now();
    this.updateDate = this.createDate;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updateDate = LocalDateTime.now();
  }
}
