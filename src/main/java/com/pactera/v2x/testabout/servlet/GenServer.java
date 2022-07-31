package com.pactera.v2x.testabout.servlet;

import com.pactera.v2x.testabout.service.thrift.V2XService;
import com.pactera.v2x.testabout.service.thrift.hello;
import com.pactera.v2x.testabout.service.thrift.impl.V2XServiceImpl;
import com.pactera.v2x.testabout.service.thrift.impl.helloImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;

public class GenServer {
    private void startServer(){

        hello.Processor processor = new hello.Processor<hello.Iface>(new helloImpl());
//        V2XService.Processor processor = new V2XService.Processor<V2XService.Iface>(new V2XServiceImpl());
        try{
            TServerTransport transport = new TServerSocket(9999);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(transport);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            args.transportFactory(new TTransportFactory());
            args.minWorkerThreads(10);
            args.maxWorkerThreads(20);
            TServer server = new TThreadPoolServer(args);
            server.serve();

        }catch (Exception e){
            System.out.println(e);
        }


    }

    public static void main(String[] args) {
        GenServer server = new GenServer();
        server.startServer();
    }
}
