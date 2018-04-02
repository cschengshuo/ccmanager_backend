package com.winsyo.ccmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

  @Id
  private String id;

  @Column(unique = true)
  private String role;

  @Column
  private String name;

}
