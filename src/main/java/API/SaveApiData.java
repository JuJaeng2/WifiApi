package API;

import DTO.InfoDTO;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;

public class SaveApiData {

    public JsonElement totalDataCount;

//"http://openapi.seoul.go.kr:8088/4169614f736a373831384742564b69/json/TbPublicWifiInfo/1/100

    public int getAPI() throws IOException {
        //1 ~ 1000 먼저 저장
        String json1 = apiConnection(1);
        parseAndSave(json1);

        //1001번부터 나머지 저장
        int end = Integer.parseInt(String.valueOf(totalDataCount)) / 1000 + 1;
        for (int i = 2; i <= end; i++) {
            String json2 = apiConnection((i - 1) * 1000 + 1);
            parseAndSave(json2);
        }

        return Integer.parseInt(String.valueOf(totalDataCount));

    }
    public String apiConnection(int startData) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("4169614f736a373831384742564b69","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startData),"UTF-8")); /*요청시작위치 */
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startData + 999),"UTF-8")); /*요청종료위치*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/

        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String result = rd.readLine();
//        System.out.println("result >> " + result);
        return result;
    }

    public void parseAndSave(String json){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject1 = (JsonObject) jsonParser.parse(json);
        JsonObject jsonObject2 = (JsonObject) jsonObject1.get("TbPublicWifiInfo");
        this.totalDataCount = jsonObject2.get("list_total_count");
        JsonArray jsonArray = (JsonArray) jsonObject2.get("row");
//        System.out.println("jsonArray >> " + jsonArray);

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            InfoDTO infoDTO = new InfoDTO();

            infoDTO.setX_SWIFI_MGR_NO(object.get("X_SWIFI_MGR_NO").getAsString());
            infoDTO.setX_SWIFI_WRDOFC(object.get("X_SWIFI_WRDOFC").getAsString());
            infoDTO.setX_SWIFI_MAIN_NM(object.get("X_SWIFI_MAIN_NM").getAsString());
            infoDTO.setX_SWIFI_ADRES1(object.get("X_SWIFI_ADRES1").getAsString());
            infoDTO.setX_SWIFI_ADRES2(object.get("X_SWIFI_ADRES2").getAsString());
            infoDTO.setX_SWIFI_INSTL_FLOOR(object.get("X_SWIFI_INSTL_FLOOR").getAsString());
            infoDTO.setX_SWIFI_INSTL_TY(object.get("X_SWIFI_INSTL_TY").getAsString());
            infoDTO.setX_SWIFI_INSTL_MBY(object.get("X_SWIFI_INSTL_MBY").getAsString());
            infoDTO.setX_SWIFI_SVC_SE(object.get("X_SWIFI_SVC_SE").getAsString());
            infoDTO.setX_SWIFI_CMCWR(object.get("X_SWIFI_CMCWR").getAsString());
            infoDTO.setX_SWIFI_CNSTC_YEAR(object.get("X_SWIFI_CNSTC_YEAR").getAsString());
            infoDTO.setX_SWIFI_INOUT_DOOR(object.get("X_SWIFI_INOUT_DOOR").getAsString());
            infoDTO.setX_SWIFI_REMARS3(object.get("X_SWIFI_REMARS3").getAsString());
            infoDTO.setLAT(object.get("LAT").getAsString());
            infoDTO.setLNT(object.get("LNT").getAsString());
            infoDTO.setWORK_DTTM(object.get("WORK_DTTM").getAsString());

            // infoDTO에 저장된 값 데이터베이스에 저장
            save(infoDTO);
        }

        System.out.println("<<< API 데이터 저장 종료 >>>");
    }


    public void save(InfoDTO infoDTO){
        String url = "jdbc:mariadb://localhost/mission1db";
        String userId = "mission1";
        String password = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, userId, password);
            String sql = "insert into wifi_info(" +
                    "x_swifi_mgr_no," +
                    "x_swifi_wrdofc," +
                    "x_swifi_main_nm," +
                    "x_swifi_adres1, " +
                    "x_swifi_adres2, " +
                    "x_swifi_instl_floor, " +
                    "x_swifi_instl_ty, " +
                    "x_swifi_instl_mby, " +
                    "x_swifi_svc_se, " +
                    "x_swifi_cmcwr, " +
                    "x_swifi_cnstc_year, " +
                    "x_swifi_inout_door, " +
                    "x_swifi_remars3, " +
                    "lat, " +
                    "lnt," +
                    "work_dttm) " +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, infoDTO.getX_SWIFI_MGR_NO());
            preparedStatement.setString(2, infoDTO.getX_SWIFI_WRDOFC());
            preparedStatement.setString(3, infoDTO.getX_SWIFI_MAIN_NM());
            preparedStatement.setString(4, infoDTO.getX_SWIFI_ADRES1());
            preparedStatement.setString(5, infoDTO.getX_SWIFI_ADRES1());
            preparedStatement.setString(6, infoDTO.getX_SWIFI_INSTL_FLOOR());
            preparedStatement.setString(7, infoDTO.getX_SWIFI_INSTL_TY());
            preparedStatement.setString(8, infoDTO.getX_SWIFI_INSTL_MBY());
            preparedStatement.setString(9, infoDTO.getX_SWIFI_SVC_SE());
            preparedStatement.setString(10, infoDTO.getX_SWIFI_CMCWR());
            preparedStatement.setString(11, infoDTO.getX_SWIFI_CNSTC_YEAR());
            preparedStatement.setString(12, infoDTO.getX_SWIFI_INOUT_DOOR());
            preparedStatement.setString(13, infoDTO.getX_SWIFI_REMARS3());
            preparedStatement.setString(14, infoDTO.getLAT());
            preparedStatement.setString(15, infoDTO.getLNT());
            preparedStatement.setString(16, infoDTO.getWORK_DTTM());


            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0){
                System.out.println("API 데이터 저장 성공");
            }else {
                System.out.println("API 데이터 저장 실패");
            }

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("JDBC Connection Fail");
        }finally {

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }


        }

    }
}





















