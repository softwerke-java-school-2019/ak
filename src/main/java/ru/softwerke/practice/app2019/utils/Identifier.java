package ru.softwerke.practice.app2019.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Identifier {
    private AtomicLong id = new AtomicLong(1);

    public long nextId(){
        return id.getAndIncrement();
    }
}
