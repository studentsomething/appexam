package no.westerdals.pgr301exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private Integer numberOfWelcomes = 0;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Shows a meaningless welcome message that is barely changed at all compared to the template given by teacher. But that's the charm with this page ;)
     * @return
     */
    @RequestMapping("/")
    public String welcome() {

        logger.info("Showing some fancy welcome information");
        numberOfWelcomes++;

        // Will only be printed if log lvl is as low as debug
        logger.debug(System.getenv("HOSTEDGRAPHITE_HOST"));
        logger.debug(System.getenv("HOSTEDGRAPHITE_APIKEY"));

        return "...Welcome to this small REST service, it will accept: \n" +
                "GET    /customers \n" +
                "POST   /customers \n" +
                "DELETE /customers/{id} \n" +
                "GET    /customers/{id}";
    }
}
