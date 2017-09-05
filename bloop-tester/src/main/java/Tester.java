import com.tdc.bloop.api.BloopAPI;
import com.tdc.bloop.api.BloopAPIClientBuilder;
import com.tdc.bloop.model.BloopRequest;

public class Tester {
    public static void main(String[] args) {
        BloopAPI api = BloopAPIClientBuilder.defaultClient();
        BloopRequest request = new BloopRequest();
        request.setApplicationName("TextEditor");
        request.setApplicationVersion("1.0.0");
        request.setTargetHost("192.168.0.101");
        request.setTimeout(8000);
        request.setBloopObject("{\"message\":\"\"HelloWorld\"\"}");
        //api.bloop(request).get().getDescription()
        try {
            System.out.println(api.bloop(request).get().getDescription());
        }
        catch(Exception ex){

        }

    }
}
