/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * CreateTestConfigCommand<p/>This command is for reading a TestConfiguration<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class CreateTestConfigCommand implements NabuccoCommand {

    private CreateTestConfigCommandHandler createTestConfigCommandHandler = NabuccoInjector
            .getInstance(CreateTestConfigCommand.class)
            .inject(CreateTestConfigCommandHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.CreateTestConfigCommand";

    /** Constructs a new CreateTestConfigCommand instance. */
    public CreateTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        createTestConfigCommandHandler.createTestConfigCommand();
    }

    @Override
    public String getId() {
        return CreateTestConfigCommand.ID;
    }
}
