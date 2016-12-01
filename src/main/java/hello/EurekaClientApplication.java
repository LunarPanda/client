package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}

@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Autowired
    private Bot bot;
    
    @RequestMapping("/service")
    public String home() {
       return "hello from hello client.";
    }
    
    @RequestMapping(value="/train",produces = "application/json")
    public String train(){
    	return "{\"accuracy\":0.975}";
    }
    
    @RequestMapping(value="/chat",method=RequestMethod.GET,produces = "application/json")
    public String chat(@RequestParam("request") String request){
    	String reply=bot.reply(request);
		return reply;
    	
    }

    @RequestMapping("/service-instances/{applicationName}")
    public String serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
    	String result="hello: ";
        List<String> list=this.discoveryClient.getServices();
        for (String s: list){
        	result+=s+" ";
        }
        return result;
    }
}
