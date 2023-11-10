package API;

import DTO.InfoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetWifiInfo {
    public List<InfoDTO> getWifiInfo(double curLat, double curLnt){

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
        ResultSet rs = null;
        ArrayList<InfoDTO> infoDtoList = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(url, userId, password);
            String sql = "select * from wifi_info;";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();


            while(rs.next()){
                InfoDTO newInfoDTO = new InfoDTO();

                newInfoDTO.setX_SWIFI_MGR_NO(rs.getString("x_swifi_mgr_no"));
                newInfoDTO.setX_SWIFI_WRDOFC(rs.getString("x_swifi_wrdofc"));
                newInfoDTO.setX_SWIFI_MAIN_NM(rs.getString("x_swifi_main_nm"));
                newInfoDTO.setX_SWIFI_ADRES1(rs.getString("x_swifi_adres1"));
                newInfoDTO.setX_SWIFI_ADRES2(rs.getString("x_swifi_adres2"));
                newInfoDTO.setX_SWIFI_INSTL_FLOOR(rs.getString("x_swifi_instl_floor"));
                newInfoDTO.setX_SWIFI_INSTL_TY(rs.getString("x_swifi_instl_ty"));
                newInfoDTO.setX_SWIFI_INSTL_MBY(rs.getString("x_swifi_instl_mby"));
                newInfoDTO.setX_SWIFI_SVC_SE(rs.getString("x_swifi_svc_se"));
                newInfoDTO.setX_SWIFI_CMCWR(rs.getString("x_swifi_cmcwr"));
                newInfoDTO.setX_SWIFI_CNSTC_YEAR(rs.getString("x_swifi_cnstc_year"));
                newInfoDTO.setX_SWIFI_INOUT_DOOR(rs.getString("x_swifi_inout_door"));
                newInfoDTO.setX_SWIFI_REMARS3(rs.getString("x_swifi_remars3"));
                newInfoDTO.setLAT(rs.getString("lat"));
                newInfoDTO.setLNT(rs.getString("lnt"));
                newInfoDTO.setWORK_DTTM(rs.getString("work_dttm"));

                infoDtoList.add(newInfoDTO);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if (preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        infoDtoList.sort((o1, o2) -> {
            double answer1 = getDistance(Double.parseDouble(o1.getLAT()), Double.parseDouble(o1.getLNT()), curLat, curLnt);
            o1.setDistance(String.format("%.3f", answer1));
            double answer2 = getDistance(Double.parseDouble(o2.getLAT()), Double.parseDouble(o2.getLNT()), curLat, curLnt);
            o2.setDistance(String.format("%.3f", answer2));
            return Double.compare(answer1, answer2);
        });


        return infoDtoList.subList(0, 20);
    }

    public double getDistance(double lat1, double lnt1, double lat2, double lnt2){

        double theta = lnt1 - lnt2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist / 1000;
    }

    public double deg2rad(double deg){
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

    public void test(){
        System.out.println("test Success!");
    }
}
