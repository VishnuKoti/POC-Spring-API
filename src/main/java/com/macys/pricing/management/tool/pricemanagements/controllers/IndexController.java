package com.macys.pricing.management.tool.pricemanagements.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Homepage controller.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

}
