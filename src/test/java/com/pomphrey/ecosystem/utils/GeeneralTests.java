package com.pomphrey.ecosystem.utils;

import com.pomphrey.ecosystem.model.configuration.Species;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GeeneralTests {

    @Test
    void testArrayList(){
        Species wolf = new Species();
        wolf.setName("old");
        List<Species> list = new ArrayList<>();
        list.add(wolf);
        System.out.println(wolf.getName());
        System.out.println(list.get(0).getName());
        wolf.setName("new");
        System.out.println(wolf.getName());
        System.out.println(list.get(0).getName());
    }


    @Test
    void testArray(){
        Species wolf1 = new Species();
        Species wolf2 = new Species();
        Species wolf3 = new Species();
        wolf1.setName("old1");
        wolf2.setName("old2");
        wolf3.setName("old3");
        Species[] list = {wolf1, wolf2, wolf3};
        System.out.println(wolf1.getName() + "/" + list[0].getName());
        System.out.println(wolf2.getName() + "/" + list[1].getName());
        System.out.println(wolf3.getName() + "/" + list[2].getName());
        wolf1.setName("new");
        System.out.println(wolf1.getName() + "/" + list[0].getName());
        System.out.println(wolf2.getName() + "/" + list[1].getName());
        System.out.println(wolf3.getName() + "/" + list[2].getName());
    }



}
