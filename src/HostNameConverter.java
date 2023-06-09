import java.net.InetAddress;

public class HostNameConverter {
    public static InetAddress hostAddress;
    public static void main(String[] args) {
        try{
            hostAddress = InetAddress.getLocalHost();
            System.out.println("IP address: " + hostAddress.getHostAddress()+"\nHost name: "+ hostAddress.getHostName());
        }
        catch (Exception e){
            //If local address unavailable.
            e.printStackTrace();
        }
    }
}
