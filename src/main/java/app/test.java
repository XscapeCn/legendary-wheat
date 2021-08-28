package app;

import java.util.Date;

public class test {
    public static void main(String[] args) {

        String str = "/data1/home/songxu";
        String[] split = str.split("/");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            System.out.println(i);

        }
        System.out.println(split[1]);

    }
}
