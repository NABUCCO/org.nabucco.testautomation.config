/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.wizard.find;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.base.facade.datatype.utils.I18N;

/**
 * FindAndReplaceWizardPage
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class FindAndReplaceWizardPage extends WizardPage {

    private static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.wizard.FindAndReplaceWizardPage";

    private Text findTf;

    private Text replaceTf;

    private Button nameCb;

    private Button descriptionCb;

    private Button documentationCb;

    private Button propertiesCb;

    private Button attributesCb;

    private Button regexCb;

    /**
     * Constructs and initializes a new page instance.
     */
    public FindAndReplaceWizardPage() {
        super(I18N.i18n(ID + ".title"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControl(Composite parent) {

        GridLayout parentLayout = new GridLayout();
        parent.setLayout(parentLayout);
        GridData parentLayoutData = (GridData) parent.getLayoutData();
        parentLayoutData.minimumHeight = 250;
        parentLayoutData.minimumWidth = 300;

        Composite page = new Composite(parent, SWT.NONE);
        GridLayout pageGridLayout = new GridLayout(1, false);
        pageGridLayout.marginTop = 10;
        pageGridLayout.marginBottom = 0;
        pageGridLayout.marginLeft = 25;
        pageGridLayout.marginRight = 25;
        pageGridLayout.marginWidth = 0;
        pageGridLayout.marginHeight = 0;
        pageGridLayout.verticalSpacing = 15;
        page.setLayout(pageGridLayout);
        GridData pageLayoutData = new GridData();
        pageLayoutData.grabExcessHorizontalSpace = true;
        pageLayoutData.horizontalAlignment = SWT.FILL;
        pageLayoutData.grabExcessVerticalSpace = true;
        pageLayoutData.verticalAlignment = SWT.FILL;
        page.setLayoutData(pageLayoutData);

        Composite textFieldPlate = new Composite(page, SWT.NONE);
        GridLayout textFieldLayout = new GridLayout(3, false);
        textFieldPlate.setLayout(textFieldLayout);
        GridData textFieldLayoutData = new GridData();
        textFieldLayoutData.grabExcessHorizontalSpace = true;
        textFieldLayoutData.horizontalAlignment = SWT.FILL;
        textFieldLayoutData.grabExcessVerticalSpace = true;
        textFieldLayoutData.verticalAlignment = SWT.FILL;
        textFieldPlate.setLayoutData(textFieldLayoutData);

        GridData tfData = new GridData();
        tfData.horizontalAlignment = SWT.LEFT;
        tfData.widthHint = 250;

        Label findLabel = new Label(textFieldPlate, SWT.NULL);
        findLabel.setText((I18N.i18n(ID + ".find")));

        findTf = new Text(textFieldPlate, SWT.BORDER);
        findTf.setLayoutData(tfData);

        Composite optionsPlate = new Composite(textFieldPlate, SWT.NULL);
        GridLayout optionsLayout = new GridLayout(2, false);
        optionsLayout.marginLeft = 5;
        optionsLayout.marginBottom = 0;
        optionsLayout.marginTop = 0;
        optionsLayout.marginRight = 0;
        optionsLayout.horizontalSpacing = 5;
        optionsLayout.verticalSpacing = 0;
        optionsPlate.setLayout(optionsLayout);

        regexCb = new Button(optionsPlate, SWT.CHECK);
        regexCb.setSelection(true);
        Label nameLabel = new Label(optionsPlate, SWT.NULL);
        nameLabel.setText((I18N.i18n(ID + ".options.regex")));

        Label replaceLabel = new Label(textFieldPlate, SWT.NULL);
        replaceLabel.setText((I18N.i18n(ID + ".replace")));

        replaceTf = new Text(textFieldPlate, SWT.BORDER);
        replaceTf.setLayoutData(tfData);

        Group optionsGroup = new Group(page, SWT.SHADOW_ETCHED_IN);
        optionsGroup.setText((I18N.i18n(ID + ".options")));
        optionsGroup.setLayoutData(new GridData(SWT.NULL));
        optionsLayout = new GridLayout(4, false);
        optionsLayout.marginTop = 0;
        optionsLayout.marginBottom = 0;
        optionsGroup.setLayout(optionsLayout);

        optionsPlate = new Composite(optionsGroup, SWT.NULL);
        optionsLayout = new GridLayout(4, false);
        optionsLayout.marginLeft = 5;
        optionsLayout.marginBottom = 5;
        optionsLayout.marginTop = 5;
        optionsLayout.marginRight = 5;
        optionsLayout.horizontalSpacing = 5;
        optionsLayout.verticalSpacing = 5;
        optionsPlate.setLayout(optionsLayout);
        GridData optionsLayoutData = new GridData();
        optionsLayoutData.grabExcessHorizontalSpace = true;
        optionsLayoutData.horizontalAlignment = SWT.FILL;
        optionsLayoutData.grabExcessVerticalSpace = true;
        optionsLayoutData.verticalAlignment = SWT.FILL;
        optionsPlate.setLayoutData(optionsLayoutData);

        nameCb = new Button(optionsPlate, SWT.CHECK);
        nameCb.setSelection(true);
        nameLabel = new Label(optionsPlate, SWT.NULL);
        nameLabel.setText((I18N.i18n(ID + ".options.name")));

        descriptionCb = new Button(optionsPlate, SWT.CHECK);
        descriptionCb.setSelection(true);
        Label descriptionLabel = new Label(optionsPlate, SWT.NULL);
        descriptionLabel.setText((I18N.i18n(ID + ".options.description")));

        documentationCb = new Button(optionsPlate, SWT.CHECK);
        documentationCb.setSelection(true);
        Label documentationLabel = new Label(optionsPlate, SWT.NULL);
        documentationLabel.setText((I18N.i18n(ID + ".options.documentation")));

        propertiesCb = new Button(optionsPlate, SWT.CHECK);
        propertiesCb.setSelection(true);
        Label propertiesLabel = new Label(optionsPlate, SWT.NULL);
        propertiesLabel.setText((I18N.i18n(ID + ".options.properties")));

        attributesCb = new Button(optionsPlate, SWT.CHECK);
        attributesCb.setSelection(true);
        Label attributesLabel = new Label(optionsPlate, SWT.NULL);
        attributesLabel.setText((I18N.i18n(ID + ".options.attributes")));

        ModifyListener textFieldModifyListener = new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent arg0) {
                setPageComplete(validatePage());
            }
        };

        SelectionListener checkBoxSelectionListener = new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                setPageComplete(validatePage());
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        };

        findTf.addModifyListener(textFieldModifyListener);
        replaceTf.addModifyListener(textFieldModifyListener);
        nameCb.addSelectionListener(checkBoxSelectionListener);
        descriptionCb.addSelectionListener(checkBoxSelectionListener);
        documentationCb.addSelectionListener(checkBoxSelectionListener);
        propertiesCb.addSelectionListener(checkBoxSelectionListener);
        attributesCb.addSelectionListener(checkBoxSelectionListener);

        setControl(page);
        setPageComplete(validatePage());
    }

    public String getFind() {
        return this.findTf.getText();
    }

    public String getReplace() {
        return this.replaceTf.getText();
    }

    public boolean getFindInName() {
        return this.nameCb.getSelection();
    }

    public boolean getFindInDescription() {
        return this.descriptionCb.getSelection();
    }

    public boolean getFindInDocumentation() {
        return this.documentationCb.getSelection();
    }

    public boolean getFindInProperties() {
        return this.propertiesCb.getSelection();
    }

    public boolean getFindInAttributes() {
        return this.attributesCb.getSelection();
    }

    public boolean isRegex() {
        return this.regexCb.getSelection();
    }

    private boolean validatePage() {
        return this.findTf.getText().length() > 0
                && this.replaceTf.getText().length() > 0
                && (this.nameCb.getSelection()
                        || this.descriptionCb.getSelection() || this.documentationCb.getSelection()
                        || this.propertiesCb.getSelection() || this.attributesCb.getSelection());
    }

}
