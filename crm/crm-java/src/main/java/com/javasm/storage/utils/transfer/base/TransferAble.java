package com.javasm.storage.utils.transfer.base;

import java.awt.datatransfer.Transferable;

@FunctionalInterface
public interface TransferAble<T,V> {
    void transfer(T t,V v);
}
