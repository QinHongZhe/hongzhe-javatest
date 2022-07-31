package com.pactera.v2x.testabout.client;

import com.pactera.v2x.testabout.service.thrift.V2XService;
import com.pactera.v2x.testabout.service.thrift.hello;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class DemoClient {
    private static final String SERVER_IP = "172.17.5.61";
    private static final int SERVER_PORT = 9999;
    private static final int TIMEOUT = 1000;

    private void startClient(String userName) {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT);
            TProtocol protocol = new TBinaryProtocol(transport);
//            hello.Client client = new hello.Client(protocol);
            V2XService.Client client = new V2XService.Client(protocol);
            transport.open();
            // 调用服务端的方法
            String res = client.ping("hellow");
            System.out.println("调用返回结果为：" + res);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    public static void main(String[] args) {
        DemoClient client = new DemoClient();
        client.startClient("hello");
    }
}
