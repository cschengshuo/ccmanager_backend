package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.MessageInfo;
import com.winsyo.ccmanager.repository.MessageinfoRepository;
import com.winsyo.ccmanager.util.umengpush.UmengPush;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoService {

  private MessageinfoRepository messageinfoRepository;

  public MessageInfoService(MessageinfoRepository messageinfoRepository) {
    this.messageinfoRepository = messageinfoRepository;
  }

  @Transactional
  public void sendAnnouncement(String text) {
    MessageInfo messageInfo = new MessageInfo();
    messageInfo.setText(text);
    messageInfo.setType("0");
    messageInfo.setCreatetime(LocalDateTime.now());

    messageinfoRepository.save(messageInfo);

    UmengPush.sendAndroidBroadcast(text);
    UmengPush.sendIOSBroadcast(text);
  }

  public Page<MessageInfo> listMessageInfo(Pageable pageable) {
    return messageinfoRepository.findByTypeOrderByCreatetimeDesc("0", pageable);
  }

}
