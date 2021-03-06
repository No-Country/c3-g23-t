package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.User;
import com.estore.ecomerce.dto.ClientResponse;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserUpdateResponse;
import javassist.NotFoundException;

import javax.persistence.EntityNotFoundException;

public interface IUserService {
    User getInfoUser() throws NotFoundException;

    void delete(Long id)throws EntityNotFoundException;

    UserUpdateResponse update(Client clientAuth, Long id, UserRegisterRequest request)throws NotFoundException;

    ClientResponse getById(Long id);
}
