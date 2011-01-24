/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * DeleteTestConfigCommand<p/>This command should delete a TestConfig<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class DeleteTestConfigCommand implements NabuccoCommand {

    private DeleteTestConfigHandler deleteTestConfigHandler = NabuccoInjector.getInstance(
            DeleteTestConfigCommand.class).inject(DeleteTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.DeleteTestConfigCommand";

    /** Constructs a new DeleteTestConfigCommand instance. */
    public DeleteTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        deleteTestConfigHandler.deleteTestConfig();
    }

    @Override
    public String getId() {
        return DeleteTestConfigCommand.ID;
    }
}
