package io.bittiger;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.bittiger.entity.Ad;
import io.bittiger.entity.Campaign;

/**
 * inverted index insert memcached
 * forward index insert mysql
 */
public class IndexBuilder {
	private int EXP = 0;//0: never expire
	private MySQLAccess mysql;
	private MemcachedClient cache;
	
	IndexBuilder(String memcachedServer, int memcachedPortal,
			String mysqlHost,String mysqlDb,String user,String password){
		
		try {
			mysql = new MySQLAccess(mysqlHost, mysqlDb, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String address = memcachedServer + ":" + memcachedPortal;
		try {
			cache = new MemcachedClient(
					new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Retry).build(), 
					AddrUtil.getAddresses(address)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void close() {
		if(mysql != null) {
			try {
				mysql.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * inverted index
	 * words为索引，将含有该关键词的所有广告的adId以Set的形式保存
	 */
	Boolean buildInvertIndex(Ad ad) {
		String keyWords = Utility.strJoin(ad.keyWords, ",");
		List<String> tokens = Utility.cleanedTokenize(keyWords);
		for(int i = 0; i < tokens.size();i++){
			String key = tokens.get(i);
			//将每个key对应的adId保存到cache中，因为可能重复添加，所以使用Set去重。
			if(cache.get(key) instanceof Set) {
				@SuppressWarnings("unchecked")
				Set<Long> adIdList = (Set<Long>)cache.get(key);
				adIdList.add(ad.adId);
			    cache.set(key, EXP, adIdList);
			} else {
				Set<Long> adIdList = new HashSet<Long>();
				adIdList.add(ad.adId);
				cache.set(key, EXP, adIdList);
			}
		}
		return true;
	}
	
	/**
	 * forward index
	 * adId为索引，每个广告相关的words以List的形式保存在该广告数据中
	 */
	Boolean buildForwardIndex(Ad ad) {
		try {
			mysql.addAdData(ad);			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	Boolean updateBudget(Campaign camp) {
		try {
			mysql.addCampaignData(camp);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	
}
