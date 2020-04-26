package se1_prog_lab.util;

import java.util.ArrayList;
import java.util.List;

public class ByteArrays {
    public static List<Byte> toList(byte[] byteArray) {
        ArrayList<Byte> list = new ArrayList<>();
        for (byte b: byteArray) {
            list.add(b);
        }
        return list;
    }

    public static byte[] toByteArray(List<Byte> list) {
        byte[] byteArray = new byte[list.size()];
        for(int i = 0; i < list.size(); i++) {
            byteArray[i] = list.get(i);
        }

        return byteArray;
    }
}
