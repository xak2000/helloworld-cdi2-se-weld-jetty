/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.jersey.examples.helloworld.cdi2se;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.examples.helloworld.cdi2se.resource.CounterResource;
import org.glassfish.jersey.examples.helloworld.cdi2se.resource.HelloWorldResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

/**
 * Hello world!
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/");
    public static final String ROOT_HELLO_PATH = "helloworld";
    public static final String ROOT_COUNTER_PATH = "counter";

    public static void main(String[] args) {
//        grizzlySever();
        jettyServer();
    }

    public static void grizzlySever() {
        try {
            System.out.println("\"Hello World\" Jersey Example App");

            ResourceConfig resourceConfig = new ResourceConfig(HelloWorldResource.class, CounterResource.class);
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

            server.start();

            System.out.println("Application started.\nTry out");
            System.out.println(String.format("%s%s", BASE_URI, ROOT_HELLO_PATH));
            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/request"));
            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/application"));
            System.out.println("Stop the application using CTRL+C");

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void jettyServer() {
        try {
            System.out.println("\"Hello World\" Jersey Example App");

            ResourceConfig resourceConfig = new ResourceConfig(HelloWorldResource.class, CounterResource.class);
            Server server = JettyHttpContainerFactory.createServer(BASE_URI, resourceConfig, false);
            server.start();

            System.out.println("Application started.\nTry out");
            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_HELLO_PATH, "/{name}"));
            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/request"));
            System.out.println(String.format("%s%s%s", BASE_URI, ROOT_COUNTER_PATH, "/application"));
            System.out.println("Stop the application using CTRL+C");

            Thread.currentThread().join();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
