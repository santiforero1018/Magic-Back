// package edu.eci.arsw.magicBrushstrokes.repository;

// import java.sql.*;
// import edu.eci.arsw.magicBrushstrokes.model.Player;

// public class PlayerRepositiryManual implements PlayerRepository {

//     @Override
//     public void savePlayer(Player player) {
//         // create our mysql database connection
//         String myDriver = "com.mysql.cj.jdbc.Driver";
//         String myUrl = "jdbc:mysql://mysqldbmagicserver.mysql.database.azure.com:3306/magicbrushstrokes?useSSL=true&requireSSL=false";
//         try {
//             Class.forName(myDriver);
//             Connection conn = DriverManager.getConnection(myUrl, "root", "123");
//             String query = "INSERT INTO magicbrushstrokes.players(idplayer, name, nickname, age, password) VALUES ("
//                     + "1,"
//                     + player.getName() + "," + player.getNickName() + "," + player.getAge() + "," + player.getPassword()
//                     + ");";
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(query);
//         } catch (Exception e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }

//     }
// }
