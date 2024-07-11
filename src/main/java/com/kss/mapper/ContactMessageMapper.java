package com.kss.mapper;

import com.kss.domains.ContactMessage;
import com.kss.dto.ContactMessageDTO;
import com.kss.dto.request.ContactMessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper  {

  ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);

  @Mapping(target = "id", ignore = true)
  ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);

  //getAllContactMessage()
  List<ContactMessageDTO> map (List<ContactMessage> contactMessageList);




}
