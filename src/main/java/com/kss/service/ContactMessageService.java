package com.kss.service;

import com.kss.domains.ContactMessage;
import com.kss.dto.ContactMessageDTO;
import com.kss.dto.request.ContactMessageRequest;
import com.kss.exception.BadRequestException;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.mapper.ContactMessageMapper;
import com.kss.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;


    // *************SAVEMESSAGE*******************
    public ContactMessageDTO saveMessage(ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage =
                contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageRepository.save(contactMessage);
        return contactMessageMapper.contactMessageToDTO(contactMessage);
    }

    // ************ GETALLMESSAGE********************
    public List<ContactMessageDTO> getAllMessage() {
        List<ContactMessage> contactMessageList = contactMessageRepository.findAll();
        return contactMessageMapper.map(contactMessageList);


    }
    // **************GETCONTACTMESSAGEWITHPAGE******************

    public Page<ContactMessageDTO> getAllMessage(Pageable pageable) {
        Page<ContactMessage> contactMessagePage = contactMessageRepository.findAll(pageable);
        return contactMessagePage.map(contactMessageMapper::contactMessageToDTO);
    }

    // **************GETMESSAGEBYID******************
    public ContactMessageDTO getMessageById(Long id) {
        ContactMessage contactMessage = contactMessageRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        return contactMessageMapper.contactMessageToDTO(contactMessage);

    }


    // **************UPDATEMESSAGE******************
    public ContactMessageDTO updateMessage(Long id, ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage = findMessageById(id);
        contactMessage.setName(contactMessageRequest.getName());
        contactMessage.setBody(contactMessageRequest.getBody());
        contactMessage.setSubject(contactMessageRequest.getSubject());
        contactMessage.setEmail(contactMessageRequest.getEmail());
        contactMessageRepository.save(contactMessage);
        return contactMessageMapper.contactMessageToDTO(contactMessage);
    }

    public ContactMessage findMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

    }

    // **************DELETEMESSAGE******************
    public ContactMessageDTO deleteContactMessage(Long id) {
        ContactMessage contactMessage = findMessageById(id);
        contactMessageRepository.delete(contactMessage);
        return contactMessageMapper.contactMessageToDTO(contactMessage);

    }

    public void removeAll() {
        contactMessageRepository.deleteAll();
    }

    public long countContactMessageRecords() {
        return contactMessageRepository.count();
    }
}
