package com.pactera.v2x.testabout.service.thrift.impl;

import com.pactera.v2x.testabout.service.thrift.hello;

public class helloImpl implements hello.Iface {
    public String sayHello() throws org.apache.thrift.TException {
        System.out.println("hello 方法执行....");
        return "helle return!";
    }
}
