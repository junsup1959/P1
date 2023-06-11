package springboot.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/monitor")
@Service
public class WebSocket {


    private static Set<Session> CLIENTS = Collections.synchronizedSet(new HashSet<>());
    private ScheduledExecutorService executorService;




    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.toString());

        if (CLIENTS.contains(session)) {
            System.out.println(" 이미 연결된 세션입니다. > " + session);
        } else {
            if(CLIENTS.isEmpty()|| CLIENTS.size() ==0){
                startSendingMonitorData();
            }
            CLIENTS.add(session);
            System.out.println(" 새로운 세션입니다. > " + session);
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        CLIENTS.remove(session);
        System.out.println(" 세션을 닫습니다. : " + session);
        if(CLIENTS.isEmpty()|| CLIENTS.size() ==0){
            stopSendingMonitorData();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println(" 입력된 메세지입니다. > " + message);

        for (Session client : CLIENTS) {
            System.out.println(" 메세지를 전달합니다. > " + message);
            client.getBasicRemote().sendText(message);
        }
    }

    public void startSendingMonitorData() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::sendMonitorData, 0, 3, TimeUnit.SECONDS);
    }

    private void sendMonitorData() {
        // CPU 사용률 및 모니터링 정보 수집 및 가공 로직 구현
        HashMap<String,Object> resoucre = getResoucre();
        ObjectMapper mapper = new ObjectMapper();


        for (Session client : CLIENTS) {
            try {
                String jsonStr = mapper.writeValueAsString(resoucre);
                client.getBasicRemote().sendText(jsonStr);
            } catch (IOException e) {
                // 클라이언트에게 메시지 전송 중 오류 발생 시 예외 처리
                e.printStackTrace();
            }
        }
    }
    private HashMap <String,Object> getResoucre() {
        // CPU 사용률을 얻는 로직 구현
        // 예시로 현재 시스템의 CPU 사용률을 가져오는 방법을 보여줌
        HashMap <String,Object> map = new HashMap<>();
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            double cpuUsage = osBean.getSystemCpuLoad() * 100;
            Long totalMemory = osBean.getTotalPhysicalMemorySize();
            Long FreeMemory = osBean.getFreePhysicalMemorySize();
            Long usingMemory = totalMemory - FreeMemory;

            double memory = ((double)usingMemory /totalMemory) * 100;
            map.put("cpu",cpuUsage);
            map.put("memory",memory);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }



    @PreDestroy
    public void stopSendingMonitorData() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
