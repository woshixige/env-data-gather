package com.briup.smart.env.backup;

import com.briup.smart.env.client.BackupImpl;
import org.junit.Test;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-23 14:20
 */
public class Back {
    @Test
    public void method() throws Exception {
        BackupImpl backup = new BackupImpl();
        Object a = backup.load("a", true);
        System.out.println(a);
    }
}
