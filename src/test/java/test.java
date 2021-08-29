import java.util.Date;

public class test {
    public static void main(String[] args) {

        String str = "/data1/home/songxu";
        String[] split = str.split("/");

//        System.out.println(split[0]);
        if (split[0].equals("")){
            System.out.println(1);
        }

    }
}
