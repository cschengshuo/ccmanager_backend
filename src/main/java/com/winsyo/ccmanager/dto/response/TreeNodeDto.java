package com.winsyo.ccmanager.dto.response;

import com.winsyo.ccmanager.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
