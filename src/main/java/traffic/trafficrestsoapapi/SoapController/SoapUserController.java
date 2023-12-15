package traffic.trafficrestsoapapi.SoapController;

import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import roadguard.com.*;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.service.UserService;

import jakarta.xml.bind.JAXBElement;


import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Endpoint
public class SoapUserController {

    private static final String NAMESPACE_URI = "http://com.roadguard";

    @Autowired
    private UserService userService;
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public JAXBElement<GetUserResponse> getUser(@RequestPayload JAXBElement<GetUserByIdRequest> request) {
        GetUserResponse response = new GetUserResponse();
        UserInfo userInfo = new UserInfo();
        System.out.println(request.getValue().getUsername());
        Optional<User> user = userService.getUserById(request.getValue().getUsername());
        if (user.isPresent()){
        User foundUser = user.get();
        userInfo.setUsername(foundUser.getUsername());
        userInfo.setPassword(foundUser.getPassword());
        userInfo.setNom(foundUser.getNom());
        userInfo.setPrenom(foundUser.getPrenom());
        userInfo.setLieu(foundUser.getLieu());
        userInfo.setPosition(foundUser.getPosition());}
        response.setUserInfo(userInfo);
        QName qName = new QName("GetUserResponse");
        JAXBElement<GetUserResponse> jaxbElement = new JAXBElement<GetUserResponse>( qName, GetUserResponse.class, response);
        return jaxbElement;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public JAXBElement<AddUserResponse> addUser(@RequestPayload JAXBElement<AddUserRequest> request) {
        AddUserResponse response = new AddUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        User user = new User();

        user.setUsername(request.getValue().getUsername());
        user.setPassword(request.getValue().getPassword());
        user.setNom(request.getValue().getNom());
        user.setPrenom(request.getValue().getPrenom());
        user.setLieu(request.getValue().getLieu());
        user.setPosition(request.getValue().getPosition());

        userService.createUser(user);
        serviceStatus.setStatus("SUCCESS");
        serviceStatus.setMessage("Content Added Successfully");
        response.setServiceStatus(serviceStatus);
        QName qName = new QName("AddUserRequest");
        JAXBElement<AddUserResponse> jaxbElement = new JAXBElement<AddUserResponse>( qName, AddUserResponse.class, response);
        return jaxbElement;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public JAXBElement<DeleteUserResponse> deleteUser(@RequestPayload JAXBElement<DeleteUserRequest> request) {
        userService.deleteUser(request.getValue().getUsername());
        ServiceStatus serviceStatus = new ServiceStatus();

        serviceStatus.setStatus("SUCCESS");
        serviceStatus.setMessage("Content Deleted Successfully");
        DeleteUserResponse response = new DeleteUserResponse();
        response.setServiceStatus(serviceStatus);
        QName qName = new QName("DeleteUserResponse");
        JAXBElement<DeleteUserResponse> jaxbElement = new JAXBElement<DeleteUserResponse>( qName, DeleteUserResponse.class, response);
        return jaxbElement;
    }





}

