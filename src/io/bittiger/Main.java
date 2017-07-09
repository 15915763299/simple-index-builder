package io.bittiger;

public class Main {

    /**
     * 0,Ad数据
     * 1,budget数据
     * 2,server & 3,port
     * MySQL 4,server:port, 5,database name, 6,user, 7,password
     */
    public static void main(String[] args) {

        AdsEngine adsEngine = new AdsEngine(
                "./" + args[0],//"./ads_data1.txt"**
                "./" + args[1],//"./budget.txt"
                args[2],//"127.0.0.1"
                Integer.parseInt(args[3]),//11211
                args[4],//"127.0.0.1:3306"
                args[5],//"searchads"
                args[6],//"root"
                args[7]//"password"
        );
        adsEngine.init();
    }
}
