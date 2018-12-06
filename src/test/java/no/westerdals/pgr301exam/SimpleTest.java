package no.westerdals.pgr301exam;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SimpleTest {

    private Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @Autowired
    protected CustomerRepository repository;


    @Before
    public void before(){
        logger.info("Setting up preloaded testdata");
        clearDb();
        logger.info("Preloading " + repository.save(new Customer("Eddard", "Stark")));
        logger.info("Preloading " + repository.save(new Customer("Jon", "Arryn")));
        logger.info("Preloading " + repository.save(new Customer("Robert", "Baratheon")));
        logger.info("Preloading complete");
    }

    @After
    public void after(){
        logger.info("Tearing down testdata");
        clearDb();
        logger.info("Clearing down testdata complete");
    }

    private void clearDb(){
        repository.deleteAll();
    }

    @Test
    public void creatingUserShouldGiveOneMoreUser(){

        Long numberOfCustomers = repository.count();

        // Creating new customer
        Customer tywin = new Customer("Tywin", "Lannister");
        Customer hoster = new Customer("Hoster", "Tully");

        repository.save(tywin);
        repository.save(hoster);

        Long numberOfCustomersAfterAddons = repository.count();

        Assert.assertTrue(numberOfCustomers+2 == numberOfCustomersAfterAddons);
    }

    @Test
    public void assertFailure(){
        Assert.assertFalse(false);
    }

    @Test
    public void deletingUserShouldGiveOneLessUser(){

        Long numberOfCustomers = repository.count();

        // Creating new customer
        Customer tywin = new Customer("Tywin", "Lannister");
        repository.save(tywin);

        Long numberOfCustomersAfterAddons = repository.count();
        Assert.assertTrue(numberOfCustomers+1 == numberOfCustomersAfterAddons);

        repository.delete(tywin);

        numberOfCustomersAfterAddons = repository.count();
        Assert.assertTrue(numberOfCustomers == numberOfCustomersAfterAddons);
    }

}
