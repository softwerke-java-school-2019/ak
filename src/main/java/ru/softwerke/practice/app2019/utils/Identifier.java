package ru.softwerke.practice.app2019.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class Identifier {
    private static AtomicInteger id = new AtomicInteger();

    public static int nextId(){
        return id.getAndIncrement();
    }
}
