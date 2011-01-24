/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * ExecuteTestConfigCommand<p/>Command for executing a Test Configuration<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-10-15
 */
public class ExecuteTestConfigCommand implements NabuccoCommand {

    private ExecuteTestConfigHandler executeTestConfigHandler = NabuccoInjector.getInstance(
            ExecuteTestConfigCommand.class).inject(ExecuteTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.ExecuteTestConfigCommand";

    /** Constructs a new ExecuteTestConfigCommand instance. */
    public ExecuteTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        executeTestConfigHandler.executeTestConfig();
    }

    @Override
    public String getId() {
        return ExecuteTestConfigCommand.ID;
    }
}
