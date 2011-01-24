/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * SaveTestConfigCommand<p/>This command should save a TestConfig<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SaveTestConfigCommand implements NabuccoCommand {

    private SaveTestConfigHandler saveTestConfigHandler = NabuccoInjector.getInstance(
            SaveTestConfigCommand.class).inject(SaveTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.SaveTestConfigCommand";

    /** Constructs a new SaveTestConfigCommand instance. */
    public SaveTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        saveTestConfigHandler.saveTestConfig();
    }

    @Override
    public String getId() {
        return SaveTestConfigCommand.ID;
    }
}
