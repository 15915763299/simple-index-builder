package io.bittiger;

public class Main {

    public static void main(String[] args) {
        AdsEngine adsEngine = new AdsEngine(
                "/EclipseWorkspace/SearchAdsDemo/data/ads_0502.txt",
                "/EclipseWorkspace/SearchAdsDemo/data/budget.txt",
                "127.0.0.1",
                11211,
                "127.0.0.1:3306",
                "searchads",
                "root",
                "password"
        );
        adsEngine.init();
    }
}
