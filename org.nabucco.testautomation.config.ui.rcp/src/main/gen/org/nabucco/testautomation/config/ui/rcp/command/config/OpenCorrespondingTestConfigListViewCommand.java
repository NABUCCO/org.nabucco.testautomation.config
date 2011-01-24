/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * OpenCorrespondingTestConfigListViewCommand<p/>open list view for TestConfig<p/>
 *
 * @author Stefan Huettenrauch, PRODYNA AG, 2010-06-08
 */
public class OpenCorrespondingTestConfigListViewCommand implements NabuccoCommand {

    private OpenCorrespondingTestConfigListViewHandler openCorrespondingTestConfigListViewHandler = NabuccoInjector
            .getInstance(OpenCorrespondingTestConfigListViewCommand.class).inject(
                    OpenCorrespondingTestConfigListViewHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.OpenCorrespondingTestConfigListViewCommand";

    /** Constructs a new OpenCorrespondingTestConfigListViewCommand instance. */
    public OpenCorrespondingTestConfigListViewCommand() {
        super();
    }

    @Override
    public void run() {
        openCorrespondingTestConfigListViewHandler.openCorrespondingTestConfigListView();
    }

    @Override
    public String getId() {
        return OpenCorrespondingTestConfigListViewCommand.ID;
    }
}
