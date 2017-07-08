package io.bittiger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import io.bittiger.entity.Ad;
import io.bittiger.entity.Campaign;

/**
 * 数据库操作
 */
public class MySQLAccess {

	private Connection d_connect = null;
	private String d_db_name;

	MySQLAccess(String server, String db, String user, String password) {
		d_db_name = db;

		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			String conn = "jdbc:mysql://" + server + "/" + d_db_name + "?user=" + user + "&password="
					+ password + "&useSSL=false";
			System.out.println("Connecting to database: " + conn);
			d_connect = DriverManager.getConnection(conn);
			System.out.println("Connected to database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void close() {
		System.out.println("Close Database");
		try {
			if (d_connect != null) {
				d_connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * ad
     */
	void addAdData(Ad ad) throws Exception{
		boolean isExist = false;
		String sql_string = "select adId from " + d_db_name + ".ad where adId=" + ad.adId;
		try {
			isExist = isRecordExist(sql_string);
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		if(isExist) return;
		
		sql_string = "insert into " + d_db_name + ".ad values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ad_info = null;
		try {
			ad_info = d_connect.prepareStatement(sql_string);
			ad_info.setLong(1, ad.adId);
      	    ad_info.setLong(2, ad.campaignId);
      	    String keyWords = Utility.strJoin(ad.keyWords, ",");			  
      	    ad_info.setString(3, keyWords);
      	    ad_info.setDouble(4, ad.bidPrice);
      	    ad_info.setDouble(5, ad.price);
      	    ad_info.setString(6, ad.thumbnail);
      	    ad_info.setString(7, ad.description);
      	    ad_info.setString(8, ad.brand);
      	    ad_info.setString(9, ad.detail_url);
      	    ad_info.setString(10, ad.category);
      	    ad_info.setString(11, ad.title);
      	    ad_info.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ad_info != null) {
         	   ad_info.close();
            }
		}
	}

    /**
     * campaign
     */
	void addCampaignData(Campaign campaign) throws Exception{
		boolean isExist = false;
		String sql_string = "select campaignId from " + d_db_name + ".campaign where campaignId=" + campaign.campaignId;
   	 	try {
   	 		isExist = isRecordExist(sql_string);        
        } catch(SQLException e) {
            throw e;
        } 
   	 	if(isExist) return;
   	 		 
        sql_string = "insert into " + d_db_name +".campaign values(?,?)";
   	 	PreparedStatement camp_info = null;   
        try {
        	camp_info = d_connect.prepareStatement(sql_string);
        	camp_info.setLong(1, campaign.campaignId);
			camp_info.setDouble(2, campaign.budget);
			camp_info.executeUpdate();
        } catch(SQLException e) {
            throw e;
        } finally {
        	if (camp_info != null) {
        		camp_info.close();
            }
       }   	 
	}

	private Boolean isRecordExist(String sql_string) throws SQLException {
		PreparedStatement existStatement = null;
		boolean isExist = false;

		try {
			existStatement = d_connect.prepareStatement(sql_string);
			ResultSet result_set = existStatement.executeQuery();
			if (result_set.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (existStatement != null) {
				existStatement.close();
			}
		}
		return isExist;
	}
}
