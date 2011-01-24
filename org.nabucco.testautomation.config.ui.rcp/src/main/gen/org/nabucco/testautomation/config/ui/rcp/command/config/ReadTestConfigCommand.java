/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * ReadTestConfigCommand<p/>This command is for reading a TestConfig<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ReadTestConfigCommand implements NabuccoCommand {

    private ReadTestConfigHandler readTestConfigHandler = NabuccoInjector.getInstance(
            ReadTestConfigCommand.class).inject(ReadTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.ReadTestConfigCommand";

    /** Constructs a new ReadTestConfigCommand instance. */
    public ReadTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        readTestConfigHandler.readTestConfig();
    }

    @Override
    public String getId() {
        return ReadTestConfigCommand.ID;
    }
}
