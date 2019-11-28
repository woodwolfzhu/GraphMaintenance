package demo;


import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

    @Autowired
    private Graph graph;
}
