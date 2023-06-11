package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import springboot.model.admin.AdminEntity;

import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BaseService {

    @Autowired
    protected HttpSession httpSession;

    public HttpSession getession(){
        return httpSession;
    }

    public AdminEntity getusession(){
        return (AdminEntity)httpSession.getAttribute("login");
    }

    public void setusession(AdminEntity adminEntity){
        httpSession.setAttribute("login",adminEntity);
    }


}
