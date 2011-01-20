package com.happytap.bangbang;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/16/11
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ByteUtils {

    public static byte[] toBytes(short value) {
        byte[] integer = new byte[4];
        integer[0] = (byte)(value & 0x000F);
        integer[1] = (byte)((value >> 8)  & 0x000F);
        return integer;
    }

    public static short toShort(byte[] data) {
        return byteArrayToShort(data, 0);
    }

    private static short byteArrayToShort(byte[] b, int offset) {
        short value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (2 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    public static void main(String...k) {
        for(short i = Short.MIN_VALUE; i< Short.MAX_VALUE; i++) {
            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
            try {
                ObjectOutputStream os = new ObjectOutputStream(os2);
                os.writeShort(i);
                os.flush();
                byte[] data = os2.toByteArray();
                ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(data));
                short value = is.readShort();
                if(i!=value) {
                    throw new RuntimeException(""+i);
                }

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
