package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TreeNodeDto extends TreeDto {

  private List<TreeDto> children = new ArrayList<>();

  private boolean expand;

  private boolean loading;

  public TreeNodeDto(User user) {
    super(user);
  }

  public TreeNodeDto (TreeDto dto) {
    super(dto.getTitle(),dto.getValue());
  }

}
