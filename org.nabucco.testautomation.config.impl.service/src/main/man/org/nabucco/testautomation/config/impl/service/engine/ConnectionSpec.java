/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.config.impl.service.engine;

/**
 * ConnectionSpec
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ConnectionSpec {
	
	private String host;
	
	private Integer port;
	
	private String remoteName;

	public ConnectionSpec(String host, String port, String remoteName) {
		this.host = host;
		this.port = Integer.parseInt(port);
		this.remoteName = remoteName;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getRemoteName() {
		return remoteName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result
				+ ((remoteName == null) ? 0 : remoteName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionSpec other = (ConnectionSpec) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (remoteName == null) {
			if (other.remoteName != null)
				return false;
		} else if (!remoteName.equals(other.remoteName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConnectionSpec [host=" + host + ", port=" + port
				+ ", remoteName=" + remoteName + "]";
	}

}