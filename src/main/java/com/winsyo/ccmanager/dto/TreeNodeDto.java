package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class TreeNodeDto extends TreeDto {

  private List<TreeDto> children;

  private boolean expand;

  private boolean loading;

  public TreeNodeDto(User user) {
    super(user);
    this.children = new ArrayList<>();
  }

  public TreeNodeDto(TreeDto dto) {
    super(dto.getTitle(), dto.getValue());
    this.children = new ArrayList<>();
  }

}
