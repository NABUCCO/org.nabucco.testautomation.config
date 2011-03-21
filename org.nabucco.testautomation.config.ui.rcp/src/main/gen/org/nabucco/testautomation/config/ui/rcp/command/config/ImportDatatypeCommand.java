/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * ImportDatatypeCommand<p/>This command is used to enable editing of an foreign owned datatype.<p/>
 *
 * @author Markus Jorroch, PRODYNA AG, 2010-04-15
 */
public class ImportDatatypeCommand implements NabuccoCommand {

    private ImportDatatypeHandler importDatatypeHandler = NabuccoInjector.getInstance(
            ImportDatatypeCommand.class).inject(ImportDatatypeHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.ImportDatatypeCommand";

    /** Constructs a new ImportDatatypeCommand instance. */
    public ImportDatatypeCommand() {
        super();
    }

    @Override
    public void run() {
        importDatatypeHandler.importDatatype();
    }

    @Override
    public String getId() {
        return ImportDatatypeCommand.ID;
    }
}
