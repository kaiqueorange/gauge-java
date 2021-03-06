// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.connection;

import com.thoughtworks.gauge.GaugeConstant;

import java.net.Socket;

/**
 * Makes 2 important connections to the gauge core
 * <ul>
 * <li>Core connection where messages are responded to based on message type.
 * <li>API connection used to for asking gauge for common actions.
 * </ul>
 */
public class GaugeConnector {

    public static final String LOCALHOST = "localhost";
    private Socket gaugeSocket;
    private GaugeConnection gaugeApiConnection;

    public void makeConnectionsToGaugeCore() {
        gaugeSocket = connect(GaugeConstant.GAUGE_INTERNAL_PORT);
        Socket apiSocket = connect(GaugeConstant.GAUGE_API_PORT);
        gaugeApiConnection = new GaugeConnection(apiSocket);
    }

    private static Socket connect(String portEnvVariable) {
        String gaugePort = System.getenv(portEnvVariable);

        if (gaugePort == null || gaugePort.equalsIgnoreCase("")) {
            throw new RuntimeException(portEnvVariable + " not set");
        }
        int port = Integer.parseInt(gaugePort);
        Socket clientSocket;
        while (true) {
            try {
                clientSocket = new Socket(LOCALHOST, port);
                break;
            } catch (Exception ignored) {
            }
        }

        return clientSocket;
    }

    public GaugeConnection getGaugeApiConnection() {
        return gaugeApiConnection;
    }

    public Socket getGaugeSocket() {
        return gaugeSocket;
    }
}
