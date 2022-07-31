package com.pactera.v2x.testabout.service.thrift.impl;

import com.pactera.v2x.testabout.service.thrift.V2XService;
import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

public class V2XServiceImpl implements V2XService.Iface {
    @Override
    public String ping(String param) throws TException {
        System.out.println("ping 方法执行....");
        return "helle ping!";
    }

    @Override
    public Map<String, Map<String, String>> get_int_string_mapping_result(String key, Map<String, String> value) throws TException {
        System.out.println("get_int_string_mapping_result 方法执行....");
        Map<String,Map<String,String>> map = new HashMap<String, Map<String, String>>();
        Map<String,String> map2 = new HashMap<String, String>();
        map2.put("hellow","word");
        map.put("hellw",map2);
        return map;
    }

    @Override
    public boolean get_bool_result() throws TException {
        System.out.println("get_bool_result 方法执行....");
        return true;
    }
}
