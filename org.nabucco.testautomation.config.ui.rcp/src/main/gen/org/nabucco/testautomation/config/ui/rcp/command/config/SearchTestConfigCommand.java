/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * SearchTestConfigCommand<p/>@TODO<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchTestConfigCommand implements NabuccoCommand {

    private SearchTestConfigHandler searchTestConfigHandler = NabuccoInjector.getInstance(
            SearchTestConfigCommand.class).inject(SearchTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.SearchTestConfigCommand";

    /** Constructs a new SearchTestConfigCommand instance. */
    public SearchTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        searchTestConfigHandler.searchTestConfig();
    }

    @Override
    public String getId() {
        return SearchTestConfigCommand.ID;
    }
}
