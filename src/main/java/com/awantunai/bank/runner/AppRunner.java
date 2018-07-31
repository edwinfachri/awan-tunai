package com.awantunai.bank.runner;

import com.awantunai.bank.service.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
class AppRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final AdminService adminService;

    public AppRunner(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        adminService.createAdmin("Alice", "Alice", 1);
        // Assert.isTrue(adminService.findAllAdmins().size() == 7,
        //         "First admin should work with no problem");
        // logger.info("Alice, Bob and Carol have been booked");
        // try {
        //     adminService.book("Chris", "Samuel");
        // } catch (RuntimeException e) {
        //     logger.info("v--- The following exception is expect because 'Samuel' is too " +
        //             "big for the DB ---v");
        //     logger.error(e.getMessage());
        // }
        //
        // for (String person : adminService.findAllAdmins()) {
        //     logger.info("So far, " + person + " is booked.");
        // }
        // logger.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, " +
        //         "and Chris was rolled back in the same TX");
        // Assert.isTrue(adminService.findAllAdmins().size() == 3,
        //         "'Samuel' should have triggered a rollback");
        //
        // try {
        //     adminService.book("Buddy", null);
        // } catch (RuntimeException e) {
        //     logger.info("v--- The following exception is expect because null is not " +
        //             "valid for the DB ---v");
        //     logger.error(e.getMessage());
        // }
        //
        // for (String person : adminService.findAllAdmins()) {
        //     logger.info("So far, " + person + " is booked.");
        // }
        // logger.info("You shouldn't see Buddy or null. null violated DB constraints, and " +
        //         "Buddy was rolled back in the same TX");
        // Assert.isTrue(adminService.findAllAdmins().size() == 3,
        //         "'null' should have triggered a rollback");
    }

}
