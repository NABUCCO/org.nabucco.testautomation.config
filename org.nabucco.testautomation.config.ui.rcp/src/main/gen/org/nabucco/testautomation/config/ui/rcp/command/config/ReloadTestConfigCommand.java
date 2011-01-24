/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * ReloadTestConfigCommand<p/>This command should reload a TestConfig<p/>
 *
 * @author Marco Sussek, PRODYNA AG, 2010-10-19
 */
public class ReloadTestConfigCommand implements NabuccoCommand {

    private ReloadTestConfigHandler reloadTestConfigHandler = NabuccoInjector.getInstance(
            ReloadTestConfigCommand.class).inject(ReloadTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.ReloadTestConfigCommand";

    /** Constructs a new ReloadTestConfigCommand instance. */
    public ReloadTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        reloadTestConfigHandler.reloadTestConfig();
    }

    @Override
    public String getId() {
        return ReloadTestConfigCommand.ID;
    }
}
