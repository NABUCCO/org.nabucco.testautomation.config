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
package org.nabucco.testautomation.config.impl.service.engine.visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import org.nabucco.testautomation.script.facade.datatype.metadata.MetadataLabel;
import org.nabucco.testautomation.script.facade.datatype.visitor.MetadataVisitor;

/**
 * MetadataPreparationVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class MetadataPreparationVisitor extends MetadataVisitor {

	private Long environment;
	
	private Long release;
	
	private Long brand;
	
	private Metadata metadata;
	
	private final Set<MetadataLabel> labelSet = new HashSet<MetadataLabel>();
	
	public MetadataPreparationVisitor(Code environment, Code release, Code brand) {
		super();
		
		if (environment != null) {
			this.environment = environment.getId();
		}
		if (release != null) {
			this.release = release.getId();
		}
		if (brand != null) {
			this.brand = brand.getId();
		}
	}

	@Override
	protected void visit(Metadata metadata) {
		this.metadata = metadata;

		for (MetadataLabel label : metadata.getLabelList()) {
			this.visit(label);
		}
		setPropertyList();
	}

	@Override
	protected void visit(MetadataLabel label) {
		this.labelSet.add(label);
	}
	
	private void setPropertyList() {
		
		List<MetadataLabel> matches = new ArrayList<MetadataLabel>();
		
		for (MetadataLabel label : this.labelSet) {
			
			if (label.getBrandTypeRefId() != null && !label.getBrandTypeRefId().equals(this.brand)) {
				continue;
			}
			if (label.getReleaseTypeRefId() != null && !label.getReleaseTypeRefId().equals(this.release)) {
				continue;
			}
			if (label.getEnvironmentTypeRefId() != null && !label.getEnvironmentTypeRefId().equals(this.environment)) {
				continue;
			}
			matches.add(label);
		}
		
		if (matches.isEmpty()) {
			return;
		} else if (matches.size() == 1) {
			metadata.setPropertyList(matches.get(0).getPropertyList());
		} else {
			for (MetadataLabel label : matches) {
				if (label.getBrandTypeRefId() != null && label.getBrandTypeRefId().equals(this.brand)
						&& label.getEnvironmentTypeRefId() != null && label.getEnvironmentTypeRefId().equals(this.environment)
						&& label.getReleaseTypeRefId() != null && label.getReleaseTypeRefId().equals(this.release)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getBrandTypeRefId() != null && label.getBrandTypeRefId().equals(this.brand)
						&& label.getEnvironmentTypeRefId() != null && label.getEnvironmentTypeRefId().equals(this.environment)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getReleaseTypeRefId() != null && label.getReleaseTypeRefId().equals(this.release)
						&& label.getEnvironmentTypeRefId() != null && label.getEnvironmentTypeRefId().equals(this.environment)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getBrandTypeRefId() != null && label.getBrandTypeRefId().equals(this.brand)
						&& label.getReleaseTypeRefId() != null && label.getReleaseTypeRefId().equals(this.release)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getBrandTypeRefId() != null && label.getBrandTypeRefId().equals(this.brand)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getEnvironmentTypeRefId() != null && label.getEnvironmentTypeRefId().equals(this.environment)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getReleaseTypeRefId() != null && label.getReleaseTypeRefId().equals(this.release)) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			for (MetadataLabel label : matches) {
				if (label.getReleaseTypeRefId() == null && label.getEnvironmentTypeRefId() == null && label.getBrandTypeRefId() == null) {
					metadata.setPropertyList(label.getPropertyList());
					return;
				}
			}
			metadata.setPropertyList(matches.get(0).getPropertyList());
		}
		
	}

}
