
import com.vmm.JHTTPServer;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer extends JHTTPServer {

    public MyServer(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        System.out.println("Req received " + uri);
        Response res = null;
        if (uri.contains("GetSource")) {
            System.out.println(uri);
            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            System.out.println("New " + uri);
            res = sendCompleteFile(uri);

        } else if (uri.contains("StreamMedia")) {
            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            res = streamFile(uri, method, header);

        } else if (uri.contains("AdminLogin")) {
            String email = parms.getProperty("email");
            String passw = parms.getProperty("password");
            ResultSet rs = JdbcCommon.executeQuery("select * from Admin where email='" + email + "' and pass='" + passw + "'");

            try {

                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "success");

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (uri.contains("AddCourse")) {

            try {

                String name = parms.getProperty("name");
                String description = parms.getProperty("description");
                String amount2 = parms.getProperty("amount");
                int amount = Integer.parseInt(amount2);
                String pre = parms.getProperty("pre");
                String category = parms.getProperty("category");
                String filename1 = saveFileOnServerWithRandomName(files, parms, "squarephoto", "src/coursecontents");
                String filename2 = saveFileOnServerWithRandomName(files, parms, "widephoto", "src/coursecontents");
                String filename3 = saveFileOnServerWithRandomName(files, parms, "samplevideo", "src/coursecontents");
                ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + name + "'");
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                } else {
                    rs.moveToInsertRow();
                    rs.updateString("coursename", name);
                    rs.updateString("description", description);
                    rs.updateString("category", category);
                    rs.updateString("squarephoto", "src/coursecontents/" + filename1);
                    rs.updateString("widephoto", "src/coursecontents/" + filename2);
                    rs.updateString("samplevideo", "src/coursecontents/" + filename3);
                    rs.updateString("pre", pre);
                    rs.updateInt("amount", amount);
                    rs.insertRow();

                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else if (uri.contains("viewcourses")) {
            String ans = "";
            ResultSet rs = JdbcCommon.executeQuery("select * from course");
            try {
                while (rs.next()) {
                    String name = rs.getString("coursename");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    String amount = rs.getString("amount");

                    ans = ans + name + ";" + description + ";" + category + ";" + amount + "~";
                }
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("DeleteCourse")) {
            String coursed = parms.getProperty("coursed");
            System.out.println("request = " + coursed);
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + coursed + "'");

            try {
                if (rs.next()) {
                    rs.deleteRow();
                    res = new Response(HTTP_OK, "text/plain", "success");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }
            } catch (Exception ex) {
                //  Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

        } else if (uri.contains("getcourse")) {
            String ans = "";
            String selection = parms.getProperty("selection");
            String video = parms.getProperty("videopath");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where category = '" + selection + "'");

            try {
                while (rs.next()) {
                    ans = ans + rs.getString("coursename") + ";";
                }
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);

                ex.printStackTrace();
            }
        } else if (uri.contains("addvideos")) {
            int lectureno = Integer.parseInt(parms.getProperty("lecture"));
            int duration = Integer.parseInt(parms.getProperty("duration"));
            String title = parms.getProperty("title");
            String coursename = parms.getProperty("coursename");

            String filename1 = saveFileOnServerWithRandomName(files, parms, "video", "src/video_content");
            String filename2 = saveFileOnServerWithRandomName(files, parms, "thumbnail", "src/video_content");

            ResultSet rs = JdbcCommon.executeQuery("select * from videos");

            try {

                rs.moveToInsertRow();
                rs.updateInt("lecturenol", lectureno);
                rs.updateString("videopath", "src/video_content/" + filename1);
                rs.updateString("videothumbnail", "src/video_content/" + filename2);
                rs.updateString("title", title);
                rs.updateInt("duration", duration);
                rs.updateString("coursename", coursename);
                rs.insertRow();

                res = new Response(HTTP_OK, "text/plain", "success");
            } catch (SQLException ex) {
                //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);

                ex.printStackTrace();
            }
        } else if (uri.contains("fetchvideos")) {
            String ans = "";
            String course = parms.getProperty("course");
            ResultSet rs = JdbcCommon.executeQuery("select * from videos where coursename ='" + course + "'");
            try {
                while (rs.next()) {
                    int videoid = rs.getInt("videoid");
                    int lectureno = rs.getInt("lecturenol");
                    //int duration=rs.getInt("duration");
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration");
                    ans = ans + videoid + ";" + lectureno + ";" + title + ";" + duration + "~";
                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else if (uri.contains("Deletevideoid")) {
            String videoid = parms.getProperty("videoid");
            System.out.println("request = " + videoid);
            ResultSet rs = JdbcCommon.executeQuery("select * from videos where videoid='" + videoid + "'");

            try {
                if (rs.next()) {
                    rs.deleteRow();
                    res = new Response(HTTP_OK, "text/plain", "success");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }
            } catch (Exception ex) {
                //  Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

        } else if (uri.contains("signup")) {
            String email = parms.getProperty("email");
            String password = parms.getProperty("password");
            String name = parms.getProperty("name");
            String phone = parms.getProperty("phone");
            String f = saveFileOnServerWithRandomName(files, parms, "photo", "src/userphotos/");
            ResultSet rs = JdbcCommon.executeQuery("select * from  user where email='" + email + "'");

            try {
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                } else {
                    rs.moveToInsertRow();
                    rs.updateString("email", email);
                    rs.updateString("pass", password);
                    rs.updateString("name", name);
                    rs.updateString("phone", phone);
                    rs.updateString("photo", "src/userphotos/" + f);
                    rs.insertRow();

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
//Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("UserLogin")) {
            String email = parms.getProperty("email");
            String passw = parms.getProperty("password");
            ResultSet rs = JdbcCommon.executeQuery("select * from user where email='" + email + "' and pass='" + passw + "'");

            try {

                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "success");

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (uri.contains("fetchcoursesfromcat")) {
            String ans = "";
            String cname = parms.getProperty("category");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where category='" + cname + "'");

            try {
                while (rs.next()) {
                    ans = ans + rs.getString("coursename") + ";" + rs.getString("squarephoto") + ";" + rs.getInt("amount") + "~";
                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else if (uri.contains("Getdata")) {
            System.out.println("inside get data");

            String ans = "";
            String status;
            String coursename = parms.getProperty("coursename");
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + coursename + "'");
            try {
                if (rs.next()) {
                    ans = ans + rs.getString("coursename") + ";" + rs.getString("pre") + ";" + rs.getString("category") + ";" + rs.getString("squarephoto") + ";" + rs.getInt("amount") + ";" + rs.getString("widephoto") + ";" + rs.getString("samplevideo");
                }
                ResultSet rs2 = JdbcCommon.executeQuery("select * from UserSubscription where coursename = '" + coursename + "' and email = '" + email + "'");
                if (rs2.next()) {
                    status = "Yes";
                    ans = ans + ";" + status;
                } else {
                    status = "no";
                    ans = ans + ";" + status;
                }
                res = new Response(HTTP_OK, "text/plain", ans);
                System.out.println("Result = " + ans);
            } catch (SQLException ex) {
                //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else if (uri.contains("Streamvideos")) {
            String ans = "";
            String cname = parms.getProperty("coursename");
            System.out.println(cname);
            ResultSet rs = JdbcCommon.executeQuery("select * from videos where coursename ='" + cname + "'");

            try {
                while (rs.next()) {
                    ans = ans + rs.getInt("lecturenol") + ";" + rs.getString("title") + ";" + rs.getInt("duration") + ";" + rs.getString("videothumbnail") + ";" + rs.getString("videopath") + "~";
                }
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (uri.contains("Subscribe")) {
            String coursename = parms.getProperty("coursename");
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from UserSubscription");
            System.out.println(coursename + " " + email);
            try {
                rs.moveToInsertRow();

                rs.updateString("coursename", coursename);
                rs.updateString("email", email);
                rs.insertRow();
                System.out.println("ccccccccc" + coursename);
                System.out.println("eeeeeeeee" + email);
                res = new Response(HTTP_OK, "text/plain", "success");
                //rs.insertRow();
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("forgotpassword")) {
            try {
                String ans = "";
                String email = parms.getProperty("email");
                ResultSet rs = JdbcCommon.executeQuery("select * from user where email='" + email + "'");
                if (rs.next()) {
                    String contact = rs.getString("phone");
                    String password = rs.getString("pass");
                    System.out.println("passss" + password + contact);
                    ans = ans + contact + ";" + password;
                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (uri.contains("ModifyPassword")) {
            System.out.println("-------------");
            Set<String> stringPropertyNames = parms.stringPropertyNames();
            for (String s : stringPropertyNames) {
                System.out.println(s + "--");
            }
            String email = parms.getProperty("email");
            System.out.println(email);
            String password = parms.getProperty("pass");
            System.out.println(password);
            String npassword = parms.getProperty("new");
            System.out.println(email + " " + password + " " + npassword);
            ResultSet rs = JdbcCommon.executeQuery("select * from user where email='" + email + "' and pass ='" + password + "'");
            try {
                if (rs.next()) {
                    rs.updateString("pass", npassword);
                    rs.updateRow();
                    res = new Response(HTTP_OK, "text/plain", "success");
                   } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                }
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (uri.contains("UserFetch")) {
            try {
                String ans = "";
                String email = parms.getProperty("email");
                ResultSet rs = JdbcCommon.executeQuery("select * from user where email='" + email + "'");
                if (rs.next()) {
                    String name = rs.getString("name");
                    String contact = rs.getString("phone");
                    String photo = rs.getString("photo");
                    //String password = rs.getString("pass");
                    //System.out.println("passss" + password + contact);
                    ans = ans + photo + ";" + name + ";" + contact + ";" + email;
                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("sub")) {
            String ans = "";
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename in(select coursename from usersubscription where email='" + email + "')");

            try {
                while (rs.next()) {
                    String squarephoto = rs.getString("squarephoto");
                    String cou = rs.getString("coursename");
                    ans = ans + squarephoto + ";" + cou + "~";
                }
                res=new Response(HTTP_OK,"text/plain",ans);
            } catch (Exception ex) {
                // Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }

        return res;
    }
}
