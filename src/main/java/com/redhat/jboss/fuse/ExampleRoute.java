/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.jboss.fuse;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import static org.apache.camel.model.rest.RestParamType.path;
import org.springframework.stereotype.Component;

/**
 *
 * @author dfreese
 */
@Component
public class ExampleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").port(80).bindingMode(RestBindingMode.json);

        rest("/users").description("User REST service")
                .consumes("application/json")
                .produces("application/json")

            .get("/{id}").description("Find user by ID")
                .outType(User.class)
                .param().name("id").type(path).description("The ID of the user").dataType("integer").endParam()
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("bean:userService?method=findUser(${header.id})");
    }

}
