package xrservice3;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class Service3App {

    public static void main(String[] args) {
        SpringApplication.run(Service3App.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "failureAdvice")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    @RequestMapping("/stock")
    public String getStock(@RequestParam(value = "name", defaultValue = "AMD") String name) {
        return "hi stock: " + name + " , port No is:" + port;
    }


    @RequestMapping("/nba")
    public String getNbaTeams() {
        System.out.println("hihihihi");
        Random random = new Random();
        List<String> teams = new ArrayList<>();
        teams.add("raptors");
        teams.add("lakers");
        teams.add("Rockets");
        teams.add("Bucks");
        teams.add("Kings");
        teams.add("Warriors");
        teams.add("TimberWolves");
        teams.add("spurs");
        return "hi team: " + teams.get(random.nextInt(teams.size() - 1)) + " ";
    }


    public String failureAdvice(String name) {
        return "hi," + name + ",sorry,service 1 failed!";
    }
}
